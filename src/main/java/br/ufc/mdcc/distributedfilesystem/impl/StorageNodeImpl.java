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
	
		return FileUtil.readFile(directory, name);
	}


	public void writeFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
	
		try {
			FileUtil.writeFile(directory, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteFile(String name) throws RemoteException {
		// TODO Auto-generated method stub
		FileUtil.deleteFile(directory, name);
	}

	public void updateFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
		writeFile(file);
	}
	
	

}
