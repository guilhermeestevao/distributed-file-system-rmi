package br.ufc.mdcc.distributedfilesystem.interfaces;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BalanceNode extends Remote{
	
	//Metodos executados por um cliente
	public abstract Proxy requestProxy();
	
	//Metodos executads pelo proxies
	public abstract void addProxy(Proxy proxy) throws RemoteException;
	
	
}
