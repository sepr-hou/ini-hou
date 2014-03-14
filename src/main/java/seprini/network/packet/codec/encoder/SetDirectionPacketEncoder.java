package seprini.network.packet.codec.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import seprini.network.packet.SetDirectionPacket;

public final class SetDirectionPacketEncoder extends Encoder<SetDirectionPacket> {
	public SetDirectionPacketEncoder() {
		super(0x02);
	}

	@Override
	public ByteBuf encode(ByteBufAllocator alloc, SetDirectionPacket packet) {
		ByteBuf buf = alloc.buffer();

		buf.writeInt(packet.getPlaneId());
		buf.writeFloat(packet.getDirection());

		return buf;
	}
}
