package com.mobiussoftware.samples.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import org.apache.log4j.Logger;


public class NettyUDPPacketHandler extends SimpleChannelInboundHandler<DatagramPacket>
{
	private final static Logger logger = Logger.getLogger(NettyUDPPacketHandler.class);  
	
	private NettyUDPServer serverBean;
	
	public NettyUDPPacketHandler(NettyUDPServer serverBean)
	{
		this.serverBean=serverBean;		
	}
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) 
	{
		try
		{
			//read packet content
			InetSocketAddress address = packet.sender();			
		    byte[] data = new byte[packet.content().readableBytes()];
		    packet.content().getBytes(0, data);
		    
		    //validate address,parse packet
		    
		    //send response
			//serverBean.sendData(address,buffer);			
		}
		finally
		{
			ReferenceCountUtil.release(packet);			
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) 
	{
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
	{
		
	}
}