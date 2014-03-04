package seprini.network.packets.client;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.netty.buffer.ByteBuf;

public class Packet3SetVelocity implements ClientPacket {

	@Accessors(chain = true) @Getter @Setter private int planeid;
	@Accessors(chain = true) @Getter @Setter private int velocity;

	@Override
	public void readPacket(ByteBuf input) throws Exception {
		planeid = input.readInt();
		velocity = input.readInt();
	}

	@Override
	public void getBytes(ByteBuf out) throws Exception {
		out.writeInt(planeid);
		out.writeInt(velocity);
	}

	@Override
	public byte getId() {
		return 0x03;
	}

	@Override
	public byte getVersion() {
		return 0;
	}
	
}