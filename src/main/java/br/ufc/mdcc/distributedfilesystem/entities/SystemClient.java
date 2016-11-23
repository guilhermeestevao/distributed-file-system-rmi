package br.ufc.mdcc.distributedfilesystem.entities;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;

public class SystemClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name = "balancenode";
		try {
			Registry registry = LocateRegistry.getRegistry(null, 1099);
			
			BalanceNode stub = (BalanceNode) registry.lookup(name);
			
			Proxy proxy = stub.requestProxy();
			
			System.out.println("Selecionado "+proxy.getName());
			
			proxy.deleteFile("Untitled.ipynb");
			
			
			
			/*
			proxy.writeFile(new File("/home/guilherme/Untitled.ipynb"));
			
			
			
			
			
			File file = proxy.getFile("Untitled.ipynb");
			
			File root = new File("/home/guilherme");
			
			FileUtil.writeFile(root, file);
			
			
			
			
			proxy.updateFile(new File("/home/guilherme/Untitled.ipynb"));
			*/
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
