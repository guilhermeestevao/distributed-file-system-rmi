package br.ufc.mdcc.distributedfilesystem;

import org.jgroups.Address;

import entities.Client;
import entities.Proxy;

public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Client client = new Client();
			
			
			
			Proxy proxy = new Proxy();
			
			
			client.requestProxy(proxy);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
