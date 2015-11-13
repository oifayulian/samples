package com.mobiussoftware.samples.netty.tcp;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TCPPacketDecoder extends ByteToMessageDecoder 
{
    @Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,List<Object> out) throws Exception 
	{
    	//get packets from buffer and store then in out object
    	//buffer may contain several packets , and last one may be partial
	}
}
