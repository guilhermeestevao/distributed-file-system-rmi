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
		proxiesList.add(proxy);
		System.out.println("Adicionado um proxy novo");
		System.err.println(proxiesList.size());
	}



	public Proxy requestProxy() {
		//Retorna algum proxy disponivel na lista
		return null;
	}

}
