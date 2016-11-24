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

import javax.swing.JFileChooser;

import br.ufc.mdcc.distributedfilesystem.impl.BalanceNodeImpl;
import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;
import br.ufc.mdcc.distributedfilesystem.util.FileUtil;

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
		
		Registry registry = LocateRegistry.getRegistry();
		
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
	
		switch (opc) {
		case 1:
			System.out.println("Selecione um arquivo");
			File file = selecteFile();
			write(file);
			break;
		case 2:
			System.out.println("Digite o nome do arquivo");
			System.out.print("> "); 
			System.out.flush();
			String nameRead = in.readLine();
			read(nameRead);
			break;
		case 3:
			System.out.println("Digite o nome do arquivo");
			System.out.print("> "); 
			System.out.flush();
			String nameRemove = in.readLine();
			delete(nameRemove);
			break;
		case 4:
			System.out.println("Selecione um arquivo");
			File fileUpdated = selecteFile();
			update(fileUpdated);
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
	
    private File selecteFile(){
    	JFileChooser chooser = new JFileChooser();
    	chooser.showOpenDialog(null);
    
    	File file = chooser.getSelectedFile();
    	return file;
    }
    
    
    private void write(File file) throws RemoteException{
    	Proxy proxy = balanceNode.requestProxy();
    	if(proxy != null){
    		proxy.setAvailability(false);
			proxy.writeFile(file);
			proxy.setAvailability(true);
    	}else{
    		System.out.println("Nenhum proxy disponível");
    	}
    }
    
    private void read(String name) throws IOException{
    	Proxy proxy = balanceNode.requestProxy();
		if(proxy != null){
			proxy.setAvailability(false);
			File fileReaded = proxy.getFile(name);
			proxy.setAvailability(true);
			FileUtil.writeFile(FileUtil.getHome(), fileReaded);
		}else{
			System.out.println("Nenhum proxy disponível");
		}
	}
   
    private void delete(String name) throws IOException{
    	Proxy proxy = balanceNode.requestProxy();
		
    	if(proxy != null){
    		proxy.setAvailability(false);
			proxy.deleteFile(name);
			proxy.setAvailability(true);
    	}else{
    		System.out.println("Nenhum proxy disponível");
    	}
    }
    
    private void update(File file) throws RemoteException{
    	Proxy proxy = balanceNode.requestProxy();
    	if(proxy != null){
    		proxy.setAvailability(false);
			proxy.updateFile(file);
			proxy.setAvailability(true);
    	}else{
    		System.out.println("Nenhum proxy disponível");
    	}
    }
    

}
