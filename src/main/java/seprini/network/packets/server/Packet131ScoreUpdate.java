package seprini.network.packets.server;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.netty.buffer.ByteBuf;

public class Packet131ScoreUpdate implements ServerPacket {

	@Accessors(chain = true) @Getter @Setter private byte playerId;
	@Accessors(chain = true) @Getter @Setter private int score;

	@Override
	public void readPacket(ByteBuf input) throws Exception {
		playerId = input.readByte();
		score = input.readInt();
	}

	@Override
	public void getBytes(ByteBuf out) throws Exception {
		out.writeByte(playerId);
		out.writeInt(score);
	}

	@Override
	public byte getId() {
		return (byte) 0x83;
	}

	@Override
	public byte getVersion() {
		return 0;
	}
	
}