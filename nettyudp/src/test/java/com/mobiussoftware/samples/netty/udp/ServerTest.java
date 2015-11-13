package com.mobiussoftware.samples.netty.udp;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerTest
{
	public ServerTest()
	{
		
	}
	
	@BeforeClass
    public static void setUpClass() throws Exception 
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception 
    {
    }

    @Before
    public void setUp() 
    {
    	 BasicConfigurator.resetConfiguration();
         BasicConfigurator.configure();         
    }

    @After
    public void tearDown() 
    {
    }
    
    @Test
    public void testParser() 
    {
    	
    }
    
    @Test
    public void testServer() 
    {
    	
    }    
}