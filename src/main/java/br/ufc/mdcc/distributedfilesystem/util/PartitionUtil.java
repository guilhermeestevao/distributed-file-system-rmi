package br.ufc.mdcc.distributedfilesystem.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PartitionUtil {


	public static HashMap<Integer, String[]> fillPartitionsMap(String filename){

		HashMap<Integer, String[]> partitionsMap = new HashMap<Integer, String[]>();
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
		
		return partitionsMap;

	}
}
