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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.csvreader.CsvWriter;

public class EnviarSms {

	private static final String PATH_SFTP_ELOQUA = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_2\\LogSmsValido.csv";
	private static final String PATH_ARCHIVO_ENVIO = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_0\\EnvioSms.csv";
	private static final String PATH_ARCHIVO_1 = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_1\\LogSms.csv";
	private static final String SEPARADOR = ";";
	private static int INCREMENTADOR = 0;
	private static int NUMERO_ETAPA = 0;

	/*
	 * public static void main(String[] args) throws IOException, ParseException {
	 * EnviarSms s = new EnviarSms(); Date fechaActual_1 = new Date();
	 * SimpleDateFormat formato_1 = new SimpleDateFormat("yyyy-MM-dd"); String
	 * cadenaFecha_1 = formato_1.format(fechaActual_1);
	 * 
	 * // Valida Feriado if (!isFeriado(cadenaFecha_1)) { //
	 * System.out.println("No Feriado:: "+cadenaFecha_1);
	 * 
	 * // Valida Fin de samana if (!s.isEndWeek()) {
	 * 
	 * //System.out.println("Paso bien"); s.loadFile(); ValidaStatusSms valida = new
	 * ValidaStatusSms(); valida.ejecutar(); } else {
	 * 
	 * // mandar a eliminar los archivos de subida al eloqua
	 * s.copyArchivoLogValido(); System.out.println("Fin de Semana :: " +
	 * cadenaFecha_1); } ;
	 * 
	 * } else {
	 * 
	 * // mandar a eliminar los archivos de subida al eloqua
	 * s.copyArchivoLogValido(); System.out.println("Feriado :: " + cadenaFecha_1);
	 * 
	 * }
	 * 
	 * }
	 */

	public void enviarSms() throws IOException {

		Date fechaActual_1 = new Date();
		SimpleDateFormat formato_1 = new SimpleDateFormat("yyyy-MM-dd");
		String cadenaFecha_1 = formato_1.format(fechaActual_1);

		// Valida Feriado
		if (!isFeriado(cadenaFecha_1)) {
			// System.out.println("No Feriado:: "+cadenaFecha_1);

			// Valida Fin de samana
			if (!isEndWeek()) {

				// System.out.println("Paso bien");
				loadFile();
				ValidaStatusSms valida = new ValidaStatusSms();
				valida.ejecutar();
			} else {

				// mandar a eliminar los archivos de subida al eloqua
				copyArchivoLogValido();
				System.out.println("Fin de Semana :: " + cadenaFecha_1);
			}
			

		} else {

			// mandar a eliminar los archivos de subida al eloqua
			copyArchivoLogValido();
			System.out.println("Feriado :: " + cadenaFecha_1);

		}

	}

