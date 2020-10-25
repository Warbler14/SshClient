package com.vision.ssh.main;

import java.security.KeyPair;

import com.vision.ssh.core.ApacheMinaSshdConnector;
import com.vision.ssh.core.client.SshClientInformation;

public class ClientMain {

	public static void main(String[] args) {
		KeyPair keyPair = null;
		SshClientInformation sshClientInformation = new SshClientInformation();
		sshClientInformation.setUsername("centos");
		sshClientInformation.setPassword("centos");
		sshClientInformation.setHost("192.168.25.62");
		sshClientInformation.setPort(22);
		
		if(keyPair == null) {
			String keyPairAlgorithm = sshClientInformation.getKeyPairAlgorithm();
			keyPair = ApacheMinaSshdConnector.getKeyPair(keyPairAlgorithm);
			sshClientInformation.setKeyPair(keyPair);
		}

		sshClientInformation.setConnectTimeout(2l);
		
		try {
			ApacheMinaSshdConnector.sshChannelShellSyncedConnect(sshClientInformation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}


/*
Microsoft Windows [Version 10.0.18363.1139]
(c) 2019 Microsoft Corporation. All rights reserved.

C:\Users\insung>telnet 192.168.25.62 22
'telnet'은(는) 내부 또는 외부 명령, 실행할 수 있는 프로그램, 또는
배치 파일이 아닙니다.

C:\Users\insung>ssh centos@192.168.25.62
The authenticity of host '192.168.25.62 (192.168.25.62)' can't be established.
ECDSA key fingerprint is SHA256:H8oPB7ZySI/IDMmIY7g+yK9nC2NS3TJnHYcKwIZiWqE.
Are you sure you want to continue connecting (yes/no)? yes
Warning: Permanently added '192.168.25.62' (ECDSA) to the list of known hosts.
centos@192.168.25.62's password:
Last login: Fri Oct 23 23:37:47 2020
Last login: Fri Oct 23 23:37:47 2020
[centos@localhost ~]$
[centos@localhost ~]$
*/