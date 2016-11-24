package br.ufc.mdcc.distributedfilesystem.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;
import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;
import br.ufc.mdcc.distributedfilesystem.util.FileUtil;
import br.ufc.mdcc.distributedfilesystem.util.HashUtil;
import br.ufc.mdcc.distributedfilesystem.util.PartitionUtil;

public class ProxyImpl implements Proxy{

	private boolean status;
	private String name;
	private Map<Integer, String[]> partitionsMap;
	private static final String PARTITIONS_FILE_NAME="partitions";
	
	public ProxyImpl(String name){
		this.status = true;
		this.name = name;
		partitionsMap = PartitionUtil.fillPartitionsMap(PARTITIONS_FILE_NAME);
		System.out.println("Proxy "+name+" executando");
	}

	public boolean isAvailable() throws RemoteException {
		// TODO Auto-generated method stub
		return status;
	}

	public void setAvailability(boolean status) throws RemoteException {
		// TODO Auto-generated method stub
		this.status = status;
	}

	public File getFile(String name) throws RemoteException {

		StorageNode[] nodes = getStorageNodes(name);
		
		for(StorageNode node : nodes){
			File file = node.getFile(name);
			if (file != null){
				System.out.println("encontrando em "+name);
				return file;
			}
			else
				System.out.println("Não encontrado em "+node);
		}

		return null;
	}

	public void writeFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
		String name = file.getName();
		
		StorageNode[] nodes = getStorageNodes(name);
		
		for(StorageNode node : nodes){
			try{
				if(node != null)
					node.writeFile(file);
			}catch(Exception e){
				continue;		
			}
		}
		
	}

	public void deleteFile(String name) throws RemoteException {
		// TODO Auto-generated method stub
		StorageNode[] nodes = getStorageNodes(name);
		
		for(StorageNode node : nodes){
			try{
				if(node != null)
					node.deleteFile(name);
			}catch(Exception e){
				continue;		
			}
		}
	}

	public void updateFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
		String name = file.getName();
		
		StorageNode[] nodes = getStorageNodes(name);
		
		for(StorageNode node : nodes){
			try{
				if(node != null)
					node.updateFile(file);
			}catch(Exception e){
				continue;		
			}
		}
	}

	

	private StorageNode[] getStorageNodes(String name) throws RemoteException{
		
		int partition = getPartition(name);
		
		
		String[] names = partitionsMap.get(partition); 
		
		StorageNode[] nodes = new StorageNode[names.length];
		
		Registry registry = LocateRegistry.getRegistry(null, 1099);
		
		for(int i = 0; i< names.length; i++){
			
			String nameNode = names[i];
			StorageNode stub;
			try {
				stub = (StorageNode) registry.lookup(nameNode);
				
				nodes[i] = stub;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Nó de armazenamento "+nameNode+" não está disponivel");
				continue;
			}
		}
		
		return nodes;
	}
	
	
	private int getPartition(String name){
		
		int partition = 0;
		try {
			
			BigInteger hash = HashUtil.getHashMD5(name);
			
			partition = (int) (hash.longValue() % partitionsMap.size());
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
		
		return partition;
	}

	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return name;
	}

}
