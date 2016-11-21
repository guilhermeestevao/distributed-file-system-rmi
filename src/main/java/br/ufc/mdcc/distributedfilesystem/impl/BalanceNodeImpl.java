package br.ufc.mdcc.distributedfilesystem.impl;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;

public class BalanceNodeImpl implements BalanceNode{
	
	private List<Proxy> proxiesList = new ArrayList<Proxy>();
	
	public void addProxy(Proxy proxy) throws RemoteException{
		// TODO Auto-generated method stub		
		proxiesList.add(proxy);

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
