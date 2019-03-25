package cn.sqwsy.health365interface.service.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class HashUtil {
	
	public static final String SHA1 = "SHA-1";
	public static final String MD5 = "MD5";
	
	/**
	 * MD5加密
	 * @param input
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String MD5Hashing(String input){
		String output = "";
		MessageDigest md;		
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] original = input.getBytes("utf-8");
            byte[] bytes = md.digest(original);
            for (int i = 0; i<bytes.length; i++) {
            	output += Integer.toHexString((bytes[i] & 0xff) + 0x100).substring(1);
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
        return output;
	}
}
