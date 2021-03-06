package com.api.nach.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Collections;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
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

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



@Component
public class XmlSigning {
	
	
	private static String fiUrl = "";
	
	@Value("${fi.url}")
    public void setUrl(String url) {
		fiUrl = url;
    }
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(XmlSigning.class);
	
	public String getSignedData(String xmlData, String KeyStoreFilePath, String KeyStorePass, String AliasName) throws IOException {
		
		logger.info("Keystore is "+KeyStoreFilePath);
		logger.info("URL is"+fiUrl);
		
		String signedData  = null;
		KeyInfo keyInfo = null;
        KeyValue keyValue = null;
		try {
			
			//load the xml doc to sign
			
			DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
			docBuildFac.setNamespaceAware(true);
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
	
			Document xmlDoc = docBuild.parse(new InputSource(new StringReader(xmlData)));
			
			//Load the keystore containing the keys
			
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			
			char[] password = KeyStorePass.toCharArray();
			
			keystore.load(new FileInputStream(KeyStoreFilePath),password);
			
			String aliasName = AliasName;
			Certificate cert = keystore.getCertificate(aliasName);
			
			Key key = keystore.getKey(aliasName, password);
			
			PublicKey publicKey = cert.getPublicKey();
			
			KeyPair keypair = new KeyPair(publicKey, (PrivateKey) key);
			//PrivateKey privatekey= keypair.getPrivate();
			
		
			PrivateKey privatekey = (PrivateKey)keystore.getKey(aliasName , password);
			
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
	        
	       //Converting signed data to string format
	        DOMSource domSource = new DOMSource(xmlDoc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			signedData = writer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return signedData;
	}
	
	public boolean isSignatureValid(String signedXmlData,String KeyStoreFilePath, String KeyStorePass, String AliasName) throws Exception {
		//String FilePath = "keys" + File.separator + "nayan.p12";
		
		KeyStore keystore = KeyStore.getInstance("PKCS12");
		
		//char[] password = "nayan123".toCharArray();
		char[] password = KeyStorePass.toCharArray();
		
		keystore.load(new FileInputStream(KeyStoreFilePath),password);
		
		//String aliasName = "mykey";
		String aliasName = AliasName;
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
	
	
	/*public static void main(String[] args) throws Exception {
		
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
		
		
	}*/

}
