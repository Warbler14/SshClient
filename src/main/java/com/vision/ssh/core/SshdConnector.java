package com.vision.ssh.core;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import net.i2p.crypto.eddsa.EdDSASecurityProvider;

public class SshdConnector {
	
	public static void returnWait(ByteArrayOutputStream responseStream) {
		returnWait(responseStream, 100);
	}
	
	public static void returnWait(ByteArrayOutputStream responseStream, long waitTime) {
		try {
			int previousSize = 0;
			while(true) {
				Thread.sleep(waitTime);
				int size = responseStream.size();
				System.out.println("size " + size + ", previousSize : " + previousSize);
				if(previousSize < size){
					previousSize = size;
				} else if(previousSize>0){
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printStream(ByteArrayOutputStream responseStream) {
		byte[] byteArray = responseStream.toByteArray();
		System.out.print("[");
		for (byte b : byteArray) {
			System.out.print(b + ".");
		}
		System.out.println("]");
	}
	
	public static KeyPair getKeyPair(final String algorithm) {
		KeyPair KeyPair = null;
		
		String _algorithm = "EdDSA";
		
		if(algorithm != null && algorithm.length() > 0 ) {
			_algorithm = algorithm;
		}
		
		try {
	        // add eddsa to the security providers
	        Security.addProvider(new EdDSASecurityProvider());
	
	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(_algorithm);
	        KeyPair = keyPairGenerator.generateKeyPair();
	        
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		return KeyPair;
	}
	
	public static KeyPair getKeyPair(final String algorithm, final String provider) {
		KeyPair KeyPair = null;
		
		String _algorithm = "EdDSA";
		String _provider = "EdDSA";
		
		if(algorithm != null && algorithm.length() > 0 ) {
			_algorithm = algorithm;
		}
		
		if(provider != null && provider.length() > 0 ) {
			_provider = provider;
		}
		
		try {
			 // add eddsa to the security providers
	        Security.addProvider(new EdDSASecurityProvider());
	        
	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(_algorithm, _provider);
	        KeyPair = keyPairGenerator.generateKeyPair();
			
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		}
		
		return KeyPair;
	}
	
	public static KeyPair getKeyPair(byte[] publicKeyBytes, byte[] privateKeyBytes, final String algorithm) {
		
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		PublicKey publicKey = null;
	    PrivateKey privateKey = null;
		try {
			publicKey = keyFactory.generatePublic(publicKeySpec);
			privateKey = keyFactory.generatePrivate(privateKeySpec);
				
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return new KeyPair(publicKey, privateKey);
	}
	
	public static void printKeyPair(KeyPair KeyPair) {
		PrivateKey privateKey = KeyPair.getPrivate();
        PublicKey publicKey = KeyPair.getPublic();
        
        System.out.println("Algorithm : " + privateKey.getAlgorithm());
        System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));	        
        System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        
	}
}
