package br.ufc.mdcc.distributedfilesystem.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.PasswordAuthentication;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import br.ufc.mdcc.distributedfilesystem.impl.BalanceNodeImpl;
import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;

public class Event {
	
	private BalanceNode balanceNode;
	private String clientName;
	private String[] OPTIONS = {"(1) - Escrever arquivo", "(2) - Ler arquivo", "(3) - Excluir arquivo", "(4) - Atualizar arquivo"};
	private BufferedReader in;
	
	public Event(String clientName){
		this.clientName = clientName;
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void start() throws AccessException, RemoteException, NotBoundException{
		
		Registry registry = LocateRegistry.getRegistry(null, 1099);
		
		balanceNode = (BalanceNode) registry.lookup("balancenode");
		
		int opc = 0;
		while (true){
			try {
				opc = getOption();
				process(opc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	private void process(int opc) throws IOException {
		Proxy proxy = null;
		switch (opc) {
		case 1:
			System.out.println("Digite o path do arquivo");
			String pathWrite = in.readLine();
			proxy = balanceNode.requestProxy();
			proxy.setAvailability(false);
			proxy.writeFile(new File(pathWrite));
			proxy.setAvailability(true);
			break;
		case 2:
			System.out.println("Digite o nome do arquivo");
			String nameRead = in.readLine();
			proxy = balanceNode.requestProxy();
			proxy.setAvailability(false);
			proxy.getFile(nameRead);
			proxy.setAvailability(true);
			break;
		case 3:
			System.out.println("Digite o nome do arquivo");
			String nameRemove = in.readLine();
			proxy = balanceNode.requestProxy();
			proxy.setAvailability(false);
			proxy.deleteFile(nameRemove);
			proxy.setAvailability(true);
			break;
		case 4:
			System.out.println("Digite o path do arquivo");
			String pathUpdate = in.readLine();
			proxy = balanceNode.requestProxy();
			proxy.setAvailability(false);
			proxy.updateFile(new File(pathUpdate));
			proxy.setAvailability(true);
			break;

		default:
			break;
		}
	}
	
	private int getOption() throws IOException{
		
		System.out.println("-------------------");
		System.out.println(">>> "+clientName);
		System.out.println("-------------------");
		
		for (String string : OPTIONS) {
			System.out.println(string);
		}
		System.out.print("> "); 
		System.out.flush();
		String s = in.readLine();
		return Integer.parseInt(s);
	}

}
