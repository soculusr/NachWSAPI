package com.api.nach.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Service
public class XmlSigning {
	
	public static String getSignedData(String xmlData) {
		
		
		String FilePath = "keys" + File.separator + "nayan.p12";
		String signedData  = null;
		KeyInfo keyInfo = null;
        KeyValue keyValue = null;
		try {
			
			//load the xml doc to sign
			
			DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
	
			Document xmlDoc = docBuild.parse(new InputSource(new StringReader(xmlData)));
			
			//Load the keystore containing the keys
			
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			
			char[] password = "nayan123".toCharArray();
			
			keystore.load(new FileInputStream(FilePath),password);
			
			String aliasName = "mykey";
			Certificate cert = keystore.getCertificate(aliasName);
			
			Key key = keystore.getKey(aliasName, password);
			
			PublicKey publicKey = cert.getPublicKey();
			
			KeyPair keypair = new KeyPair(publicKey, (PrivateKey) key);
			//PrivateKey privatekey= keypair.getPrivate();
			
		
			PrivateKey privatekey = (PrivateKey)keystore.getKey(aliasName , password);
			
			
			System.out.println("Private key "+privatekey);
			DOMSignContext domSignCtx = new DOMSignContext(privatekey, xmlDoc.getDocumentElement());
			
			XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
			
			Reference ref = null;
	        SignedInfo signedInfo = null;
	        
	        try {
	            ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA256, null),
	                    Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
	                    (TransformParameterSpec) null)), null, null);
	            signedInfo = xmlSigFactory.newSignedInfo(
	                    xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
	                    (C14NMethodParameterSpec) null),
	                    xmlSigFactory.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null),
	                    Collections.singletonList(ref));
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	        } catch (InvalidAlgorithmParameterException ex) {
	            ex.printStackTrace();
	        }
	        //Pass the Public Key File Path 
	        
	        KeyInfoFactory keyInfoFact = xmlSigFactory.getKeyInfoFactory();

	        try {
	            keyValue = keyInfoFact.newKeyValue(keypair.getPublic());
	        } catch (KeyException ex) {
	            ex.printStackTrace();
	        }
	        keyInfo = keyInfoFact.newKeyInfo(Collections.singletonList(keyValue));
	    
	        
	        //Create a new XML  Signature
	        XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
	        try {
	            //Sign the document
	            xmlSignature.sign(domSignCtx);
	        } catch (MarshalException ex) {
	            ex.printStackTrace();
	        } catch (XMLSignatureException ex) {
	            ex.printStackTrace();
	        }
	        
	       
	        DOMSource domSource = new DOMSource(xmlDoc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			//System.out.println("XML IN String format is: \n" + writer.toString());
			signedData = writer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return signedData;
	}
	
	private static byte[] getKeyData(String filePath) {
        File file = new File(filePath);
        byte[] buffer = new byte[(int) file.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }
	
	public PrivateKey getStoredPrivateKey(String filePath) {
		
		PrivateKey privateKey = null;
        byte[] keydata = getKeyData(filePath);
        PKCS8EncodedKeySpec encodedPrivateKey = new PKCS8EncodedKeySpec(keydata);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(encodedPrivateKey);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }
	
	public static PublicKey getStoredPublicKey(String filePath) {
		
		/*String publicKeyData = "-----BEGIN PUBLIC KEY-----\r\n" + 
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp/6IUw9H2VdWPpM6pP9k\r\n" + 
				"pCylsECejWlieo+I00kcAHVNN+oyUNJ2vcuen3OSsIPh1byrsaU6ku0AxXWhr3qf\r\n" + 
				"0uAAfCTeVRpZjaoDPLVCX/F+gu+4xXeErfttg0syOX6DdeNWucW1ja231pQGGZis\r\n" + 
				"iTfFdJ0aWn4Tc15DaxIkqhdWYBiGrNT9cCuzXNo3g2UIvhKJh5/Dr1GVV4SXlBC3\r\n" + 
				"L/O7lc1iGPq/utq0uIZRLEC+e9M/EzH22psV7E99qMS6DPhg5rn6m0iP3K34iM5M\r\n" + 
				"tRogTGxuluN4TjrYTm9tsSfPd2guoRKF3aEg5yIaS02X05hPVD2DyseBqZK/Exug\r\n" + 
				"zwIDAQAB\r\n" + 
				"-----END PUBLIC KEY-----";
		
		String pubKeyPEM = publicKeyData.replace("-----BEGIN PUBLIC KEY-----\r\n", "").replace("-----END PUBLIC KEY-----", "");
		
		byte[] encodedPublicKeyData = Base64.getMimeDecoder().decode(pubKeyPEM);*/
        PublicKey publicKey = null;
        byte[] keydata = getKeyData(filePath);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        X509EncodedKeySpec encodedPublicKey = new X509EncodedKeySpec(keydata);
        try {
            publicKey = keyFactory.generatePublic(encodedPublicKey);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
	
	public static boolean isXmlDigitalSignatureValid(String signedXmlData) throws Exception {
		String FilePath = "keys" + File.separator + "nayan.p12";
		//String pubicKeyFilePath = "keys" + File.separator + "publickey.pem";
		
		KeyStore keystore = KeyStore.getInstance("PKCS12");
		
		char[] password = "nayan123".toCharArray();
		
		keystore.load(new FileInputStream(FilePath),password);
		
		String aliasName = "mykey";
		Certificate cert = keystore.getCertificate(aliasName);
		PublicKey publicKey = cert.getPublicKey();
		System.out.println("Public 1 "+publicKey);
		Key key = keystore.getKey(aliasName, password);
		KeyPair keypair = new KeyPair(publicKey, (PrivateKey) key);
		System.out.println("Public 2 "+keypair.getPublic());
		//PublicKey publicKey = getStoredPublicKey(pubicKeyFilePath);
		
		
			boolean validFlag = false;
			
			//load the xml doc to VERIFY sign
			
			DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
			docBuildFac.setNamespaceAware(true);
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
			Document xmlDoc = docBuild.parse(new InputSource(new StringReader(signedXmlData)));
			NodeList nl = xmlDoc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
			System.out.println("Node length is "+nl.getLength());
			if (nl.getLength() == 0) {
			throw new Exception("No XML Digital Signature Found, document is discarded");
				
			}
			DOMValidateContext valContext = new DOMValidateContext(keypair.getPublic(), nl.item(0));
			XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
			XMLSignature signature = fac.unmarshalXMLSignature(valContext);
			validFlag = signature.validate(valContext);
			return validFlag;
			}
	
	
	public static void main(String[] args) throws Exception {
		
		String xml = "<ach:GetPanDtlsRqst xmlns:ach=\"http://npci.org/ach/schema/\" >\r\n" + 
				"			<Head ver=\"1.0\" ts=\"2018-02-12T10:11:00\" />	\r\n" + 
				"			<Source type=\"CODE\" value=\"SBIN\" name=\"State Bank of India\" />\r\n" + 
				"			<Destination type=\"CODE\" value=\"UTIB\" name=\"Axis Bank\" />\r\n" + 
				"			<Request id=\"13022018b\" type=\"DETAILS_ENQ\" refUrl=\"\"/>\r\n" + 
				"			<ReqData>\r\n" + 
				"				<Detail accNo=\"a+6zC8vWtkMytiwHU/hPnF3+817rBBhHcIrbXhw1SJQHCf4umnZfExOKUI9vsHbMg+tUkYVR66hubVNzmIGwasTxCOfpu0kQrDaGe9dWDPi3IZ8N9M2NSyaP4rsPovt5eZnXsn9fWjINMZ/wRGLasCfwiOCNCZQY8i+Oe2dlz3uYrGz/ysJMqekvf/ryTXCdV/H7ZZxhWySPg8cc8/iUmJiO2gwr0KI+dF3PczgkL5rm58l0lm/Tbr++Ik2/AxqgoCQPGDuvpkzL7rFqoINFOywqqNP6EIExjdmBXZyz2/y2fOKx7yWDKiUdfCOwa8OstMHJW5RQd7d82uw9LFdOUw==\" />\r\n" + 
				"			</ReqData>	\r\n" + 
				"			<NpciRefId value=\"\" />\r\n" + 
				"		</ach:GetPanDtlsRqst>";
		
		
		String signedXmlData = getSignedData(xml);
		System.out.println(getSignedData(xml));
		Boolean flag = isXmlDigitalSignatureValid(signedXmlData);
		
		System.out.println("Verify status is" + flag);
		
		
	}

}
