package br.ufc.mdcc.distributedfilesystem.entities;

import java.rmi.AccessException;
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
		
		Proxy stub = null;
		Registry registry = null;
		Scanner sc = new Scanner(System.in);
		String name = sc.next();
		try {
			
			ProxyImpl proxy = new ProxyImpl(name);
			stub = (Proxy) UnicastRemoteObject.exportObject(proxy, 0);
            registry = LocateRegistry.getRegistry();
			registry.bind(name, stub);
			registryOnBalanceNodeList(proxy);
			
			System.err.println("Proxy "+proxy+"está disponivel");
        
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	private static void registryOnBalanceNodeList(Proxy proxy){
		String name = "balancenode";
		try {
			Registry registry = LocateRegistry.getRegistry(null, 1099);
			
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
