package seprini.network;

import io.netty.buffer.ByteBuf;

public interface Packet {
	
	public void readPacket(ByteBuf input) throws Exception;
	
	public void getBytes(ByteBuf out) throws Exception;
	
	public byte getId();
	
	public byte getVersion();
	
}