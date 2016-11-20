package br.ufc.mdcc.distributedfilesystem.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.mdcc.distributedfilesystem.interfaces.BalanceNode;
import br.ufc.mdcc.distributedfilesystem.interfaces.Proxy;
import br.ufc.mdcc.distributedfilesystem.interfaces.StorageNode;
import br.ufc.mdcc.distributedfilesystem.util.FileUtil;

public class ProxyImpl implements Proxy{

	private boolean status;
	private String name;
	private Map<Integer, String[]> partitionsMap;
	private static final String PARTITIONS_FILE_NAME="partitions";
	
	public ProxyImpl(String name){
		this.status = true;
		this.name = name;
		fillPartitionsMap(PARTITIONS_FILE_NAME);

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


		return null;
	}

	public File[] getAllFiles() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeFile(File file) throws RemoteException {
		// TODO Auto-generated method stub
		String name = file.getName();
		
		System.out.println(name);
		
		StorageNode[] nodes = getStorageNodes(name);
		
		for(StorageNode node : nodes){
			node.writeFile(file);
		}
		
		
	}

	public void deleteFile(File file) throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void updateFile(File file) throws RemoteException {
		// TODO Auto-generated method stub

	}

	private void fillPartitionsMap(String filename){
		
		partitionsMap = new HashMap<Integer, String[]>();
		File file = FileUtil.getPartitionMapFile(filename);


		try{

			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null)   {

				String[] objects = strLine.split(":");
				
				int partition = Integer.parseInt(objects[0]);
				
				String[] nodes = objects[1].split(",");
				
				partitionsMap.put(partition, nodes);
			
			}
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}


	}

	private StorageNode[] getStorageNodes(String name) throws RemoteException{
		int hash = name.hashCode();

		int partition = hash % partitionsMap.size();
		
		String[] names = partitionsMap.get(partition); 
		
		System.out.println("Hash "+hash+" partitions "+partition+" "+names.toString());
		
		StorageNode[] nodes = new StorageNode[names.length];
		
		Registry registry = LocateRegistry.getRegistry(null, 1099);
		
		for(int i = 0; i< names.length; i++){
			
			String nameNode = names[i];
			StorageNode stub;
			try {
				stub = (StorageNode) registry.lookup(nameNode);
				nodes[i] = stub;
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				continue;
			}
			
			
		}
		
		return nodes;
	}

	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return name;
	}

}
