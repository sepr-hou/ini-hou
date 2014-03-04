package seprini.network.packets.server;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.netty.buffer.ByteBuf;

public class Packet128PlayerJoin implements ServerPacket {

	@Accessors(chain = true) @Getter @Setter private byte playerId;
	@Accessors(chain = true) @Getter @Setter private String playerName;

	@Override
	public void readPacket(ByteBuf input) throws Exception {
		playerId = input.readByte();
		int strLength = input.readInt();
		playerName = new String(input.readBytes(strLength).array());
	}

	@Override
	public void getBytes(ByteBuf out) throws Exception {
		out.writeByte(playerId);
		out.writeInt(playerName.getBytes().length);
		out.writeBytes(playerName.getBytes());
	}

	@Override
	public byte getId() {
		return (byte) 0x80;
	}

	@Override
	public byte getVersion() {
		return 0;
	}
	
}