package br.ufc.mdcc.distributedfilesystem.impl;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;
import br.ufc.mdcc.distributedfilesystem.util.FileUtil;

public class StorageNodeImpl implements StorageNode{
	
	private String name;
	private File directory;
	
	public StorageNodeImpl(String name){
		this.name = name;
		this.directory = FileUtil.readDirectory(name);
	}

	public File getFile(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public File[] getAllFiles() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(name+" "+name+"chamado para escrever no arquivo");
		try {
			FileUtil.writeFile(directory, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void updateFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	

}