	public void loadFile() {

		List<ArchivoEloqua> lis_ArchivoEluqua = new ArrayList<ArchivoEloqua>();

		BufferedReader bufferLectura = null;
		try {

			String pathAchivoEnvio = PATH_ARCHIVO_ENVIO;

			boolean alreadyExists = new File(pathAchivoEnvio).exists();

			if (alreadyExists) {

				bufferLectura = new BufferedReader(new FileReader(PATH_ARCHIVO_ENVIO));

				// Leer una linea del archivo
				String linea = bufferLectura.readLine();
				linea = bufferLectura.readLine();
				while (linea != null) {
					String respuesta = null;
					String codigo = null;
					String mensaje = null;
					String[] campos = linea.split(SEPARADOR);

					ArchivoEloqua logSms = new ArchivoEloqua();

					Date fechaActual_1 = new Date();
					SimpleDateFormat formato_1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					String cadenaFecha_1 = formato_1.format(fechaActual_1);
					int tipoArchivo = Integer.parseInt(campos[28]);
					NUMERO_ETAPA = tipoArchivo;

					String[] lisnombres_4 = campos[19].split(" ");

					logSms.setPeriodo("");
					logSms.setRut(campos[1]);
					logSms.setAp_materno(campos[2]);
					logSms.setAp_paterno(campos[3]);
					logSms.setCelular(campos[4]);

					logSms.setCod_agencia_solicitud(campos[5]);
					logSms.setCodigo_area_celular(campos[6]);
					logSms.setCodigo_area_telefono(campos[7]);
					logSms.setDescripcion_modalidad(campos[8]);
					logSms.setEdad(campos[9]);

					logSms.setEmali(campos[10]);
					logSms.setEntidad(campos[11]);
					logSms.setEstado_tramite(campos[12]);

					logSms.setFec_nacimiento(converFecha(campos[14], "yyyy-MM-dd"));

					// logSms.setFecha_de_pago(converFecha(campos[17], "yyyy-MM-dd"));
					logSms.setFecha_de_pago("");
					logSms.setInd_solicita_pago_preliminar(campos[18]);
					logSms.setNombre(lisnombres_4[0]);

					logSms.setNum_tramite(campos[20]);
					logSms.setPaso(campos[21]);
					logSms.setSexo(campos[22]);
					logSms.setTelefono(campos[23]);
					logSms.setForma_de_pago(campos[24]);

					logSms.setTipo_modalidad(campos[25]);
					logSms.setTipo_seleccion(campos[26]);
					logSms.setTipo_tramite(campos[27]);

					logSms.setHist_sms_etp_5("");

					logSms.setSms_etapa(Integer.parseInt(campos[28]));

					switch (tipoArchivo) {
					case 4:

						logSms.setFec_seleccion_modalidad(converFecha(campos[15], "yyyy-MM-dd"));

						if (!validarCelular(campos[6], campos[4])) {
							respuesta = "Numero no identificado. Fecha: " + cadenaFecha_1;
							if (campos[10].trim().equals("NT@NT.CL")) {
								logSms.setEmali("");
							}

						} else {
							logSms.setEmali(validarEmail(campos[10].trim(), campos[1], lisnombres_4[0].trim()));

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
										+ campos[11].trim();
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

							respuesta = CallSendSms(campos[6] + campos[4], mensaje);

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
						logSms.setFec_emision(converFecha(campos[13], "yyyy-MM-dd"));
						if (!validarCelular(campos[6], campos[4])) {

							respuesta = "Numero no identificado. Fecha: " + cadenaFecha_1;
							if (campos[10].trim().equals("NT@NT.CL")) {
								logSms.setEmali("");
							}
						} else {
							logSms.setEmali(validarEmail(campos[10].trim(), campos[1], lisnombres_4[0].trim()));
							mensaje = "AFP CAPITAL: " + lisnombres_4[0].trim()
									+ ", SU CERTIFICADO DE SALDO ESTA DISPONIBLE (VIGENCIA 35 DIAS). ACERQUESE A CUALQUIER SUCURSAL PARA SEGUIR CON SU TRAMITE DE PENSION.";

							respuesta = CallSendSms(campos[6] + campos[4], mensaje);

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

						logSms.setFec_solicitud_tramite(converFecha(campos[16], "yyyy-MM-dd"));
						if (!validarCelular(campos[6], campos[4])) {
							respuesta = "Numero no identificado. Fecha: " + cadenaFecha_1;
							if (campos[10].trim().equals("NT@NT.CL")) {
								logSms.setEmali("");

							}

						} else {
							logSms.setEmali(validarEmail(campos[10].trim(), campos[1], lisnombres_4[0].trim()));
							mensaje = "AFP CAPITAL: " + lisnombres_4[0].trim() + ", LE INFORMAMOS QUE CON FECHA "
									+ converFecha(campos[16], "yyyy-MM-dd")
									+ " INICIO SU TRAMITE DE PENSION. LE COMUNICAREMOS LAS SIGUIENTES ETAPAS DE ESTE TRAMITE.";

							respuesta = CallSendSms(campos[6] + campos[4], mensaje);

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
			} // fin si exite archivo
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Problemas con el archivo Proceso 0");
		} finally {
			if (bufferLectura != null) {
				try {
					bufferLectura.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			writeFile(lis_ArchivoEluqua);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		System.out.println("Registro: " + INCREMENTADOR + " Celular: " + numero + " Etapa: " + NUMERO_ETAPA);
		JerseyClientGet client = new JerseyClientGet();
		codigo_response = client.callSendJersey(numero, mensaje);
		return response + "|" + codigo_response;
	}

	private String converFecha(String fecha, String formatofecha) {

		String resultFecha = null;
		try {
			SimpleDateFormat formato = new SimpleDateFormat(formatofecha);
			Date cadenaFecha = formato.parse(fecha);
			resultFecha = new SimpleDateFormat("dd-MM-yyyy").format(cadenaFecha);
		} catch (Exception e) {
			System.out.println("Formato de Fecha Incorrecta: " + formatofecha + ", :" + fecha);
			return resultFecha = "";
		}
		return resultFecha;
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

	private String validaArchivo() {
		String nombreDeArchivoLog = PATH_ARCHIVO_1;

		try {
			boolean alreadyExists = new File(nombreDeArchivoLog).exists();
			if (alreadyExists) {
				copyArchivoLog();
				File ficheroLog = new File(nombreDeArchivoLog);
				ficheroLog.delete();
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Problemas con el Archivo Proceso 1");
			return nombreDeArchivoLog = "";
		}

		return nombreDeArchivoLog;
	}

	private static boolean isFeriado(String fecha) {
		boolean retValue = false;

		try {

			JerseyClientGet ws = new JerseyClientGet();
			String response = ws.callSendJerseyFeriados();

			retValue = response.contains(fecha);

		} catch (Exception e) {
			retValue = false;
		}
		return retValue;
	}

	private boolean isEndWeek() {
		boolean retValue = false;

		try {

			Calendar cal = Calendar.getInstance();
			int dia_semana = 0;
			dia_semana = cal.get(Calendar.DAY_OF_WEEK);
			// System.out.println("dia: "+dia_semana);
			if (dia_semana == 1 || dia_semana == 7) {

				retValue = true;
			}
		} catch (Exception e) {
			retValue = false;
		}
		return retValue;
	}

	public void copyArchivoLogValido() throws IOException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String cadenaFecha_ayer = formato.format(cal.getTime());
		String nombreDeArchivoLogValido = PATH_SFTP_ELOQUA;
		String destinoDeArchivoLogValido = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_2\\HistoricoLogSmsValido\\";

		try {
			boolean alreadyExists = new File(nombreDeArchivoLogValido).exists();

			if (alreadyExists) {

				File archivoValido = new File(nombreDeArchivoLogValido);
				InputStream invalido = new FileInputStream(archivoValido);
				OutputStream outValido = new FileOutputStream(destinoDeArchivoLogValido
						+ archivoValido.getName().replace(".csv", "") + "_" + cadenaFecha_ayer + ".csv");

				byte[] bv = new byte[1024];
				int lv;
				while ((lv = invalido.read(bv)) > 0) {
					outValido.write(bv, 0, lv);
				}
				outValido.flush();
				outValido.close();
				invalido.close();
				DeleteArchivoLogValida();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Problemas con copia del archivo LogSmsValido.csv");
		}

	}

	public void DeleteArchivoLogValida() {
		String nombreDeArchivoLog = PATH_SFTP_ELOQUA;

		try {
			boolean alreadyExists = new File(nombreDeArchivoLog).exists();

			if (alreadyExists) {
				File ficheroLog = new File(nombreDeArchivoLog);
				ficheroLog.delete();
			}

		} catch (Exception e) {
			System.out.println("Problemas con el archivo LogSmsValido.csv");

		}
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

	public void copyArchivoLog() throws IOException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String cadenaFecha_ayer = formato.format(cal.getTime());
		String nombreDeArchivoLog = PATH_ARCHIVO_1;
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

}
