package com.mobiussoftware.samples.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

public class NettyUDPServer 
{
	private EventLoopGroup group;
	private Bootstrap connectionlessBootstrap;
	
	private Channel serverChannel;
	
	private int port;
	private String hostname;
	
	private Logger logger=Logger.getLogger(NettyUDPServer.class);
	
	public NettyUDPServer(String hostname,int port)
	{
		this.hostname=hostname;
		this.port=port;
	}
	
	public void startServer()
	{
		group = new NioEventLoopGroup(16);
		connectionlessBootstrap=new Bootstrap();
		//optional , may increase buffer size on channel
		//connectionlessBootstrap.option(ChannelOption.SO_SNDBUF, 262144);
		//connectionlessBootstrap.option(ChannelOption.SO_RCVBUF, 262144);
		connectionlessBootstrap.channel(NioDatagramChannel.class);
		connectionlessBootstrap.group(group);
		
		final NettyUDPServer me=this;
		connectionlessBootstrap.handler(new NettyUDPPacketHandler(me));					
						
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
			future=connectionlessBootstrap.bind(new InetSocketAddress("0.0.0.0", port));
		else
			future=connectionlessBootstrap.bind(new InetSocketAddress(current, port));
			
		future.awaitUninterruptibly();
		serverChannel=future.channel();
		logger.info("UDP Listener started");				
	}
	
	public void stopServer()
	{
		logger.info("Closing UDP listener");
		ChannelFuture channelFuture=serverChannel.close();
		channelFuture.awaitUninterruptibly();
		group.shutdownGracefully();
		logger.info("UDP Listener stopped");					
	}
	
	public void sendData(InetSocketAddress address,ByteBuf buffer)	
	{				
		serverChannel.writeAndFlush(new DatagramPacket(buffer, address));		
	}
}