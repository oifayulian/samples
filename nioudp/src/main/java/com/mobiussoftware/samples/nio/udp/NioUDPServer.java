package com.mobiussoftware.samples.nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.apache.log4j.Logger;

public class NioUDPServer implements UdpHandler
{
	private DatagramChannel udpChannel;
	private ByteBuffer receiveBuffer = ByteBuffer.allocate(1000);
	private InetSocketAddress serverAddress;
	private Logger logger=Logger.getLogger(NioUDPServer.class);
	
	public NioUDPServer(String hostname,int port)
	{	
		serverAddress=new InetSocketAddress(hostname, port);
	}
	
	public void startServer(UdpChannelsManager manager) throws IOException
	{
		udpChannel=DatagramChannel.open();		
		udpChannel.configureBlocking(false);
		udpChannel.bind(serverAddress);
		manager.registerChannel(udpChannel, this);		
	}
		
	public void stopServer() throws IOException
	{
		logger.info("Closing UDP listener");
		udpChannel.close();
		logger.info("UDP Listener stopped");					
	}
	
	public void receive()
	 {
		receiveBuffer.clear();
		SocketAddress address=null;
		try
		{
			address=udpChannel.receive(receiveBuffer);
			while(address!=null)
			{
				receiveBuffer.flip();
				//TODO:process packet
				receiveBuffer.flip();
				receiveBuffer.clear();
				address=udpChannel.receive(receiveBuffer);				
			}
		}	
		catch(IOException ex)
		{
			logger.error("An error occured while receiving packet",ex);
		}
	 }
	
	public void sendData(InetSocketAddress address,ByteBuffer buffer) throws IOException
	{				
		udpChannel.write(buffer);		
	}
}