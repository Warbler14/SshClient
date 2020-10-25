package com.vision.ssh.test;

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

import com.vision.ssh.core.SshdConnector;

import net.i2p.crypto.eddsa.EdDSASecurityProvider;

public class EdDSATest {
	public static void main(String[] args) {
		
		new EdDSASecurityProvider();
		
//		test1();
//		test2();
//		test3();
		test4();
	
//		for (int idx = 0 ; idx < 10; idx++ ) {
//			test1();
//		}
//		
//		System.out.println("-----------------");
//		
//		for (int idx = 0 ; idx < 10; idx++ ) {
//			test2();
//		}
		
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
	
	public static void test3() {
		String algorithm = "DSA"; // or RSA, DH, etc.

	    // Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
	    KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
	    keyGen.initialize(1024);
	    KeyPair keypair = keyGen.genKeyPair();
	    PrivateKey privateKey = keypair.getPrivate();
	    PublicKey publicKey = keypair.getPublic();

	    byte[] privateKeyBytes = privateKey.getEncoded();
	    byte[] publicKeyBytes = publicKey.getEncoded();
	    
	    KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
	    EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
	    PrivateKey privateKey2 = null;
		try {
			privateKey2 = keyFactory.generatePrivate(privateKeySpec);
		} catch (InvalidKeySpecException e1) {
			e1.printStackTrace();
		}

	    EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
	    PublicKey publicKey2 = null;
		try {
			publicKey2 = keyFactory.generatePublic(publicKeySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

	    // The orginal and new keys are the same
	    boolean same = privateKey.equals(privateKey2); 
	    System.out.println(same);
	    same = publicKey.equals(publicKey2);
	    System.out.println(same);
	}
	
	public static void test4() {
		
		String algorithm = "EdDSA";
		String provider = "EdDSA";
		
	    KeyPairGenerator keyGen = null;
	    
		try {
			Security.addProvider(new EdDSASecurityProvider());
			
			keyGen = KeyPairGenerator.getInstance(algorithm, provider);
			
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			e1.printStackTrace();
		}
		
	    KeyPair keypair = keyGen.genKeyPair();
	    PublicKey publicKey = keypair.getPublic();
	    PrivateKey privateKey = keypair.getPrivate();

	    byte[] publicKeyBytes = publicKey.getEncoded();
	    byte[] privateKeyBytes = privateKey.getEncoded();
	    
	    KeyPair keyPair = SshdConnector.getKeyPair(publicKeyBytes, privateKeyBytes, algorithm);

	    // The orginal and new keys are the same
	    boolean same = privateKey.equals(keyPair.getPrivate()); 
	    System.out.println(same);
	    same = publicKey.equals(keyPair.getPublic());
	    System.out.println(same);
	}

}
