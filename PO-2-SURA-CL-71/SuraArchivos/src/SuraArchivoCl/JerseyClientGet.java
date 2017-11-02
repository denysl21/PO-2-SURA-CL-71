package SuraArchivoCl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClientGet {

	private static final String WEBSERVICE = "https://ida.itdchile.cl/publicSms/sendSms.html?username=surait&password=5ur41t&";
	private static final String WEBSERVICESTATUS = "https://ida.itdchile.cl/publicSms/smsStatus.html?username=surait&password=5ur41t&key=";
	private static final String WEBSERVICEFERIADOS = "https://feriados-cl-api.herokuapp.com/feriados";

	/*
	 * public static void main (String [] args){ //String salida=
	 * callSendJersey("946406175","Hola como estas? esto es una prueba."); String
	 * salida=verySendJersey("d1c3a4e9-4aa9-43ab-ac2e-6d6fcdf4af44");
	 * 
	 * System.out.println(salida); }
	 */

	public String callSendJersey(String numero, String mensaje) {

		String output = null;

		try {

			Client client = Client.create();
			String param = "phone=56" + numero + "&message=" + mensaje.replace(" ", "%20").trim();

			WebResource webResource = client.resource(WEBSERVICE + param);

			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			output = response.getEntity(String.class);

			// System.out.println("Output from Server .... \n");
			// System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return controlResponseSend(output);
	}

	public String verySendJersey(String id) {

		String output = null;

		try {

			Client client = Client.create();
			String param = id;

			WebResource webResource = client.resource(WEBSERVICESTATUS + param);

			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			output = response.getEntity(String.class);

			// System.out.println("Output from Server .... \n");
			// System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return controlResponseStatus(output, id);
	}

	public String controlResponseSend(String resul) {

		String output = null;
		if (resul.contains("OK")) {

			output = resul.replace("OK", " ").trim();
		} else {
			/*
			 * - INTERNAL ERROR - NOT ENOUGHT CREDITS - WRONG USERNAME OR PASSWORD - BAD
			 * PARAMETERS - BLACKLISTED - MAXIMUM TRAFFIC EXCEEDED
			 */

			if (resul.contains("INTERNAL")) {
				output = "ERROR INTERNO";

			} else if (resul.contains("NOT")) {
				output = "NO HAY CREDITOS";

			} else if (resul.contains("WRONG")) {
				output = "NOMBRE DE USUARIO O CONTRASEÑA INCORRECTOS";

			} else if (resul.contains("BAD")) {
				output = "PARÁMETROS MALOS";
			} else if (resul.contains("BLACKLISTED")) {
				output = "NUMERO BLOQUEADO";

			} else if (resul.contains("MAXIMUM")) {
				output = "TRAFICO EXCEDIDO";
			}
		}

		return output;

	}

	public String controlResponseStatus(String resul, String key) {
		/*
		 * - CONFIRMED DELIVERY - SENT - WAITING CONFIRMATION --WAITING FOR CONFIRMATION
		 * - UNDELIVERED - REJECTED - EXPIRED - MESSAGE QUEUED
		 */

		/*
		 * - MESSAGE NOT FOUND - WRONG USERNAME OR PASSWORD - BAD PARAMETERS
		 */

		String output = null;

		switch (resul.trim()) {
		case "CONFIRMED DELIVERY":
			output = "ENTREGA CONFIRMADA";
			// System.out.println("Salida: entrega:"+output);
			break;
		case "SENT":
			output = "ENVIADO";
			break;
		case "WAITING FOR CONFIRMATION":
			output = "ESPERANDO CONFIRMACION";
			break;
		case "UNDELIVERED":
			output = "NO ENVIADO";

			break;
		case "REJECTED":
			output = "RECHAZADO";

			break;
		case "EXPIRED":
			output = "VENCIDO";

			break;
		case "MESSAGE QUEUED":
			output = "MENSAJE EN FILA";

			break;
		case "MESSAGE NOT FOUND":
			output = "MENSAJE NO ENCONTRADO";

			break;
		case "WRONG USERNAME OR PASSWORD":
			output = "NOMBRE DE USUARIO O CONTRASEÑA INCORRECTOS";

			break;
		case "BAD PARAMETERS":
			output = "PARAMETROS MALOS";

			break;
		}
		// System.out.println("Salida: Final:"+output);
		return output;

	}

	public String sendMessageSMS(String numero, String mensaje) {
		String output = null;
		try {
			Client client = Client.create();
			String reqXml = "<Index>\n" + "<mt_message>\n" + "\n" + "    <idEmpresa></idEmpresa>\n" + "\n"
					+ "    <celular>56" + numero + "</celular>\n" + "\n" + "    <mensaje>" + mensaje + "</mensaje>\n"
					+ "\n" + "    <idAplicativo>6</idAplicativo>\n" + "\n" + "    </mt_message>\n" + "</Index>";
			WebResource webResource = client.resource("http://afpcapitalsuite.mcstrx.mobid.cl/ListenMT");
			ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, reqXml);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			output = response.getEntity(String.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public String callSendJerseyFeriados() {

		String output = null;

		try {

			Client client = Client.create();

			WebResource webResource = client.resource(WEBSERVICEFERIADOS);

			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			output = response.getEntity(String.class);

			// System.out.println("Output from Server .... \n");
			// System.out.println("web service::"+output);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return output;
	}
}
