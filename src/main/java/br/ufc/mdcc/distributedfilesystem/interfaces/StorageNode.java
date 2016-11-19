package br.ufc.mdcc.distributedfilesystem.interfaces;

import java.io.File;
import java.rmi.Remote;

public interface StorageNode extends Remote{
	
	abstract File getFile(String name);
	abstract File[] getAllFiles();
	abstract void writeFile(File file);
	abstract void deleteFile(File file);
	abstract void updateFile(File file);
}
