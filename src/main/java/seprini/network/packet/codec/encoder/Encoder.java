package seprini.network.packet.codec.encoder;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import seprini.network.packet.Packet;

public abstract class Encoder<T extends Packet> {
	private final static Map<Integer, Encoder<?>> encoders = new HashMap<>();

	public Encoder(int packetId) {
		encoders.put(packetId, this);
	}

	public abstract ByteBuf encode(ByteBufAllocator alloc, T packet);

	public static Encoder<?> get(int packetId) {
		return encoders.get(packetId);
	}

	static {
		new JoinGamePacketEncoder();
		new SetAltitudePacketEncoder();
		new SetDirectionPacketEncoder();
		new SetVelocityPacketEncoder();
		new PlayerJoinPacketEncoder();
		new SpawnPlanePacketEncoder();
		new UpdatePlanePacketEncoder();
		new UpdateScorePacketEncoder();
	}

	@SuppressWarnings("unchecked")
	public ByteBuf encodeGeneric(ByteBufAllocator alloc, Packet packet) {
		return encode(alloc, (T) packet);
	}
}
