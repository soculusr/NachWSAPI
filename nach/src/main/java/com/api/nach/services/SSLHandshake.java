package com.api.nach.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SSLHandshake {
	
	private static String keystorePath;
	@Value("${keystore.path}")
	public void setPath(String path) {
		keystorePath = path;
		
	}
	
	private static String keystorePass;
	@Value("${keystore.pass}")
	public void setPass(String pass) {
		
		keystorePass = pass;
	}
	
	public static void startHandshake() {
		
		System.setProperty("javax.net.debug", "all");
		System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
		System.setProperty("https.protocols", "TLSv1.2");
		System.setProperty("javax.net.ssl.keyStore",  keystorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", keystorePass);
		
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				new javax.net.ssl.HostnameVerifier() {

					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						return true;
					}
				});
	}

}

