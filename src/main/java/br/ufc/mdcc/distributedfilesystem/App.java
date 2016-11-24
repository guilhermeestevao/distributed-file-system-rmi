package br.ufc.mdcc.distributedfilesystem;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import br.ufc.mdcc.distributedfilesystem.entities.Event;
import br.ufc.mdcc.distributedfilesystem.impl.BalanceNodeImpl;
import br.ufc.mdcc.distributedfilesystem.impl.ProxyImpl;
import br.ufc.mdcc.distributedfilesystem.impl.StorageNodeImpl;
import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;
import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;

public class App {
	
	private static Options options = new Options();
	
    public static void main( String[] args ){
    	setupOptions();
    	
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmdLine = parser.parse(options, args);
			
			if(cmdLine.hasOption("b")){
				String nameBn = cmdLine.getOptionValue("b");
				createBalanceNode(nameBn);
			}
			if(cmdLine.hasOption("p")){
				String nameProxy = cmdLine.getOptionValue("p");
				createProxyNode(nameProxy);
			}
			if(cmdLine.hasOption("s")){
				String nameSn = cmdLine.getOptionValue("s");
				createStorageNode(nameSn);
			}if(cmdLine.hasOption("c")){
				String nameClient = cmdLine.getOptionValue("c");
				createClientNode(nameClient);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
    	
    }
    
    private static void createClientNode(String nameClient) throws AccessException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		Event event = new Event(nameClient);
		event.start();
	}

	private static void setupOptions(){
		Option proxy  = Option.builder("p")                 
				.required(false)                 
				.desc("Processo que representa um proxy")                 
				.longOpt("proxy")                 
				.hasArg()                 
				.build();

		Option client  = Option.builder("c")                 
				.required(false)                 
				.desc("Processo que representa um cliente")                 
				.longOpt("cliente")                 
				.hasArg()                 
				.build();
		
		Option balancenode  = Option.builder("b")                 
				.required(false)                 
				.desc("Processo que represnta o nó de balanceamneto")                 
				.longOpt("balancenode") 
				.hasArg()
				.build();
		
		Option storagenode  = Option.builder("s")                 
				.required(false)                 
				.desc("Processo que represetna um nó de armazenamento")                 
				.longOpt("balancenode")    
				.hasArg()
				.build();

		options.addOption(proxy);
		options.addOption(client);
		options.addOption(balancenode);
		options.addOption(storagenode);

	}
    
    private static void createBalanceNode(String name) throws RemoteException, AlreadyBoundException{
    	BalanceNodeImpl node = new BalanceNodeImpl();
		BalanceNode stub = (BalanceNode) UnicastRemoteObject.exportObject(node, 0);
        Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind(name, stub);
    }
    
    private static void createProxyNode(String name) throws RemoteException{
    	ProxyImpl proxy = new ProxyImpl(name);
    	Proxy stub = (Proxy) UnicastRemoteObject.exportObject(proxy, 0);
    	Registry registry = LocateRegistry.getRegistry();
		try {
			registry.bind(name, stub);
			registryOnBalanceNodeList(proxy);
				
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			try {
				registry.rebind(name, stub);
				registryOnBalanceNodeList(proxy);
			} catch (AccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
    }
    
    private static void createStorageNode(String name) throws RemoteException{
    	
    	StorageNodeImpl node = new StorageNodeImpl(name);
    	StorageNode stub = null;
		Registry registry = null;
		try {
			
			stub = (StorageNode) UnicastRemoteObject.exportObject(node, 0);;
			registry = LocateRegistry.getRegistry();
	    	
			registry.bind(name, stub);
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			try {
				registry.rebind(name, stub);
			} catch (AccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

    }
    
    
    
	private static void registryOnBalanceNodeList(ProxyImpl proxy) {
		// TODO Auto-generated method stub
		String name = "balancenode";
		try {
			Registry registry = LocateRegistry.getRegistry();
			
			BalanceNode stub = (BalanceNode) registry.lookup(name);
			
			stub.addProxy(proxy.getName());
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    
}
