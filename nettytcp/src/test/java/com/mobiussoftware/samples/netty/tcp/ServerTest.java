package com.mobiussoftware.samples.netty.tcp;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerTest 
{
	@BeforeClass
	public static void setUpClass() throws Exception 
	{
		BasicConfigurator.resetConfiguration();
    	BasicConfigurator.configure();    
	}

    @AfterClass
    public static void tearDownClass() throws Exception 
    {
    }

    @Before
    public void setUp() 
    {
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