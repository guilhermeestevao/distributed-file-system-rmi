package br.ufc.mdcc.distributedfilesystem.entities;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;

public class SystemClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name = "balancenode";
		try {
			Registry registry = LocateRegistry.getRegistry(null, 1099);
			
			BalanceNode stub = (BalanceNode) registry.lookup(name);
			
			//Operações de read e write aqui 
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
