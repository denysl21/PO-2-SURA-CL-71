package SuraArchivoCl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.csvreader.CsvWriter;

public class UnirArchivo {

	private static final String PATH_FTP = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Historicos_FTP\\";
	private static final String PATH_ARCHIVO_ENVIO = "C:\\SFTP_ExpertProject\\SURA_TRAMITE_PENSIONES\\Proceso_0\\EnvioSms.csv";
	private static final String SEPARADOR = ";";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		UnirArchivo a = new UnirArchivo();

		System.out.println(a.validaArchivo());
		System.out.println(a.getFechaArchivos());
		try {
			a.leerAllFiles();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EnviarSms e = new EnviarSms();
		e.enviarSms();

	}

	public void leerAllFiles() throws ParseException {
		String cadenaFecha_ayer = null;
		cadenaFecha_ayer = getFechaArchivos();

		File folder = new File(PATH_FTP);
		String files = null;
		File[] listOfFiles = folder.listFiles();
		List<ArchivoEloqua> lis_ArchivoEluqua = new ArrayList<ArchivoEloqua>();
		// Consulto si el archivo Existe
		if (validaArchivo()) {
			// Obtengo la lista del archivo
			System.out.println(validaArchivo());
			ArchivoEloqua logSms = null;

			BufferedReader bufferLectura_existe = null;
			try {
				bufferLectura_existe = new BufferedReader(new FileReader(PATH_ARCHIVO_ENVIO));

				String linea_existe = bufferLectura_existe.readLine();
				linea_existe = bufferLectura_existe.readLine();
				while (linea_existe != null) {
					logSms = new ArchivoEloqua();
					String[] campos = linea_existe.split(SEPARADOR);

					logSms.setPeriodo("");
					logSms.setRut(campos[1] != null ? campos[1] : "");
					logSms.setAp_materno(campos[2] != null ? campos[2] : "");
					logSms.setAp_paterno(campos[3] != null ? campos[3] : "");
					logSms.setCelular(campos[4] != null ? campos[4] : "");
					logSms.setCod_agencia_solicitud(campos[5] != null ? campos[5] : "");
					logSms.setCodigo_area_celular(campos[6] != null ? campos[6] : "");
					logSms.setCodigo_area_telefono(campos[7] != null ? campos[7] : "");
					logSms.setDescripcion_modalidad(campos[8] != null ? campos[8] : "");
					logSms.setEdad(campos[9] != null ? campos[9] : "");
					logSms.setEmali(campos[10] != null ? campos[10] : "");
					logSms.setEntidad(campos[11] != null ? campos[11] : "");
					logSms.setEstado_tramite(campos[12] != null ? campos[12] : "");
					logSms.setFec_emision(campos[13] != null ? campos[13] : "");
					logSms.setFec_nacimiento(campos[14] != null ? campos[14] : "");
					logSms.setFec_seleccion_modalidad(campos[15] != null ? campos[15] : "");
					logSms.setFec_solicitud_tramite(campos[16] != null ? campos[16] : "");
					logSms.setFecha_de_pago(campos[17] != null ? campos[17] : "");
					logSms.setInd_solicita_pago_preliminar(campos[18] != null ? campos[18] : "");
					logSms.setNombre(campos[19] != null ? campos[19] : "");
					logSms.setNum_tramite(campos[20] != null ? campos[20] : "");
					logSms.setPaso(campos[21] != null ? campos[21] : "");
					logSms.setSexo(campos[22] != null ? campos[22] : "");
					logSms.setTelefono(campos[23] != null ? campos[23] : "");
					logSms.setForma_de_pago(campos[24] != null ? campos[24] : "");
					logSms.setTipo_modalidad(campos[25] != null ? campos[25] : "");
					logSms.setTipo_seleccion(campos[26] != null ? campos[26] : "");
					logSms.setTipo_tramite(campos[27] != null ? campos[27] : "");
					logSms.setSms_etapa(Integer.parseInt(campos[28]));

					lis_ArchivoEluqua.add(logSms);
					linea_existe = bufferLectura_existe.readLine();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bufferLectura_existe != null) {
					try {
						bufferLectura_existe.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				// System.out.println(files);
				if (cadenaFecha_ayer != null && files.contains(cadenaFecha_ayer)) {

					if (!files.equals("FF_TRACKING_ETAPA_5_" + cadenaFecha_ayer + ".csv")
							|| files.equals("HistoricoLogSms")) {
						// System.out.println(files);
						BufferedReader bufferLectura = null;
						try {

							bufferLectura = new BufferedReader(new FileReader(PATH_FTP + files));

							int tipoArchivo = archivoEtapa(files);
							// Leer una linea del archivo
							String linea = bufferLectura.readLine();
							linea = bufferLectura.readLine();
							while (linea != null) {

								String[] campos = linea.split(SEPARADOR);

								ArchivoEloqua logSms = new ArchivoEloqua();
								// System.out.println("::"+campos[0]);

								switch (tipoArchivo) {
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
			try {
				writeFile(lis_ArchivoEluqua);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private boolean validaArchivo() {
		String nombreDeArchivoLog = PATH_ARCHIVO_ENVIO;
		boolean result = false;
		try {

			boolean alreadyExists = new File(nombreDeArchivoLog).exists();

			if (alreadyExists) {
				result = true;
			}

		} catch (Exception e) {
			System.out.println("Problemas al Buscando archivos a unir");
			result = false;
		}

		return result;
	}

	private String getFechaArchivos() {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String cadenaFecha_ayer = null;

		cal.add(Calendar.DATE, -1);

		cadenaFecha_ayer = formato.format(cal.getTime());

		return cadenaFecha_ayer;
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

	private String converFecha(String fecha, String formatofecha) throws ParseException {

		SimpleDateFormat formato = new SimpleDateFormat(formatofecha);
		Date cadenaFecha = formato.parse(fecha);
		String resultFecha = new SimpleDateFormat("dd-MM-yyyy").format(cadenaFecha);

		return resultFecha;
	}

	private void writeFile(List<ArchivoEloqua> lis_ArchivoEloqua) throws IOException {
		String archivoLog = validaEscribe();
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

				csvOutput.write(Integer.toString(log.getSms_etapa()));
				csvOutput.endRecord();
			}

			csvOutput.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}// fin logSMS

	private String validaEscribe() {
		String nombreDeArchivoLog = PATH_ARCHIVO_ENVIO;
		try {

			boolean alreadyExists = new File(nombreDeArchivoLog).exists();

			if (alreadyExists) {
				File ficheroLog = new File(nombreDeArchivoLog);
				ficheroLog.delete();
			}

		} catch (Exception e) {
			System.out.println("Problemas con el archivo LogSms.csv");
			nombreDeArchivoLog = "";
		}

		return nombreDeArchivoLog;
	}

}
