package com.mobiussoftware.samples.nio.udp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class StaticData 
{
	public static byte[] loadBytesFromResource(String resource) throws IOException
	{
		InputStream is=StaticData.class.getClassLoader().getResourceAsStream(resource);
    	byte[] result=IOUtils.toByteArray(is);
    	is.close();
    	return result;
	}
}