package com.api.nach.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;



public class DataEncryption {
	
	public static byte[] readFileBytes(String filename) throws IOException
	{
	    Path path = Paths.get(filename);
	    return Files.readAllBytes(path);        
	}

	public static PublicKey readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		
		byte[] keyBytes = readFileBytes(filename);
		String pubKey = new String(keyBytes, "UTF-8");
		pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)","");
		//System.out.println("Public key is " + pubKey);
		keyBytes = Base64.getMimeDecoder().decode(pubKey);
		//keyBytes = pubKey.getBytes();
	    X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(keyBytes);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePublic(publicSpec);       
	}

	public static PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		byte[] keyBytes = readFileBytes(filename);
		String priKey = new String(keyBytes, "UTF-8");
		priKey = priKey.replaceAll("(-+BEGIN RSA PRIVATE KEY-+\\r?\\n|-+END RSA PRIVATE KEY-+\\r?\\n?)","");
		System.out.println("Private key is " + priKey);
		//keyBytes = Base64.getMimeDecoder().decode(priKey);
		//keyBytes=DatatypeConverter.parseHexBinary(priKey);
		//keyBytes = pubKey.getBytes();
		/*PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(keyBytes);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePrivate(privateSpec);*/ 
		
		PrivateKey key = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
		
		return key;
	}
	
	public static PrivateKey readPrivateKey2(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePrivate(keySpec);     
	}
	
	public static String encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encrypted = cipher.doFinal(plaintext);
	    String encryptedText = DatatypeConverter.printBase64Binary(encrypted);
	    //System.out.println("Encrypted "+ encryptedText);
	    return encryptedText;
	}

	public static String decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
	    cipher.init(Cipher.DECRYPT_MODE, key);  
	    byte[] decrypted = cipher.doFinal(ciphertext);
	    String decryptedText = DatatypeConverter.printBase64Binary(decrypted);
	    //return cipher.doFinal(ciphertext);
	    return decryptedText;
	}
	
	public static PublicKey readPublicKey2(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
	    X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePublic(publicSpec);       
	}
	
	

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String publicKeyFile = "keys" + File.separator + "nayanpublic.der";
		String privateKeyFile = "keys" + File.separator + "privatekey.der";
		try
	    {
	        PublicKey publicKey = readPublicKey2(publicKeyFile);
	        PrivateKey privateKey = readPrivateKey(privateKeyFile);
	        //PrivateKey privateKey = readPrivateKey("private.der");
	        byte[] message = "Hello World".getBytes("UTF8");
	        String secret = encrypt(publicKey, message);
	        String decrypt = decrypt(privateKey, message);
	      // byte[] recovered_message = decrypt(privateKey, secret.getBytes());
	       // System.out.println(new String(recovered_message, "UTF8"));
	        System.out.println("Secret message "+ secret);
	        System.out.println("Decrypted "+decrypt);
	        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }

	}*/

}
