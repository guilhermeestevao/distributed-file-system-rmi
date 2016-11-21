package br.ufc.mdcc.distributedfilesystem.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
	
	private static String FILE_SYSTEM_DEFAULT_FOLDER = "FileSystem";
	
	private static File getHome(){
		String currentUsersHomeDir = System.getProperty("user.home");
		
		File home = new File(currentUsersHomeDir + File.separator + FILE_SYSTEM_DEFAULT_FOLDER);
		
		if (!home.exists())
			home.mkdir();
		
		return home;
	}
	
	public static File readDirectory(String name){
		
		File home = getHome();
		
		String directoryName = home.getAbsolutePath()+File.separator + name;
	
		File file = new File(directoryName);
		
		if(!file.exists())
			file.mkdir();
		
		File syncFile = new File(file, ".sync");
		
		if(!syncFile.exists()){
			try {
				syncFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	
		return file;
	}
	
	public static File getPartitionMapFile(String file){
		File home = getHome();
		
		File mapFile = new File(home.getAbsolutePath() + File.separator + file);
		
		return mapFile;
		
	}
	
	public static void writeFile(File root, File file) throws IOException{
		
		String fileName = root.getAbsolutePath() + File.separator + file.getName();
		
		File newFile = new File(fileName);
				
		byte[] data = Files.readAllBytes(file.toPath());
		
		FileOutputStream out = new FileOutputStream(newFile);
		out.write(data);
		out.close();
	}
	
	public static File readFile(File root, String name){
	
		File target = new File(root, name);
		
		if(target.exists())
			return target;
		else 
			return null;
	}
	
	public static void deleteFile(File root, String name){
		File target = new File(root, name);
		target.delete();
	}
	
	public static void saveFileMap(File root, Map<String, Integer> mapFiles){
		
		File file = new File(root, ".sync");
		try {
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(mapFiles);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static Map<String, Integer> getMapFiles(File root){
		
		File file = new File(root, ".sync");
		
		FileInputStream fin;
		try {
			fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			Map<String, Integer> map = (Map<String, Integer>) ois.readObject();
			return map;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return new HashMap<String, Integer>();
	}
	

}
