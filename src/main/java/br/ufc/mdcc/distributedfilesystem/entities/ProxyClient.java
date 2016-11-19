package br.ufc.mdcc.distributedfilesystem.entities;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import br.ufc.mdcc.distributedfilesystem.impl.ProxyImpl;
import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;

public class ProxyClient {
	
	public static void main(String[] args) {
		
		try {
			
			Scanner sc = new Scanner(System.in);
			
			String name = sc.next();
			
			ProxyImpl proxy = new ProxyImpl(name);
			Proxy stub = (Proxy) UnicastRemoteObject.exportObject(proxy, 0);
            Registry registry = LocateRegistry.getRegistry();
			registry.bind(name, stub);
			
			registryOnBalanceNodeList(proxy);
			
			System.err.println("Proxy "+proxy+"está disponivel");
        
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private static void registryOnBalanceNodeList(Proxy proxy){
		String name = "balancenode";
		try {
			Registry registry = LocateRegistry.getRegistry(null, 1099);
			
			BalanceNode stub = (BalanceNode) registry.lookup(name);
			
			stub.addProxy(proxy);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
