package br.ufc.mdcc.distributedfilesystem.impl;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;

public class BalanceNodeImpl implements BalanceNode{
	
	private List<String> proxiesList = new ArrayList<String>();
	
	public BalanceNodeImpl(){
		System.out.println("Nó de balaceamento executando!");
	}
	
	public void addProxy(String proxy) throws RemoteException{
		// TODO Auto-generated method stub
		if(!proxiesList.contains(proxy)){
			proxiesList.add(proxy);
			System.out.println(proxy+ "adicionado a lista do nó de balanceamento");
		}
	}



	public synchronized Proxy requestProxy() throws RemoteException{
		
		for(String proxyName :proxiesList){
			System.out.println("Verificando: "+proxyName);
			try {
				Registry registry = LocateRegistry.getRegistry();
				Proxy stub = (Proxy) registry.lookup(proxyName);

				if (stub.isAvailable()){
					
					System.out.println("Escolhido "+stub.getName());
					
					return stub;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				System.out.println(proxyName +" não está mais disponivel");
			
				continue;
			}
			
			
		}
		
		return null;
	}

}
