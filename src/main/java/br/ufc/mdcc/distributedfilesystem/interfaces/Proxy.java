package br.ufc.mdcc.distributedfilesystem.interfaces;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Proxy extends Remote{
	//Metodos acessados pelo nó de balanceamento para 
	//determinar a disponibilidade de umproxy
	abstract boolean isAvailable() throws RemoteException;
	abstract void setAvailability(boolean status) throws RemoteException;
	public abstract String getName() throws RemoteException;
	
	//Metod de leiutra e escreta de soliciados por um cliente
	abstract File getFile(String name) throws RemoteException;
	abstract File[] getAllFiles() throws RemoteException;
	abstract void writeFile(File file) throws RemoteException;
	abstract void deleteFile(File file) throws RemoteException;
	abstract void updateFile(File file) throws RemoteException;
	
	
}
