package br.ufc.mdcc.distributedfilesystem.entities;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import br.ufc.mdcc.distributedfilesystem.impl.StorageNodeImpl;
import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;

public class StorageNodeClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		String name = sc.next();
		
		StorageNodeImpl node = new StorageNodeImpl(name);
		StorageNode stub = null;
		Registry registry = null;
		try {
			stub = (StorageNode) UnicastRemoteObject.exportObject(node, 0);
			registry = LocateRegistry.getRegistry();
			registry.bind(name, stub);
			
			System.err.println("Nó "+node+" está disponivel");
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

}
