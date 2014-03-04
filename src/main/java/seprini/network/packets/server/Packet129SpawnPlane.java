package seprini.network.packets.server;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.netty.buffer.ByteBuf;

public class Packet129SpawnPlane implements ServerPacket {

	@Accessors(chain = true) @Getter @Setter private int planeId;
	@Accessors(chain = true) @Getter @Setter private byte playerId;
	
	@Accessors(chain = true) @Getter @Setter private String flightName;

	@Override
	public void readPacket(ByteBuf input) throws Exception {
		planeId = input.readInt();
		playerId = input.readByte();
		
		int strLength = input.readInt();
		flightName = new String(input.readBytes(strLength).array());
	}

	@Override
	public void getBytes(ByteBuf out) throws Exception {
		out.writeInt(planeId);
		out.writeByte(playerId);
		
		out.writeInt(flightName.getBytes().length);
		out.writeBytes(flightName.getBytes());
	}

	@Override
	public byte getId() {
		return (byte) 0x81;
	}

	@Override
	public byte getVersion() {
		return 0;
	}
	
}