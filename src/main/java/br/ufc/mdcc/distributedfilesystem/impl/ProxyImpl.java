package br.ufc.mdcc.distributedfilesystem.impl;

import java.io.File;
import java.rmi.RemoteException;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;
import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;

public class ProxyImpl implements Proxy{
	
	private boolean status;
	private String name;
	
	public ProxyImpl(String name){
		this.status = true;
		this.name = name;
	}

	public boolean isAvailable() throws RemoteException {
		// TODO Auto-generated method stub
		return status;
	}

	public void setAvailability(boolean status) throws RemoteException {
		// TODO Auto-generated method stub
		this.status = status;
	}

	public File getFile(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public File[] getAllFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeFile(File file) {
		// TODO Auto-generated method stub
		
	}

	public void deleteFile(File file) {
		// TODO Auto-generated method stub
		
	}

	public void updateFile(File file) {
		// TODO Auto-generated method stub
		
	}
	
	private StorageNode[] getStorageNodes(String filename){
		//Retorna os nó de armazenamneto baseado no hash do nome
		return null; 	
	}

}
