package com.mobiussoftware.samples.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

public class TCPPacketHandler extends SimpleChannelInboundHandler<ByteBuf>
{
	private final static Logger logger = Logger.getLogger(TCPPacketHandler.class);  
	
	public TCPPacketHandler()
	{		
	}
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf message) 
	{
		try
		{
			byte[] data = new byte[message.readableBytes()];
			message.getBytes(0, data);
			
			InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
			//parse packet
					
		}
		catch(Exception ex)
		{
			StringWriter stack = new StringWriter();
			ex.printStackTrace(new PrintWriter(stack));    			
			logger.error("Error Occured While Parsing," + ex.getMessage(),ex);
			logger.error(stack.toString());
		}
		finally
		{
			ReferenceCountUtil.release(message);			
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