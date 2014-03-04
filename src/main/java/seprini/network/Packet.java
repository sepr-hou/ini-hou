package seprini.network;

import io.netty.buffer.ByteBuf;

public interface Packet {
	
	public void readPacket(ByteBuf input) throws Exception;
	
	public byte[] getBytes() throws Exception;
	
	public int getId();
	
	public int getVersion();
	
}