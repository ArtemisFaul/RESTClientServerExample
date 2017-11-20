package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.MediaType;

public class ConnectToResource {

	private JsonObject jsonObject;

	private void getJson(String strJson) {

		try {
			JsonReader jsonReader = Json.createReader(new StringReader(strJson));
			jsonObject = jsonReader.readObject();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public JsonObject getResorce(String string)  {

		URL url;
		try {
			url = new URL(string);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
			conn.setRequestProperty("Accept-Charset", "UTF-8"); 

			if (conn.getResponseCode() != 200) {
				System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				getJson(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public JsonObject putResorce(String string, JsonObject jsonObject) {

		URL url;
		try {
			url = new URL(string);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
			conn.setRequestProperty("Accept-Charset", "UTF-8"); 
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonObject.toString().getBytes());
			os.flush();
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n ");
			while ((output = br.readLine()) != null) {
				System.out.println("Output from Server .... \n "+ output);
				getJson(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}


}
