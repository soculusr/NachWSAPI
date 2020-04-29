package com.api.nach.services;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;

public class SSLAuth {
	
	
	private SSLAuth(){
		
	}
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SSLAuth.class);
	public static void doTrustToCertificates() throws Exception{
		
		
		logger.info("Inside SSL Auth");
		
		TrustManager[] trustAllCerts = new TrustManager[] {
				
				new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					
					public void checkClientTrusted(X509Certificate[] certs, String authType) {}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {}
				}};
					
			//Ignore differences between given hostname and certificate hostname
		
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
				
			};
			
			//Install all trusting trust manager
			
			try {
				
				logger.info("Inside SSL Auth Install");
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
				
				
			} catch (Exception e) {
				logger.error("Exception", e);
			}
		
	}

}
