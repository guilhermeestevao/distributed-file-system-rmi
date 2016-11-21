package br.ufc.mdcc.distributedfilesystem.interfaces;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface StorageNode extends Remote{
	
	abstract String getNodeName()throws RemoteException;
	abstract File getFile(String name) throws RemoteException;
	abstract void writeFile(File file) throws RemoteException;
	abstract void deleteFile(String name) throws RemoteException;
	abstract void updateFile(File file) throws RemoteException;
	
	abstract File getDirectoryNode() throws RemoteException;
	abstract Map<String, Integer> getMapFileList()throws RemoteException;
}
