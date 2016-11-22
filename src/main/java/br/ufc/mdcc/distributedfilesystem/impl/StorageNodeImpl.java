package br.ufc.mdcc.distributedfilesystem.impl;

import java.io.File;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Map.Entry;

import br.ufc.mdcc.distributedfilesystem.enums.Operation;
import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;
import br.ufc.mdcc.distributedfilesystem.util.FileUtil;
import br.ufc.mdcc.distributedfilesystem.util.PartitionUtil;

public class StorageNodeImpl implements StorageNode{
	
	private String name;
	private File directory;
	private Map<Integer, String[]> partitionsMap;
	private Map<String, Integer> mapFiles; 
	private static final String PARTITIONS_FILE_NAME="partitions";
	
	public StorageNodeImpl(String name){
		this.name = name;
		this.directory = FileUtil.readDirectory(name);
		partitionsMap = PartitionUtil.fillPartitionsMap(PARTITIONS_FILE_NAME);
		mapFiles = FileUtil.getMapFiles(directory);
		
		for(Entry<Integer, String[]> entry : partitionsMap.entrySet()){
			
			boolean isAtPartition = verifyPartition(name, entry.getValue());
			
			if(isAtPartition){
							
				String[] siblings = partitionsMap.get(entry.getKey());
				
				sync(name, siblings);
			}	
		}	
	}	

	public File getFile(String name) throws RemoteException {
	
		return FileUtil.readFile(directory, name);
	}


	public void writeFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
	
		try {
			FileUtil.writeFile(directory, file);
			updateFileMap(file.getName(), Operation.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateFileMap(String name, Operation opc) {
		// TODO Auto-generated method stub
		
		switch(opc){
		case CREATE:
			mapFiles.put(name, 1);
			break;
		case DELETE:
			mapFiles.put(name, -1);
			break;
		case UPDATE:
			int lamport = mapFiles.get(name);
			lamport++;
			mapFiles.put(name, lamport);
			break;
		}
		
		FileUtil.saveFileMap(directory, mapFiles);
	}

	public void deleteFile(String name) throws RemoteException {
		// TODO Auto-generated method stub
		FileUtil.deleteFile(directory, name);
		updateFileMap(name, Operation.DELETE);
	}

	public void updateFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			
			FileUtil.writeFile(directory, file);
			updateFileMap(file.getName(), Operation.UPDATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateFile(File file, int lamport) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			
			FileUtil.writeFile(directory, file);
					
			this.mapFiles.put(file.getName(), lamport);
			
			FileUtil.saveFileMap(directory, mapFiles);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNodeName() throws RemoteException {
		// TODO Auto-generated method stub
		return name;
	}
	
	private boolean verifyPartition(String name, String[] nodes){
		
		for(String nameNode : nodes){
			if(nameNode.equals(name))
				return true;
		}
		
		return false;
	}
	
	private void sync(String nodeNAme, String[] nodes){
		
		for(String name: nodes){
			
			if(!name.equals(nodeNAme)){
			
				
					Registry registry;
					try {
						registry = LocateRegistry.getRegistry();
						StorageNode storageNode = (StorageNode) registry.lookup(name);
						
						compareMapFiles(storageNode);
						
						
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
				
						continue;
					}
				
			}
			
			
		}
	
		
	}

	private void compareMapFiles(StorageNode storageNode) throws RemoteException {
		// TODO Auto-generated method stub
		Map<String, Integer> mapFileList = storageNode.getMapFileList();
		
		for(Entry<String, Integer> entry : mapFileList.entrySet()){
			
			if(this.mapFiles.containsKey(entry.getKey())){
				
				int lamportThis = this.mapFiles.get(entry.getKey());
				
				int lamportOther = entry.getValue(); 
				
				if(lamportOther > lamportThis){
				
					transferFile(entry, storageNode);
					
				}else if(lamportOther == -1){
					deleteFile(entry.getKey());
				}
					
			}else{
				
				transferFile(entry, storageNode);
				
			}
	
		}
		
	
		
		
		
	}

	

	private void transferFile(Entry<String, Integer> entry, StorageNode origin) throws RemoteException {
		// TODO Auto-generated method stub
		
		File root = origin.getDirectoryNode();
		
		File target = FileUtil.readFile(root, entry.getKey());
		
		updateFile(target, entry.getValue());
		
	}

	public Map<String, Integer> getMapFileList() throws RemoteException{
		// TODO Auto-generated method stub
		return mapFiles;
	}

	public File getDirectoryNode() throws RemoteException {
		// TODO Auto-generated method stub
		return directory;
	}
	
	

}
