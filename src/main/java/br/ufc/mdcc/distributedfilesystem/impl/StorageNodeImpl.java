package br.ufc.mdcc.distributedfilesystem.impl;

import java.io.File;

import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;

public class StorageNodeImpl implements StorageNode{
	
	private String name;
	
	public StorageNodeImpl(String name){
		this.name = name;
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
	
	

}
