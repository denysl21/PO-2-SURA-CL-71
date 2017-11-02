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

public class ValidaStatusSms {
	private static String path_logSms = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_1\\";
	private static final String SEPARADOR_COMA = ";";

	/*
	 * public static void main(String[] args) throws IOException, ParseException {
	 * 
	 * ejecutar(); }
	 */
	public void ejecutar() {
		setTimeout(() -> {
			try {
				leerAllFileVerificacion();
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}, 50);
	}

	public void setTimeout(Runnable runnable, int delay) {
		new Thread(() -> {
			try {
				Thread.sleep(delay);
				runnable.run();
			} catch (Exception e) {
				System.err.println(e);
			}
		}).start();
	}

	public void leerAllFileVerificacion() throws IOException, ParseException, NumberFormatException, JSONException {
		try {
			File folder = new File(path_logSms);
			String files = null;
			File[] listOfFiles = folder.listFiles();

			List<ArchivoEloqua> lis_ArchivoEluqua = new ArrayList<ArchivoEloqua>();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					files = listOfFiles[i].getName();

					if (files.equals("LogSms.csv")) {

						BufferedReader bufferLectura = null;
						try {

							bufferLectura = new BufferedReader(new FileReader(path_logSms + files));

							// Leer una linea del archivo
							String linea = bufferLectura.readLine();
							linea = bufferLectura.readLine();

							while (linea != null) {

								String[] campos = linea.split(SEPARADOR_COMA);

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
								logSms.setHist_sms_etp_1("");
								logSms.setHist_sms_etp_2("");
								logSms.setHist_sms_etp_4("");
								logSms.setHist_sms_etp_5("");

								logSms.setSms_etapa(Integer.parseInt(campos[34]));

								logSms.setSms_codigo("");

								logSms.setSms_status("");

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
						writeFile(lis_ArchivoEluqua);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		String nombreDeArchivoLog = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_2\\LogSmsValido.csv";

		boolean alreadyExists = new File(nombreDeArchivoLog).exists();

		if (alreadyExists) {
			copyArchivoLogValido();
			File ficheroLog = new File(nombreDeArchivoLog);
			ficheroLog.delete();
		}

		return nombreDeArchivoLog;
	}

	public void copyArchivoLogValido() throws IOException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String cadenaFecha_ayer = formato.format(cal.getTime());
		String nombreDeArchivoLogValido = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_2\\LogSmsValido.csv";
		String destinoDeArchivoLogValido = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_2\\HistoricoLogSmsValido\\";

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
			// DeleteArchivoLogValida();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void DeleteArchivoLogValida() {
		String nombreDeArchivoLog = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_1\\LogSms.csv";
		boolean alreadyExists = new File(nombreDeArchivoLog).exists();

		if (alreadyExists) {
			File ficheroLog = new File(nombreDeArchivoLog);
			ficheroLog.delete();
		}
	}
}
