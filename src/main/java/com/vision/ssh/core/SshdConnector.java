package com.vision.ssh.core;

import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

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
	
			// also works
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
	        // works
	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(_algorithm, _provider);
	        KeyPair = keyPairGenerator.generateKeyPair();
			
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		}
		
		return KeyPair;
	}
}
