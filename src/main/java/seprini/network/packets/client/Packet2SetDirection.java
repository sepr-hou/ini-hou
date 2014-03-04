package seprini.network.packets.client;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.netty.buffer.ByteBuf;

public class Packet2SetDirection implements ClientPacket {

	@Accessors(chain = true) @Getter @Setter private int planeid;
	@Accessors(chain = true) @Getter @Setter private float direction;

	@Override
	public void readPacket(ByteBuf input) throws Exception {
		planeid = input.readInt();
		direction = input.readFloat();
	}

	@Override
	public void getBytes(ByteBuf out) throws Exception {
		out.writeInt(planeid);
		out.writeFloat(direction);
	}

	@Override
	public byte getId() {
		return 0x02;
	}

	@Override
	public byte getVersion() {
		return 0;
	}
	
}