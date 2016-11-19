package br.ufc.mdcc.distributedfilesystem.entities;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import br.ufc.mdcc.distributedfilesystem.impl.ProxyImpl;
import br.ufc.mdcc.distributedfilesystem.impl.StorageNodeImpl;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;
import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;

public class StorageNodeClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		String name = sc.next();
		
		StorageNodeImpl node = new StorageNodeImpl(name);
		StorageNode stub;
		try {
			stub = (StorageNode) UnicastRemoteObject.exportObject(node, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.bind(name, stub);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		

		
		System.err.println("Nó "+node+"está disponivel");

	}

}
