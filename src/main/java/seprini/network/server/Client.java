package seprini.network.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import seprini.network.Frame;
import seprini.network.packet.Packet;
import seprini.network.packet.codec.encoder.Encoder;
import lombok.Getter;
import lombok.Setter;

public final class Client {
	@Getter @Setter private int id;
	@Getter @Setter private String name;
	@Getter private final Server server;
	@Getter private final Channel channel;

	public Client(Server server, Channel channel) {
		this.server = server;
		this.channel = channel;
	}

	public void writePacket(Packet packet) {
		synchronized (this) {
			Encoder<?> encoder = Encoder.get(packet.getId());
			ByteBuf buf = encoder.encodeGeneric(channel.alloc(), packet);
			Frame frame = new Frame(packet.getId(), buf);
	
			channel.writeAndFlush(frame);
		}
	}
}
