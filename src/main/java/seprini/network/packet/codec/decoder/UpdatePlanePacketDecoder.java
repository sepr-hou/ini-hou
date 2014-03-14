package seprini.network.packet.codec.decoder;

import io.netty.buffer.ByteBuf;
import seprini.network.packet.UpdatePlanePacket;

public final class UpdatePlanePacketDecoder extends Decoder<UpdatePlanePacket> {
	public UpdatePlanePacketDecoder() {
		super(0x82);
	}

	@Override
	public UpdatePlanePacket decode(ByteBuf buffer) {
		int planeId = buffer.readInt();
		float x = buffer.readFloat();
		float y = buffer.readFloat();
		float direction = buffer.readFloat();
		int altitude = buffer.readInt();
		int velocity = buffer.readInt();

		return new UpdatePlanePacket(planeId, x, y, direction, altitude, velocity);
	}

}
