package br.ufc.mdcc.distributedfilesystem.entities;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import br.ufc.mdcc.distributedfilesystem.impl.BalanceNodeImpl;
import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;

public class BalanceNodeClient {
	

	public static void main(String[] args) {
		
		String name = "balancenode";
		try {
			BalanceNodeImpl node = new BalanceNodeImpl();
			BalanceNode stub = (BalanceNode) UnicastRemoteObject.exportObject(node, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind(name, stub);
			System.err.println("Nó de balanceamneto executando!");
        
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		

	}

}
