package seprini.network.packets.client;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.netty.buffer.ByteBuf;

public class Packet0JoinGame implements ClientPacket {

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
	public byte getId() {
		return 0x00;
	}

	@Override
	public byte getVersion() {
		return 0;
	}
	
}