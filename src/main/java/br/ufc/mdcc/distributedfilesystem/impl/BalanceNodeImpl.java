package br.ufc.mdcc.distributedfilesystem.impl;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;

public class BalanceNodeImpl implements BalanceNode{
	
	private List<Proxy> proxiesList = new ArrayList<Proxy>();

	
	public void addProxy(Proxy proxy) throws RemoteException{
		// TODO Auto-generated method stub
		System.out.println("Adicionado "+proxy.getName() + " na lista de proxies do Nó de balanceamento");
		proxiesList.add(proxy);
		System.out.println("Tamanho atual"+proxiesList.size());
	}



	public Proxy requestProxy() throws RemoteException{
		
		for(Proxy proxy : proxiesList){
			if (proxy.isAvailable()){
				return proxy;
			}
		}
		
		return null;
	}

}
