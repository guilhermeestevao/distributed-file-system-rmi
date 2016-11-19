package br.ufc.mdcc.distributedfilesystem.interfaces;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Proxy extends Remote{
	//Metodos acessados pelo nó de balanceamento para 
	//determinar a disponibilidade de umproxy
	abstract boolean isAvailable() throws RemoteException;
	abstract void setAvailability(boolean status) throws RemoteException;
	
	//Metod de leiutra e escreta de soliciados por um cliente
	abstract File getFile(String name);
	abstract File[] getAllFiles();
	abstract void writeFile(File file);
	abstract void deleteFile(File file);
	abstract void updateFile(File file);
	
	
}
