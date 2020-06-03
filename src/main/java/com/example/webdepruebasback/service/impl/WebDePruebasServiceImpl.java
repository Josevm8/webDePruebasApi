package com.example.webdepruebasback.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import javax.net.ssl.HttpsURLConnection;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.springframework.stereotype.Service;

import com.example.webdepruebasback.header.GeneralHeader;
import com.example.webdepruebasback.service.WebDePruebasService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class WebDePruebasServiceImpl implements WebDePruebasService {

	@Override
	public String consultarApi(GeneralHeader generalHeader, JsonNode jsonNode) throws Exception {

		String tipo = jsonNode.get("metodo").asText();

		if (tipo.equalsIgnoreCase("GET")) {
			return getApi(jsonNode, generalHeader);
		} else {
			return null; // postApi(jsonNode, generalHeader);
		}
	}
	
	

	public String getApi(JsonNode jsonNode, GeneralHeader generalHeader) throws Exception {
		
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{
		    new X509TrustManager() {
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		        }
		        public void checkClientTrusted(
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
		        public void checkServerTrusted(
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
		    }
		};

		// Install the all-trusting trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		// Now you can access an https URL without having the certificate in the truststore
		
        String metodo = "GET";
        HttpsURLConnection httpConn = getConnectionHeader(jsonNode, generalHeader, metodo);


        httpConn.setConnectTimeout(50000);
        httpConn.setReadTimeout(50000);

		int status = httpConn.getResponseCode();
		// String respuesta = con.getResponseMessage();

		StringBuffer response;
		if (status == HttpURLConnection.HTTP_OK) { // success

			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			return response.toString();

		} else {
			System.out.println("GET request not worked");
			return null;
		}

	}

	public HttpsURLConnection getConnectionHeader(JsonNode jsonNode, GeneralHeader generalHeader, String metodo) throws Exception {
		URL url = new URL(jsonNode.get("uri").asText());
		System.out.println("URL -->" + jsonNode.get("uri").asText());
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod(metodo);
		//conn.setRequestProperty("app-code", generalHeader.getApplicationCode());
		//conn.setRequestProperty("app-name", generalHeader.getApplicationName());
		return conn;
		
	}

}
