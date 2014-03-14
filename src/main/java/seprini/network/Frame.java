package seprini.network;

import io.netty.buffer.ByteBuf;

public final class Frame {
	private final int packetId;
	private final ByteBuf data;

	public Frame(int packetId, ByteBuf data) {
		this.packetId = packetId;
		this.data = data;
	}

	public int getPacketId() {
		return packetId;
	}

	public ByteBuf getData() {
		return data;
	}
}
