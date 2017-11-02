package SuraArchivoCl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebServiceEloqua {

	private static final String WSELOQUALISTCONTACT = "https://secure.p04.eloqua.com/api/REST/1.0/data/contacts";
	private static final String WSELOQUADETAILTCONTACT = "https://secure.p04.eloqua.com/api/REST/1.0/data/contact";
	private static final String WSELOQUAEMAILCONT = "https://secure.p04.eloqua.com/api/REST/2.0/data/contacts?depth=complete&search=name=";

	private static final String APPKEY = "Basic U1VSQVxkZW55cy5sb3BlejpFcGJAMTIzNDU2";

	private static final String HIST_01 = "100217";
	private static final String HIST_02 = "100218";
	private static final String HIST_03 = "100219";
	private static final String HIST_04 = "100220";
	private static final String STATUS_SMS = "100235";
	private static final String CAM_FECHA_CARGA = "100243";

	/*
	 * public static void main(String[] args) throws IOException, JSONException {
	 * WebServiceEloqua p= new WebServiceEloqua();
	 * p.updateHistorialSms(1,"25375213-0","IRMA@GRUPOEPB.COM","SMS dfhdf","Envo");
	 * 
	 * }
	 */

	public void updateHistorialSms(int etapa, String rut, String email, String sms, String status)
			throws IOException, JSONException {

		WebServiceEloqua his = new WebServiceEloqua();
		String json_contacto = his.getIdContacto(email);
		// System.out.println(json_contacto);
		JSONObject Jobject = new JSONObject(json_contacto);
		JSONArray Jarray = Jobject.getJSONArray("elements");

		// For datos 1
		for (int i = 0; i < Jarray.length(); i++) {
			JSONObject object = Jarray.getJSONObject(i);

			String id_contacto = object.getString("id");

			// System.out.println(id_contacto);

			JSONArray Jarray_d = object.getJSONArray("fieldValues");

			// for datos 2
			for (int a = 0; a < Jarray_d.length(); a++) {
				JSONObject object_d = Jarray_d.getJSONObject(a);
				String contacto_detail = object_d.getString("id");
				// System.out.println(contacto_detail);
				String contacto_detail_mensaje = null;

				switch (etapa) {
				case 1:
					if (contacto_detail.equals(HIST_01)) {

						contacto_detail_mensaje = object_d.has("value") ? object_d.getString("value") : "";
						if (!contacto_detail_mensaje.equals("")) {
							sms = contacto_detail_mensaje.trim() + "\n\n" + sms.trim();
							// System.out.println(sms);
							// System.out.println("SMS Antiguo :: "+ sms);
						}
						UpdateDetailContacto(id_contacto, email, HIST_01, sms, status);
						// System.out.println("id: "+id_contacto+" email: "+email+"Historico:
						// "+HIST_01+"Sms: "+sms+"Status: "+status);
						// System.out.println("Actualizado Etapa 1 :: "+ email);

					}
					break;
				case 2:
					if (contacto_detail.equals(HIST_02)) {

						contacto_detail_mensaje = object_d.has("value") ? object_d.getString("value") : "";
						if (!contacto_detail_mensaje.equals("")) {
							sms = contacto_detail_mensaje.trim() + "\n\n" + sms.trim();
							// System.out.println("SMS Antiguo :: "+ sms);
						}
						UpdateDetailContacto(id_contacto, email, HIST_02, sms, status);
						// System.out.println("id: "+id_contacto+" email: "+email+"Historico:
						// "+HIST_02+"Sms: "+sms+"Status: "+status);
						// System.out.println("Actualizado Etapa 2 :: "+ email);
					}
					break;
				case 3:
					if (contacto_detail.equals(HIST_03)) {
						contacto_detail_mensaje = object_d.has("value") ? object_d.getString("value") : "";
						if (!contacto_detail_mensaje.equals("")) {
							sms = contacto_detail_mensaje.trim() + "\n\n" + sms.trim();
							// System.out.println("SMS Antiguo :: "+ sms);
						}
						UpdateDetailContacto(id_contacto, email, HIST_03, sms, status);
						// System.out.println("Actualizado Etapa 3 :: "+ email);
					}
					break;
				case 4:
					if (contacto_detail.equals(HIST_04)) {
						contacto_detail_mensaje = object_d.has("value") ? object_d.getString("value") : "";
						if (!contacto_detail_mensaje.equals("")) {
							sms = contacto_detail_mensaje.trim() + "\n\n" + sms.trim();
							// System.out.println("SMS Antiguo :: "+ sms);
						}
						UpdateDetailContacto(id_contacto, email, HIST_04, sms, status);
						// System.out.println("id: "+id_contacto+" email: "+email+"Historico:
						// "+HIST_04+"Sms: "+sms+"Status: "+status);
						// System.out.println("Actualizado Etapa 4 :: "+ email);
					}
					break;

				}

			} // fin for datos 2

		} // fin for datos 1

	}

	public String getListaContactos() throws IOException {

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(WSELOQUALISTCONTACT).get().addHeader("authorization", APPKEY)
				.addHeader("cache-control", "no-cache").build();

		Response response = client.newCall(request).execute();

		return response.body().string();
	}

	public String getDetailContacto(String id) throws IOException {

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(WSELOQUADETAILTCONTACT + "/" + id).get()
				.addHeader("authorization", APPKEY).addHeader("cache-control", "no-cache").build();

		Response response = client.newCall(request).execute();

		return response.body().string();
	}

	public String getIdContacto(String email) throws IOException {

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(WSELOQUAEMAILCONT + email).get().addHeader("authorization", APPKEY)
				.addHeader("cache-control", "no-cache").build();

		Response response = client.newCall(request).execute();

		return response.body().string();
	}

	public String UpdateDetailContacto(String id, String email, String id_field, String SMS, String status)
			throws IOException {

		// System.out.println("Paso:: id: " + id + " email: " + email + " Historico: " +
		// id_field + " Sms: " + SMS
		// + " Status: " + status);
		// return "prueba";

		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");

		RequestBody body = RequestBody.create(mediaType, "{\n  \"emailAddress\":\"" + email + "\",\n  \"id\": \"" + id
				+ "\",\n    \"fieldValues\":[\n {\n  \"type\": \"FieldValue\",\n  \"id\": \"" + id_field
				+ "\",\n  \"value\": \"" + SMS + "\"\n  },\n  {\n  \"type\":\"FieldValue\",\n  \"id\":\"" + STATUS_SMS
				+ "\",\n    \"value\": \"" + status + "\"\n },\n  {\n  \"type\":\"FieldValue\",\n  \"id\":\""
				+ CAM_FECHA_CARGA + "\",\n    \"value\": \"" + getFechaActual() + "\"\n }\n  ]\n}\");");

		Request request = new Request.Builder().url(WSELOQUADETAILTCONTACT + "/" + id).put(body)
				.addHeader("content-type", "application/json").addHeader("authorization", APPKEY)
				.addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "7080a124-a8f3-21db-1c36-30e0efcf1da7").build();

		Response response = client.newCall(request).execute();

		return response.body().string();

	}

	public String getFechaActual() {

		Date fechaActual_1 = new Date();
		SimpleDateFormat formato_1 = new SimpleDateFormat("dd/MM/yyyy");
		String cadenaFecha_1 = formato_1.format(fechaActual_1);

		return cadenaFecha_1;
	}

}
