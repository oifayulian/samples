package com.mobiussoftware.samples.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.m2mair.netqos.interfaces.objects.IPAddressCompare;
import com.m2mair.netqos.interfaces.objects.IPAddressType;

public class TCPServer
{
    private int port;
    private String hostname;
    
	private Logger logger=Logger.getLogger(TCPServer.class);
		
	private ServerBootstrap bootstrap;
	private Channel serverChannel;
	private EventLoopGroup parentGroup;
	private EventLoopGroup workerGroup;
	
	public TCPServer(String hostname,int port)
	{
		this.hostname=hostname;
		this.port=port;
	}
	
	public void startServer()
	{
		parentGroup = new NioEventLoopGroup(16);
		workerGroup= new NioEventLoopGroup(16);		
		bootstrap = new ServerBootstrap();
		bootstrap.group(parentGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() 
		{
			@Override
			public void initChannel(SocketChannel ch) throws Exception 
			{
				ch.pipeline().addLast("decoder",new TCPPacketDecoder());
				ch.pipeline().addLast("handler",new TCPPacketHandler());				
			}
		});					
								
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.option(ChannelOption.SO_SNDBUF, 262144);
		bootstrap.option(ChannelOption.SO_RCVBUF, 262144);
		
		logger.info("Binding to:" + hostname + ",port:" + port);
		byte[] address;
		IPAddressType type=IPAddressCompare.getAddressType(hostname);
		if(type==IPAddressType.IPV4)
			address=IPAddressCompare.textToNumericFormatV4(hostname);
		else
			address=IPAddressCompare.textToNumericFormatV6(hostname);
		
		InetAddress current=null;
		try
		{
			current=InetAddress.getByAddress(address);
		}
		catch(Exception ex)
		{
			
		}
		
		ChannelFuture future;
		if(current==null)
			future=bootstrap.bind(new InetSocketAddress("0.0.0.0",port));
		else
			future=bootstrap.bind(new InetSocketAddress(current,port));
		
		future.awaitUninterruptibly();
		serverChannel=future.channel();
		logger.info("TCP Listener started");				
	}
	
	public void stopServer()
	{
		logger.info("Closing TCP listener");
		ChannelFuture channelFuture=serverChannel.close();
		channelFuture.awaitUninterruptibly();
		logger.info("TCP Listener stopped");	
		
		parentGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();		
	}
}