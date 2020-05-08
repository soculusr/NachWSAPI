package com.api.nach.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

public class DataEncryptDecrypt {
	
	
	private static final String ALGORITHM = "RSA";
    
    public static PublicKey readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
	    X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePublic(publicSpec);       
	}
    
    public static byte[] readFileBytes(String filename) throws IOException
	{
	    Path path = Paths.get(filename);
	    return Files.readAllBytes(path);        
	}
    
    
    public static byte[] encryptData( String PublicKeyFilePath,byte[] inputData)
            throws Exception {
    	
    	PublicKey publicKey = null;
		try {
			publicKey = readPublicKey(PublicKeyFilePath);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		byte[] publicKeyData = publicKey.getEncoded();
        PublicKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(publicKeyData));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(inputData);
        return encryptedBytes;
    }
    
    
    public static byte[] encryptData2( PublicKey PublicKey,byte[] inputData)
            throws Exception {
    	
    	//PublicKey publicKey = PublicKey;
		/*try {
			publicKey = readPublicKey(PublicKeyFilePath);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}*/
		byte[] publicKeyData = PublicKey.getEncoded();
        PublicKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(publicKeyData));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(inputData);
        return encryptedBytes;
    }
    
    public static byte[] decryptData(String KeyStoreFilePath, String KeyStorePass, String KeyStoreAlias, byte[] inputData) {
    	KeyStore keystore = null;
    	String decryptedData="";
    	byte[] decryptedBytes=null;
		try {
			keystore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		char[] password = KeyStorePass.toCharArray();
		
		try {
			keystore.load(new FileInputStream(KeyStoreFilePath),password);
		} catch (NoSuchAlgorithmException | CertificateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String aliasName = KeyStoreAlias;
		Certificate cert = null;
		try {
			cert = keystore.getCertificate(aliasName);
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PublicKey publicKey = cert.getPublicKey();
		byte[] encrypt=null;
		String message = "Hello Nayan";
		try {
			encrypt = encryptData2(publicKey, message.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encrypted = Base64.getEncoder().encodeToString(encrypt);
		System.out.println("Encrypt "+encrypted);
		byte [] acctNo = Base64.getDecoder().decode(encrypted.getBytes());
		
		
		
		Key key = null;
		try {
			key = keystore.getKey(aliasName, password);
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//KeyPair keypair = new KeyPair(publicKey, (PrivateKey) key);
		
		
	
		PrivateKey privatekey = null;
		try {
			privatekey = (PrivateKey)keystore.getKey(aliasName , password);
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        //byte[] publicKey1 = keypair.getPublic().getEncoded();
		//byte[] publicKeyData = publicKey.getEncoded();
        byte[] privateKeyData = privatekey.getEncoded();
        
        try {
			PrivateKey priKey = KeyFactory.getInstance(ALGORITHM)
			        .generatePrivate(new PKCS8EncodedKeySpec(privateKeyData));
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
        try {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			
			e.printStackTrace();
		}

        try {
			decryptedBytes = cipher.doFinal(inputData);
			
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
        
        return decryptedBytes;

    	
    }
    
    
    
	
	
	public static void main(String[] args) {
		
		//KeyPair generateKeyPair = generateKeyPair();
		String KeyStoreFilePath = "keys" + File.separator + "nayanpublic.p12";
		
		String PublicKeyFilePath = "keys" + File.separator + "nayanpublic.pub";
		byte [] pubkeybytes = null;
		try {
			pubkeybytes = readFileBytes(PublicKeyFilePath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String pubKey = null;
		try {
			pubKey = new String(pubkeybytes, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)","");
		pubkeybytes = Base64.getMimeDecoder().decode(pubKey.getBytes());
		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(pubkeybytes);
	    KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    PublicKey pubKey2 = null;
		try {
			pubKey2 = keyFactory.generatePublic(publicSpec);
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		String Pass = "nayan123";
		String alias = "nayan";
		byte[] encrypt=null;
		String message = "Hello Nayan";
		try {
			encrypt = encryptData2(pubKey2, message.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encrypted = Base64.getEncoder().encodeToString(encrypt);
		System.out.println("Encrypt "+encrypted);
		byte [] acctNo = Base64.getDecoder().decode(encrypted.getBytes());
		byte[] decrypt = decryptData(KeyStoreFilePath, Pass, alias, acctNo);
		
		String decryptData = new String(decrypt);
		System.out.println("Decrypted Data "+decryptData);
		
		
	}

}
