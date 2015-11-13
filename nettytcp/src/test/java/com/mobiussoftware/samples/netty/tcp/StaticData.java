package com.mobiussoftware.samples.netty.tcp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class StaticData 
{
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToHex(byte[] bytes) 
	{
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) 
	    {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	        hexChars[j * 2] = hexArray[v >>> 4];	        
	    }
	    
	    if(hexChars[hexChars.length-1]!='F')
	    	return new String(hexChars);
	    
	    return new String(hexChars,0,hexChars.length-1);
	}	
	
	public static byte[] loadBytesFromResource(String resource) throws IOException
	{
		InputStream is=StaticData.class.getClassLoader().getResourceAsStream(resource);
    	byte[] result=IOUtils.toByteArray(is);
    	is.close();
    	return result;
	}
	
	public static String[] getResourcesList(String folder) throws IOException
	{
		File currFile=new File(StaticData.class.getClassLoader().getResource(folder).getPath());
		File[] allFiles=currFile.listFiles();
		String[] result=new String[allFiles.length];
		for(int i=0;i<allFiles.length;i++)
			result[i]=allFiles[i].getName();
		
		return result;
	}
}
