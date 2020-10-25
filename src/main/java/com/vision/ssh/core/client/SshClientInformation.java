package com.vision.ssh.core.client;

import java.security.KeyPair;

import org.apache.sshd.client.simple.SimpleClientConfigurator;

public class SshClientInformation implements SimpleClientConfigurator{

	private long connectTimeout;
	
	private long authenticationTimeout;
	
	private String username;
	
	private String password;
	
	private String host;
	
	private int port = 22;
	
	private String keyPairAlgorithm = "EdDSA";
	
	 private KeyPair KeyPair;
	
	@Override
	public long getConnectTimeout() {
		return connectTimeout;
	}

	@Override
	public void setConnectTimeout(long timeout) {
		this.connectTimeout =  timeout;
	}

	@Override
	public long getAuthenticationTimeout() {
		return authenticationTimeout;
	}

	@Override
	public void setAuthenticationTimeout(long timeout) {
		this.authenticationTimeout = timeout;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getKeyPairAlgorithm() {
		return keyPairAlgorithm;
	}

	public void setKeyPairAlgorithm(String keyPairAlgorithm) {
		this.keyPairAlgorithm = keyPairAlgorithm;
	}

	public KeyPair getKeyPair() {
		return KeyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		KeyPair = keyPair;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SshClientInformation [connectTimeout=");
		builder.append(connectTimeout);
		builder.append(", authenticationTimeout=");
		builder.append(authenticationTimeout);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", keyPairAlgorithm=");
		builder.append(keyPairAlgorithm);
		builder.append(", KeyPair=");
		builder.append(KeyPair);
		builder.append("]");
		return builder.toString();
	}
	
	
}
