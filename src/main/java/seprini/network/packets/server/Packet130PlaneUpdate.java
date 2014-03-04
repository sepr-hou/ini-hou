package seprini.network.packets.server;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.netty.buffer.ByteBuf;

public class Packet130PlaneUpdate implements ServerPacket {

	@Accessors(chain = true) @Getter @Setter private float x;
	@Accessors(chain = true) @Getter @Setter private float y;
	@Accessors(chain = true) @Getter @Setter private float direction;
	
	@Accessors(chain = true) @Getter @Setter private int altitude;
	@Accessors(chain = true) @Getter @Setter private int velocity;

	@Override
	public void readPacket(ByteBuf input) throws Exception {
		x = input.readFloat();
		y = input.readFloat();
		direction = input.readFloat();
		
		altitude = input.readInt();
		velocity = input.readInt();
	}

	@Override
	public void getBytes(ByteBuf out) throws Exception {
		out.writeFloat(x);
		out.writeFloat(y);
		out.writeFloat(direction);
		
		out.writeInt(altitude);
		out.writeInt(velocity);
	}

	@Override
	public byte getId() {
		return (byte) 0x82;
	}

	@Override
	public byte getVersion() {
		return 0;
	}
	
}