package seprini.network.packets.server;

import io.netty.buffer.ByteBuf;
import seprini.network.Packet;

public class JoinGame implements Packet {

	@Override
	public void readPacket(ByteBuf input) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getBytes() throws Exception {
		return null;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public int getVersion() {
		return 0;
	}
	
}