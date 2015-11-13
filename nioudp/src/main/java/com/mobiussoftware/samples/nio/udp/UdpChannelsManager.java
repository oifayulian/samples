package com.mobiussoftware.samples.nio.udp;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;


public class UdpChannelsManager extends TimerTask{

	
	private Selector selector;
	private final static Logger logger = Logger.getLogger(UdpChannelsManager.class);
	private Timer timer = new Timer();
	
	public UdpChannelsManager()
	{		
		init();
		timer.scheduleAtFixedRate(this, 200, 200);	
	}
	
	private void init()
	{
		try {
			selector = SelectorProvider.provider().openSelector();
			
		} catch (IOException e) {
			logger.error(e);
		}
		
	}
	
	public void registerChannel(DatagramChannel channel, UdpHandler handler) throws IOException
	{		
		SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
		key.attach(handler);
	}

	@Override
	public void run() 
	{
		try 
		{			
            selector.selectNow();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
           
            while (it.hasNext()) 
            {
                SelectionKey key = it.next();
                it.remove();
                
                UdpHandler handler = (UdpHandler) key.attachment();

                if (handler == null) 
                    continue;
                
                if (key.isReadable()) 
                    handler.receive();                  
            }
            
            selector.selectedKeys().clear();        
		} 
		catch (IOException e) 
		{
            logger.error(e);            
        }	
	}
	
	public void stop()
	{
		timer.cancel();
	}
}
