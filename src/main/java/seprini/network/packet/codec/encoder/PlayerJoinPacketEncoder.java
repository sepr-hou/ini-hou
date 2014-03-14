package seprini.network.packet.codec.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import seprini.network.ByteBufUtils;
import seprini.network.packet.PlayerJoinPacket;

public final class PlayerJoinPacketEncoder extends Encoder<PlayerJoinPacket> {
	public PlayerJoinPacketEncoder() {
		super(0x80);
	}

	@Override
	public ByteBuf encode(ByteBufAllocator alloc, PlayerJoinPacket packet) {
		ByteBuf buf = alloc.buffer();

		buf.writeByte(packet.getPlayerId());
		ByteBufUtils.writeString(buf, packet.getName());

		return buf;
	}

}
