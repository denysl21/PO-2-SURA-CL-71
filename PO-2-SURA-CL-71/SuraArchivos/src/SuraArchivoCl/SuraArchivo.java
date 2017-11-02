package SuraArchivoCl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.csvreader.CsvWriter;

public class SuraArchivo {
	private static String path_historico = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Historicos_FTP\\";
	private static final String SEPARADOR = ";";
	private static int INCREMENTADOR = 0;
	private static int NUM_ETAPA = 0;

	public static void main(String[] args) throws IOException, ParseException {
		SuraArchivo s = new SuraArchivo();
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		// String hoy=sdf.format(new Date());

		s.leerAllFiles();
		ValidaStatusSms valida = new ValidaStatusSms();
		// if (isFeriado(hoy)) {
		valida.ejecutar();
		// }

	}

	public void leerAllFiles() throws IOException, ParseException {
		String cadenaFecha_ayer = null;
		cadenaFecha_ayer = getFechaArchivos();

		File folder = new File(path_historico);
		String files = null;
		File[] listOfFiles = folder.listFiles();

		List<ArchivoEloqua> lis_ArchivoEluqua = new ArrayList<ArchivoEloqua>();
		for (int i = 0; i < listOfFiles.length; i++) {
			// if (listOfFiles[i].isFile()&& !isFeriado(cadenaFecha_ayer)) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				// System.out.println(files);
				if (cadenaFecha_ayer != null && files.contains(cadenaFecha_ayer)) {

					if (!files.equals("FF_TRACKING_ETAPA_5_" + cadenaFecha_ayer + ".csv")
							|| files.equals("HistoricoLogSms")) {

						BufferedReader bufferLectura = null;
						try {

							bufferLectura = new BufferedReader(new FileReader(path_historico + files));

							int tipoArchivo = archivoEtapa(files);
							NUM_ETAPA = tipoArchivo;
							// Leer una linea del archivo
							String linea = bufferLectura.readLine();
							linea = bufferLectura.readLine();
							while (linea != null) {
								// System.out.println("::"+files);
								String respuesta = null;
								String codigo = null;
								String mensaje = null;
								String[] campos = linea.split(SEPARADOR);

								ArchivoEloqua logSms = new ArchivoEloqua();
								// System.out.println("::"+campos[0]);
								Date fechaActual_1 = new Date();
								SimpleDateFormat formato_1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
								String cadenaFecha_1 = formato_1.format(fechaActual_1);

								switch (tipoArchivo) {
								case 5:
									String[] lisnombres_5 = campos[6].split(" ");

									logSms.setPeriodo(campos[0]);
									logSms.setRut(campos[1]);
									// logSms.setRutsin(campos[1]);
									logSms.setPaso(campos[2]);
									logSms.setInd_solicita_pago_preliminar(campos[3]);
									logSms.setTipo_tramite(campos[4]);
									logSms.setNum_tramite(campos[5]);
									logSms.setNombre(lisnombres_5[0]);
									logSms.setAp_paterno(campos[7]);
									logSms.setAp_materno(campos[8]);
									logSms.setFec_solicitud_tramite(converFecha(campos[9], "yyyy-MM-dd"));
									logSms.setEstado_tramite(campos[10]);

									logSms.setFec_seleccion_modalidad(converFecha(campos[11], "yyyy-MM-dd"));
									logSms.setTipo_modalidad(campos[12]);
									logSms.setCod_agencia_solicitud(campos[13]);
									logSms.setTipo_seleccion(campos[14]);
									logSms.setForma_de_pago(campos[15]);
									logSms.setFecha_de_pago(converFecha(campos[16], "yyyy-MM-dd"));
									logSms.setFec_nacimiento(converFecha(campos[17], "yyyy-MM-dd"));
									logSms.setEdad(campos[18]);
									logSms.setSexo(campos[19]);

									respuesta = "Hay que definir etapa 5";
									logSms.setSms_etapa(tipoArchivo);

									logSms.setHist_sms_etp_5(respuesta);
									break;
								case 4:
									String[] lisnombres_4 = campos[4].split(" ");

									logSms.setRut(campos[0]);
									logSms.setPaso(campos[1]);
									logSms.setTipo_tramite(campos[2]);
									logSms.setNum_tramite(campos[3]);
									logSms.setNombre(lisnombres_4[0].trim());
									logSms.setAp_paterno(campos[5]);
									logSms.setAp_materno(campos[6]);
									logSms.setFec_seleccion_modalidad(converFecha(campos[7], "yyyy-MM-dd"));
									logSms.setDescripcion_modalidad(campos[8]);

									logSms.setEntidad(campos[9]);
									logSms.setCodigo_area_telefono(campos[10]);
									logSms.setTelefono(campos[11]);
									logSms.setCodigo_area_celular(campos[12]);
									logSms.setCelular(campos[13]);
									logSms.setEmali(campos[14]);
									logSms.setFec_nacimiento(converFecha(campos[15], "yyyy-MM-dd"));
									logSms.setEdad(campos[16]);
									logSms.setSexo(campos[17]);
									logSms.setSms_etapa(tipoArchivo);

									if (!validarCelular(campos[12], campos[13])) {
										respuesta = "Numero no identificado. Fecha: " + cadenaFecha_1;
										if (campos[14].trim().equals("NT@NT.CL")) {
											logSms.setEmali("");
										}

									} else {
										logSms.setEmali(
												validarEmail(campos[14].trim(), campos[0], lisnombres_4[0].trim()));

										switch (campos[8]) {
										case "RETIRO PROGRAMADO":
											mensaje = "AFP CAPITAL: " + lisnombres_4[0].trim()
													+ ", HA SELECCIONADO LA MODALIDAD DE RETIRO PROGRAMADO, SU PAGO SERA EN 10 DIAS HABILES. GRACIAS POR CONTINUAR EN NUESTRA AFP.";
											break;
										case "RENTA VITALICIA DIFERIDA":
											mensaje = "AFP CAPITAL: " + lisnombres_4[0].trim()
													+ ", HA ELEGIDO LA MODALIDAD DE PENSION DE RENTA VITALICIA DIFERIDA, SU PAGO SERA EN 12 DIAS HABILES. GRACIAS POR SEGUIR EN NUESTRA AFP.";
											break;
										case "RENTA VITALICIA":
											mensaje = "AFP CAPITAL: " + lisnombres_4[0].trim()
													+ ", HA SELECCIONADO LA MODALIDAD RENTA VITALICIA. SU PENSION SERA PAGADA POR "
													+ campos[9].trim();
											break;
										case "RENTA VITALICIA CON RETIRO PROGRAMADO":
											mensaje = "AFP CAPITAL: " + lisnombres_4[0].trim()
													+ ", HA ELEGIDO LA MODALIDAD DE PENSION DE RENTA VITALICIA CON RETIRO PROGRAMADO, SU PAGO SERA EN 10 DIAS HABILES.";
											break;
										default:
											mensaje = "AFP CAPITAL: HOLA " + lisnombres_4[0].trim()
													+ ", HA SELECCIONADO LA MODALIDAD, SU PAGO SERA EN 10 DIAS HABILES. GRACIAS POR CONTINUAR EN NUESTRA AFP.";
											break;
										}

										respuesta = CallSendSms(campos[12] + campos[13], mensaje);

										String[] parts = respuesta.split(Pattern.quote("|"));
										respuesta = parts[0];
										codigo = parts[1];
										if (codigo.contains("-")) {
											logSms.setSms_codigo(codigo);
										} else {
											logSms.setSms_status(codigo);
										}
									}

									logSms.setHist_sms_etp_4(respuesta);
									break;

								case 2:
									String[] lisnombres_2 = campos[4].split(" ");

									logSms.setRut(campos[0]);
									logSms.setPaso(campos[1]);
									logSms.setTipo_tramite(campos[2]);
									logSms.setNum_tramite(campos[3]);
									logSms.setNombre(lisnombres_2[0].trim());
									logSms.setAp_paterno(campos[5]);
									logSms.setAp_materno(campos[6]);
									logSms.setFec_emision(converFecha(campos[7], "yyyy-MM-dd"));

									logSms.setCodigo_area_telefono(campos[8]);
									logSms.setTelefono(campos[9]);
									logSms.setCodigo_area_celular(campos[10]);
									logSms.setCelular(campos[11]);
									logSms.setEmali(campos[12]);
									logSms.setFec_nacimiento(converFecha(campos[13], "yyyy-MM-dd"));
									logSms.setEdad(campos[14]);
									logSms.setSexo(campos[15]);
									logSms.setSms_etapa(tipoArchivo);
									if (!validarCelular(campos[10], campos[11])) {

										respuesta = "Numero no identificado. Fecha: " + cadenaFecha_1;
										if (campos[12].trim().equals("NT@NT.CL")) {
											logSms.setEmali("");
										}
									} else {
										logSms.setEmali(validarEmail(campos[12].trim(), campos[0].trim(),
												lisnombres_2[0].trim()));
										mensaje = "AFP CAPITAL: " + lisnombres_2[0]
												+ ", SU CERTIFICADO DE SALDO ESTA DISPONIBLE (VIGENCIA 35 DIAS). ACERQUESE A CUALQUIER SUCURSAL PARA SEGUIR CON SU TRAMITE DE PENSION.";

										respuesta = CallSendSms(campos[10] + campos[11], mensaje);

										String[] parts = respuesta.split(Pattern.quote("|"));
										respuesta = parts[0];
										codigo = parts[1];
										if (codigo.contains("-")) {
											logSms.setSms_codigo(codigo);
										} else {
											logSms.setSms_status(codigo);
										}
									}

									logSms.setHist_sms_etp_2(respuesta);
									break;
								case 1:
									String[] lisnombres_1 = campos[4].split(" ");

									logSms.setRut(campos[0]);
									logSms.setPaso(campos[1]);
									logSms.setTipo_tramite(campos[2]);
									logSms.setNum_tramite(campos[3]);
									logSms.setNombre(lisnombres_1[0].trim());
									logSms.setAp_paterno(campos[5]);
									logSms.setAp_materno(campos[6]);
									logSms.setFec_solicitud_tramite(converFecha(campos[7], "yyyy-MM-dd"));

									logSms.setCodigo_area_telefono(campos[8]);
									logSms.setTelefono(campos[9]);
									logSms.setCodigo_area_celular(campos[10]);
									logSms.setCelular(campos[11]);

									logSms.setFec_nacimiento(converFecha(campos[13], "yyyy-MM-dd"));
									logSms.setEdad(campos[14]);
									logSms.setSexo(campos[15]);
									logSms.setEmali(campos[12]);
									logSms.setSms_etapa(tipoArchivo);

									if (!validarCelular(campos[10].trim(), campos[11].trim())) {
										respuesta = "Numero no identificado. Fecha: " + cadenaFecha_1;
										if (campos[12].trim().equals("NT@NT.CL")) {
											logSms.setEmali("");

										}

									} else {
										logSms.setEmali(validarEmail(campos[12].trim(), campos[0].trim(),
												lisnombres_1[0].trim()));
										mensaje = "AFP CAPITAL: " + lisnombres_1[0] + ", LE INFORMAMOS QUE CON FECHA "
												+ converFecha(campos[7], "yyyy-MM-dd")
												+ " INICIO SU TRAMITE DE PENSION. LE COMUNICAREMOS LAS SIGUIENTES ETAPAS DE ESTE TRAMITE.";

										respuesta = CallSendSms(campos[10] + campos[11], mensaje);
										// System.out.println("respuesta");

										String[] parts = respuesta.split(Pattern.quote("|"));
										respuesta = parts[0];
										codigo = parts[1];

										if (codigo.contains("-")) {
											logSms.setSms_codigo(codigo);
										} else {
											logSms.setSms_status(codigo);
										}
									}

									logSms.setHist_sms_etp_1(respuesta);
									break;
								}

								lis_ArchivoEluqua.add(logSms);

								linea = bufferLectura.readLine();
							}
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (bufferLectura != null) {
								try {
									bufferLectura.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			writeFile(lis_ArchivoEluqua);

		}
	}

	private void writeFile(List<ArchivoEloqua> lis_ArchivoEloqua) throws IOException {
		String archivoLog = validaArchivo();
		try {

			CsvWriter csvOutput = new CsvWriter(new FileWriter(archivoLog, true), ';');

			csvOutput.write("#PERIODO");
			csvOutput.write("RUT");
			csvOutput.write("AP_MATERNO");
			csvOutput.write("AP_PATERNO");
			csvOutput.write("CELULAR");
			csvOutput.write("COD_AGENCIA_SOLICITUD");
			csvOutput.write("CODIGO_AREA_CELULAR");
			csvOutput.write("CODIGO_AREA_TELEFONO");
			csvOutput.write("DESCRIPCION_MODALIDAD");
			csvOutput.write("EDAD");
			csvOutput.write("EMAIL");
			csvOutput.write("ENTIDAD");
			csvOutput.write("ESTADO_TRAMITE");
			csvOutput.write("FEC_EMISION");
			csvOutput.write("FEC_NACIMIENTO");
			csvOutput.write("FEC_SELECCION_MODALIDAD");
			csvOutput.write("FEC_SOLICITUD_TRAMITE");
			csvOutput.write("FECHA_PAGO");
			csvOutput.write("IND_SOLICITA_PAGO_PRELIMINAR");
			csvOutput.write("NOMBRE");
			csvOutput.write("NUM_TRAMITE");
			csvOutput.write("PASO");
			csvOutput.write("SEXO");
			csvOutput.write("TELEFONO");
			csvOutput.write("FORMA_DE_PAGO");
			csvOutput.write("TIPO_MODALIDAD");
			csvOutput.write("TIPO_SELECCION");
			csvOutput.write("TIPO_TRAMITE");
			csvOutput.write("HIST_SMS_ETP_1");
			csvOutput.write("HIST_SMS_ETP_2");
			csvOutput.write("HIST_SMS_ETP_4");
			csvOutput.write("HIST_SMS_ETP_5");

			csvOutput.write("SMS_CODIGO");
			csvOutput.write("SMS_STATUS");
			csvOutput.write("SMS_ETAPA");

			csvOutput.endRecord();

			for (ArchivoEloqua log : lis_ArchivoEloqua) {
				csvOutput.write(log.getPeriodo());
				csvOutput.write(log.getRut());
				csvOutput.write(log.getAp_materno());
				csvOutput.write(log.getAp_paterno());
				csvOutput.write(log.getCelular());
				csvOutput.write(log.getCod_agencia_solicitud());
				csvOutput.write(log.getCodigo_area_celular());
				csvOutput.write(log.getCodigo_area_telefono());
				csvOutput.write(log.getDescripcion_modalidad());
				csvOutput.write(log.getEdad());
				csvOutput.write(log.getEmali());
				csvOutput.write(log.getEntidad());
				csvOutput.write(log.getEstado_tramite());
				csvOutput.write(log.getFec_emision());
				csvOutput.write(log.getFec_nacimiento());
				csvOutput.write(log.getFec_seleccion_modalidad());
				csvOutput.write(log.getFec_solicitud_tramite());
				csvOutput.write(log.getFecha_de_pago());
				csvOutput.write(log.getInd_solicita_pago_preliminar());
				csvOutput.write(log.getNombre());
				csvOutput.write(log.getNum_tramite());

				csvOutput.write(log.getPaso());

				csvOutput.write(log.getSexo());
				csvOutput.write(log.getTelefono());
				csvOutput.write(log.getForma_de_pago());
				csvOutput.write(log.getTipo_modalidad());
				csvOutput.write(log.getTipo_seleccion());
				csvOutput.write(log.getTipo_tramite());
				// csvOutput.write(log.getTipo_tramite_pension());

				csvOutput.write(log.getHist_sms_etp_1());
				csvOutput.write(log.getHist_sms_etp_2());
				csvOutput.write(log.getHist_sms_etp_4());
				csvOutput.write(log.getHist_sms_etp_5());
				csvOutput.write(log.getSms_codigo());
				csvOutput.write(log.getSms_status());
				csvOutput.write(Integer.toString(log.getSms_etapa()));
				csvOutput.endRecord();
			}

			csvOutput.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}// fin logSMS

	private String validaArchivo() throws IOException {
		String nombreDeArchivoLog = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_1\\LogSms.csv";

		boolean alreadyExists = new File(nombreDeArchivoLog).exists();

		if (alreadyExists) {
			copyArchivoLog();
			File ficheroLog = new File(nombreDeArchivoLog);
			ficheroLog.delete();
		}

		return nombreDeArchivoLog;
	}

	private int archivoEtapa(String archivo) {
		int tipoArchivo = 0;
		if (archivo.contains("ETAPA_1")) {
			tipoArchivo = 1;

		} else if (archivo.contains("ETAPA_2")) {
			tipoArchivo = 2;
		} else if (archivo.contains("ETAPA_4")) {
			tipoArchivo = 4;
		} else if (archivo.contains("ETAPA_5")) {
			tipoArchivo = 5;
		}
		return tipoArchivo;
	}

	private boolean validarCelular(String codigo, String celular) {
		boolean result = false;
		int valor = 0;
		if (celular.length() == 8) {
			valor++;
		}
		if (codigo.contains("9")) {
			valor++;
		}
		if (valor == 2) {
			result = true;
		}

		return result;
	}

	private String validarEmail(String email, String rut, String nombre) {
		String result = null;

		if (email == null || email.equals("NT@NT.CL") || email.equals("")) {
			// result= nombre+"@"+rut+".cl";
			result = rut + "@NOEXISTE.INFO";
		} else {
			result = email;
		}

		return result;
		// return email;
	}

	private String CallSendSms(String numero, String mensaje) {
		String response = null;
		String codigo_response = null;
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String cadenaFecha = formato.format(fechaActual);
		response = "Fecha Envio SMS: " + cadenaFecha + ", SMS Enviado:" + mensaje;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		INCREMENTADOR++;
		System.out.println("Registro: " + INCREMENTADOR + " Celular: " + numero + " Etapa: " + NUM_ETAPA);
		JerseyClientGet client = new JerseyClientGet();
		codigo_response = client.callSendJersey(numero, mensaje);
		return response + "|" + codigo_response;
	}

	private String converFecha(String fecha, String formatofecha) throws ParseException {

		SimpleDateFormat formato = new SimpleDateFormat(formatofecha);
		Date cadenaFecha = formato.parse(fecha);
		String resultFecha = new SimpleDateFormat("dd-MM-yyyy").format(cadenaFecha);

		return resultFecha;
	}

	private String getFechaArchivos() {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		int dia_semana = 0;
		dia_semana = cal.get(Calendar.DAY_OF_WEEK);
		String cadenaFecha_ayer = null;

		if (dia_semana == 2) {
			cal.add(Calendar.DATE, -3);
		} else {
			cal.add(Calendar.DATE, -1);
		}
		cadenaFecha_ayer = formato.format(cal.getTime());

		return cadenaFecha_ayer;
	}

	public void copyArchivoLog() throws IOException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String cadenaFecha_ayer = formato.format(cal.getTime());
		String nombreDeArchivoLog = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_1\\LogSms.csv";
		String destinoDeArchivoLog = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_1\\HistoricoLogSms\\";

		try {

			File archivo = new File(nombreDeArchivoLog);
			InputStream in = new FileInputStream(archivo);
			OutputStream out = new FileOutputStream(
					destinoDeArchivoLog + archivo.getName().replace(".csv", "") + "_" + cadenaFecha_ayer + ".csv");

			byte[] b = new byte[1024];
			int l;
			while ((l = in.read(b)) > 0) {
				out.write(b, 0, l);
			}
			out.flush();
			out.close();
			in.close();
			// archivo.delete();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String sendSMSBulk(File inFile) {
		String retValue = "";
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retValue;
	}

	private static boolean isFeriado(String fecha) {
		boolean retValue = false;

		try {

			JerseyClientGet ws = new JerseyClientGet();
			String response = ws.callSendJerseyFeriados();
			retValue = response.contentEquals(fecha);

		} catch (Exception e) {
			retValue = false;
		}
		return retValue;
	}
}
