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
import java.util.List;

import org.json.JSONException;

import com.csvreader.CsvWriter;

public class UpdateContact {

	private static String PATH_ARCHIVO_1 = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_1\\LogSms.csv";
	private static String PATH_ARCHIVO_2 = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_2\\LogSmsValido.csv";
	private static String PATH_ARCHIVO_0 = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_0\\EnvioSms.csv";
	private static String PATH_HISTORICO_0 = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_0\\HistoricoEnvioSms\\";
	private static String PATH_ACHIVO_3 = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_3\\LogUpdateSms.csv";
	private static String PATH_HISTORICO_3 = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_3\\HistoricoLogUpdateSms\\";
	private static final String SEPARADOR_COMA = ";";
	private static int INCREMENTADOR = 0;

	public static void main(String[] args) throws NumberFormatException, IOException, ParseException, JSONException {
		// TODO Auto-generated method stub

		try {

			String pathAchivoEnvio = PATH_ARCHIVO_2;

			boolean alreadyExists = new File(pathAchivoEnvio).exists();

			if (alreadyExists) {

				UpdateContact v = new UpdateContact();
				v.ejecutar();

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Problemas al actualizar eloqua: " + PATH_ARCHIVO_2);
		}

	}

	public void ejecutar() throws NumberFormatException, IOException, ParseException, JSONException {
		try {
			Thread.sleep(5000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (leerAllFileVerificacion()) {
			copyArchivoEnvio();
		}

	}

	public boolean leerAllFileVerificacion() throws IOException, ParseException, NumberFormatException, JSONException {
		boolean result = false;

		try {
			List<ArchivoEloqua> lis_ArchivoEluqua = new ArrayList<ArchivoEloqua>();

			BufferedReader bufferLectura = null;
			try {

				String pathAchivoEnvio = PATH_ARCHIVO_1;

				boolean alreadyExists = new File(pathAchivoEnvio).exists();

				if (alreadyExists) {

					bufferLectura = new BufferedReader(new FileReader(pathAchivoEnvio));

					// Leer una linea del archivo
					String linea = bufferLectura.readLine();
					linea = bufferLectura.readLine();

					while (linea != null) {
						String respuesta = null;

						String[] campos = linea.split(SEPARADOR_COMA);

						// System.out.println(campos[32]);
						ArchivoEloqua logSms = new ArchivoEloqua();

						logSms.setPeriodo(campos[0]);
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
						logSms.setFec_emision(campos[13]);
						logSms.setFec_nacimiento(campos[14]);
						logSms.setFec_seleccion_modalidad(campos[15]);
						logSms.setFec_solicitud_tramite(campos[16]);
						logSms.setFecha_de_pago(campos[17]);
						logSms.setInd_solicita_pago_preliminar(campos[18]);
						logSms.setNombre(campos[19]);
						logSms.setNum_tramite(campos[20]);
						logSms.setPaso(campos[21]);
						logSms.setSexo(campos[22]);
						logSms.setTelefono(campos[23]);
						logSms.setForma_de_pago(campos[24]);
						logSms.setTipo_modalidad(campos[25]);
						logSms.setTipo_seleccion(campos[26]);
						logSms.setTipo_tramite(campos[27]);
						logSms.setHist_sms_etp_1(campos[28]);
						logSms.setHist_sms_etp_2(campos[29]);
						logSms.setHist_sms_etp_4(campos[30]);
						logSms.setHist_sms_etp_5(campos[31]);

						logSms.setSms_etapa(Integer.parseInt(campos[34]));
						String SMS_Actual = null;
						String[] fecha_sms;

						if (!campos[32].trim().equals("")) {

							// System.out.println("32:"+campos[32]);
							logSms.setSms_codigo(campos[32]);
							respuesta = CallSendValidaSms(campos[32]);
							// System.out.println("respuesta:"+respuesta);
							if (respuesta != null && respuesta.equals("ENTREGA CONFIRMADA")) {
								switch (Integer.parseInt(campos[34])) {
								case 1:
									SMS_Actual = campos[28];

									break;
								case 2:
									SMS_Actual = campos[29];

									break;
								case 4:
									SMS_Actual = campos[30];

									break;
								case 5:
									SMS_Actual = campos[31];

									break;
								}

							} else {
								switch (Integer.parseInt(campos[34])) {
								case 1:
									fecha_sms = campos[28].split(" ");
									SMS_Actual = "ERROR ENVIO SMS: " + fecha_sms[3] + " "
											+ fecha_sms[4].replace(",", "").trim() + ".";
									break;
								case 2:
									fecha_sms = campos[29].split(" ");
									SMS_Actual = "ERROR ENVIO SMS: " + fecha_sms[3] + " "
											+ fecha_sms[4].replace(",", "").trim() + ".";
									break;
								case 4:
									fecha_sms = campos[30].split(" ");
									SMS_Actual = "ERROR ENVIO SMS: " + fecha_sms[3] + " "
											+ fecha_sms[4].replace(",", "").trim() + ".";
									break;
								case 5:
									fecha_sms = campos[31].split(" ");
									SMS_Actual = "ERROR ENVIO SMS: " + fecha_sms[3] + " "
											+ fecha_sms[4].replace(",", "").trim() + ".";
									break;
								}
							}

							logSms.setSms_status(respuesta);
						} else {
							// System.out.println("30:"+campos[30]);
							switch (Integer.parseInt(campos[34])) {
							case 1:
								if (campos[28].contains("Numero no identificado")) {
									SMS_Actual = campos[28].trim();
									respuesta = "Numero no identificado";
									logSms.setSms_status(respuesta);
								} else {

									fecha_sms = campos[28].split(" ");
									SMS_Actual = "ERROR ENVIO SMS, " + campos[33] + ": " + fecha_sms[3] + " "
											+ fecha_sms[4].replace(",", "").trim() + ".";
									respuesta = campos[33];
									logSms.setSms_status(campos[33]);
								}
								break;
							case 2:
								if (campos[29].contains("Numero no identificado")) {
									SMS_Actual = campos[29].trim();
									respuesta = "Numero no identificado";
									logSms.setSms_status(respuesta);
								} else {
									fecha_sms = campos[29].split(" ");
									SMS_Actual = "ERROR ENVIO SMS, " + campos[33] + ": " + fecha_sms[3] + " "
											+ fecha_sms[4].replace(",", "").trim() + ".";
									respuesta = campos[33];
									logSms.setSms_status(campos[33]);
								}
								break;
							case 4:
								if (campos[30].contains("Numero no identificado")) {
									SMS_Actual = campos[30].trim();
									respuesta = "Numero no identificado";
									logSms.setSms_status(respuesta);
								} else {
									fecha_sms = campos[30].split(" ");
									SMS_Actual = "ERROR ENVIO SMS, " + campos[33] + ": " + fecha_sms[3] + " "
											+ fecha_sms[4].replace(",", "").trim() + ".";
									respuesta = campos[33];
									logSms.setSms_status(campos[33]);
								}
								break;
							case 5:
								fecha_sms = campos[31].split(" ");
								SMS_Actual = "ERROR ENVIO SMS, " + campos[33] + ": " + fecha_sms[3] + " "
										+ fecha_sms[4].replace(",", "").trim() + ".";
								break;
							}

						}
						if (!campos[10].trim().equals("") || campos[10].trim().contains("NT@NT.CL")) {
							// llamando a historicos SMS
							INCREMENTADOR++;
							System.out.println("Registro: " + INCREMENTADOR + ", Email: " + campos[10] + ", Etapa: "
									+ campos[34] + ", Respuesta:" + respuesta + ", SMS:" + SMS_Actual);
							WebServiceEloqua historial = new WebServiceEloqua();
							historial.updateHistorialSms(Integer.parseInt(campos[34]), campos[1], campos[10],
									SMS_Actual, respuesta);
						}

						lis_ArchivoEluqua.add(logSms);

						linea = bufferLectura.readLine();
					}
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
			writeFile(lis_ArchivoEluqua);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	private void writeFile(List<ArchivoEloqua> lis_ArchivoEloqua) throws IOException {
		String archivoLog = validaArchivoLog();

		try {

			CsvWriter csvOutput = new CsvWriter(new FileWriter(archivoLog, true), ',');

			csvOutput.write("#PERIODO");
			csvOutput.write("ETAPA_1");
			csvOutput.write("RUT_1");
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
			csvOutput.write("SMS_ETAPA");
			csvOutput.write("SMS_CODIGO");
			csvOutput.write("SMS_STATUS");

			csvOutput.endRecord();

			for (ArchivoEloqua log : lis_ArchivoEloqua) {
				csvOutput.write(log.getPeriodo());
				csvOutput.write(log.getPaso());
				csvOutput.write(log.getRut());
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
				csvOutput.write(Integer.toString(log.getSms_etapa()));
				csvOutput.write(log.getSms_codigo());
				csvOutput.write(log.getSms_status());
				csvOutput.endRecord();
			}

			csvOutput.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("Salio");

	}// fin logSMS

	private String validaArchivoLog() throws IOException {
		String nombreDeArchivoLog = PATH_ACHIVO_3;

		try {

			boolean alreadyExists = new File(nombreDeArchivoLog).exists();

			if (alreadyExists) {
				copyArchivoLogValido();
				File ficheroLog = new File(nombreDeArchivoLog);
				ficheroLog.delete();
			}

		} catch (Exception e) {
			System.out.println("Problema con el archivo " + PATH_ACHIVO_3);
			nombreDeArchivoLog = "";
		}

		return nombreDeArchivoLog;
	}

	private String CallSendValidaSms(String key) {
		String response = null;

		try {
			JerseyClientGet client = new JerseyClientGet();
			response = client.verySendJersey(key);

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Problemas con JerseyClientGet al validar sms");
			response = "";
		}

		return response;
	}

	public void copyArchivoLogValido() throws IOException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String cadenaFecha_ayer = formato.format(cal.getTime());
		String nombreDeArchivoLogValido = PATH_ACHIVO_3;
		String destinoDeArchivoLogValido = PATH_HISTORICO_3;

		try {

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

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void DeleteArchivoLogValida() {
		String nombreDeArchivoLog = PATH_ARCHIVO_1;

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

	public void copyArchivoEnvio() throws IOException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String cadenaFecha_ayer = formato.format(cal.getTime());
		String nombreDeArchivoLogValido = PATH_ARCHIVO_0;
		String destinoDeArchivoLogValido = PATH_HISTORICO_0;

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
				DeleteArchivoEnvio();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void DeleteArchivoEnvio() {
		String nombreDeArchivoLog = PATH_ARCHIVO_0;

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

}
