package com.vision.ssh.test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Base64;

import net.i2p.crypto.eddsa.EdDSASecurityProvider;

public class EdDSATest {
	public static void main(String[] args) {
		
		new EdDSASecurityProvider();
		
//		test1();
//		test2();
	
		for (int idx = 0 ; idx < 10; idx++ ) {
			test1();
		}
		
		System.out.println("-----------------");
		
		for (int idx = 0 ; idx < 10; idx++ ) {
			test2();
		}
		
	}
	
	public static void test1() {
		KeyPair KeyPair = getKeyPair("EdDSA");
		PrivateKey privateKey = KeyPair.getPrivate();
        PublicKey publicKey = KeyPair.getPublic();
        
        System.out.println("Algorithm : " + privateKey.getAlgorithm());
        System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));	        
        System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
	}
	
	public static void test2() {
		KeyPair KeyPair =  getKeyPair("EdDSA", "EdDSA");
		PrivateKey privateKey = KeyPair.getPrivate();
        PublicKey publicKey = KeyPair.getPublic();
        
        System.out.println("Algorithm : " + privateKey.getAlgorithm());
        System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));	        
        System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
	}
	
	public static KeyPair getKeyPair(final String algorithm) {
		KeyPair KeyPair = null;
		
		String _algorithm = "EdDSA";
		
		if(algorithm != null && algorithm.length() > 0 ) {
			_algorithm = algorithm;
		}
		
		try {
			// throws NoSuchAlgorithmException: EdDSA KeyPairGenerator not available
	        //KeyPairGenerator.getInstance("EdDSA");
	
	        // add eddsa to the security providers
	        Security.addProvider(new EdDSASecurityProvider());
	
	        // throws: NoSuchAlgorithmException: Ed25519 KeyPairGenerator not available
	        //KeyPairGenerator.getInstance("Ed25519");
	
	        // throws: NoSuchAlgorithmException: no such algorithm: Ed25519 for provider EdDSA
	        //KeyPairGenerator.getInstance("Ed25519", "EdDSA");
	

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
	
	        // add eddsa to the security providers
	        //Security.addProvider(new EdDSASecurityProvider());
	
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
