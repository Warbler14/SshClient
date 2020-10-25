package com.vision.ssh.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.vision.ssh.core.client.SshClientInformation;

public class JschSshdConnector  extends SshdConnector{

	public static void sshChannelShellSyncedConnect(SshClientInformation sshClientInformation) throws Exception {

		Session session = null;
		ChannelExec channel = null;

		try {
			session = new JSch().getSession(sshClientInformation.getUsername(), sshClientInformation.getHost(),
					sshClientInformation.getPort());
			session.setPassword(sshClientInformation.getPassword());
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();

			channel = (ChannelExec) session.openChannel("exec");

			ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
			channel.setOutputStream(responseStream);

			sendAndPrint(responseStream, channel, "whoami\n");

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

	private static void sendAndPrint(ByteArrayOutputStream responseStream, ChannelExec channel, final String command)
			throws IOException, JSchException {

		channel.setCommand(command);
		channel.connect();
		

		while (channel.isConnected()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		returnWait(responseStream);

		printStream(responseStream);
		String responseMessage = new String(responseStream.toByteArray());
		System.out.println(responseMessage);
		responseStream.reset();		

	}
}
