package br.ufc.mdcc.distributedfilesystem.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
	
	
	public static BigInteger getHashMD5(String name) throws NoSuchAlgorithmException{
		MessageDigest m = MessageDigest.getInstance("MD5");

	    m.update(name.getBytes(),0,name.length());

	    BigInteger b = new BigInteger(1,m.digest());
	    
		return b;	
	}

}
