package br.ufc.mdcc.distributedfilesystem.interfaces;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StorageNode extends Remote{
	
	abstract File getFile(String name) throws RemoteException;
	abstract File[] getAllFiles() throws RemoteException;
	abstract void writeFile(File file) throws RemoteException;
	abstract void deleteFile(File file) throws RemoteException;
	abstract void updateFile(File file) throws RemoteException;
}
