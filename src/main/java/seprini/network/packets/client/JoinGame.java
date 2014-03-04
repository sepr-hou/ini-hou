package seprini.network.packets.client;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.netty.buffer.ByteBuf;
import seprini.network.Packet;

public class JoinGame implements Packet {

	@Accessors(chain = true) @Getter @Setter private String playerName;

	@Override
	public void readPacket(ByteBuf input) throws Exception {
		int strLength = input.readInt();
		playerName = new String(input.readBytes(strLength).array());
	}

	@Override
	public void getBytes(ByteBuf out) throws Exception {
		out.writeInt(playerName.getBytes().length);
		out.writeBytes(playerName.getBytes());
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