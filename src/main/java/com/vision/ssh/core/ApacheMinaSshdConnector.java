package com.vision.ssh.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannel.Streaming;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;
import org.apache.sshd.common.session.SessionContext;

import com.vision.ssh.core.client.SshClientInformation;

public class ApacheMinaSshdConnector extends SshdConnector{

	public static void sshChannelShellSyncedConnect(SshClientInformation sshClientInformation) throws IOException {

		long defaultTimeoutSeconds = sshClientInformation.getConnectTimeout();

		SshClient client = SshClient.setUpDefaultClient();
		client.start();
		
		try (
				ClientSession session = client
						.connect(sshClientInformation.getUsername(), sshClientInformation.getHost(),
								sshClientInformation.getPort())
						.verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()
		) {
			
			session.addPasswordIdentity(sshClientInformation.getPassword());
			
			
	        KeyPair keyPair = sshClientInformation.getKeyPair();
	        if(keyPair == null) {
	        	keyPair = getKeyPair(sshClientInformation.getKeyPairAlgorithm()); 
	        }
	        		
	        session.addPublicKeyIdentity(keyPair);
			
			session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
			
			try (
				ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
				ByteArrayOutputStream errorResponseStream = new ByteArrayOutputStream();
				ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL);

			) {
				channel.setStreaming(Streaming.Sync);
				channel.setOut(responseStream);
				channel.setErr(errorResponseStream);
			
				SessionContext sessionContext = channel.getSessionContext();
				System.out.println("ClientVersion : " + sessionContext.getClientVersion());
				System.out.println("ServerVersion : " + sessionContext.getServerVersion());
				
				
				//test
				if (channel instanceof ChannelShell) {
					ChannelShell channelShell = (ChannelShell) channel;
				}
				
				try {
					channel.open().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
					
					try (
						OutputStream pipedIn = channel.getInvertedIn();
					) {
						
						constantTest(channel, responseStream, errorResponseStream, pipedIn);
						
//						channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), TimeUnit.SECONDS.toMillis(30));
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					channel.close(false);
				}				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			client.stop();
		}
	}
	
	private static void constantTest(ClientChannel channel, ByteArrayOutputStream responseStream,
			ByteArrayOutputStream errorResponseStream, OutputStream pipedIn)
			throws IOException {

		/**
		 * 입력부와 출력부를 나눔
		 * 
		 * 
		 * 
		 */
		
		sendAndPrint(responseStream, errorResponseStream, pipedIn, null);		

		sendAndPrint(responseStream, errorResponseStream, pipedIn, "whoami\n");

		sendAndPrint(responseStream, errorResponseStream, pipedIn, "pwd\n");
		
		sendAndPrint(responseStream, errorResponseStream, pipedIn, "ifconfig\n");
	}
	
	private static void sendAndPrint(ByteArrayOutputStream responseStream,
			ByteArrayOutputStream errorResponseStream, OutputStream pipedIn, final String command) throws IOException{
		
		if(command != null && command.length() > 0 ) {
			pipedIn.write(command.getBytes());
			pipedIn.flush();
		}

		// 채널 종료까지 기다리는건 시간이 많이 소모됨
//		channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
		returnWait(responseStream);

		printStream(responseStream);
		String responseMessage = new String(responseStream.toByteArray());
		System.out.println(responseMessage);
		responseStream.reset();		
	}
	
}
