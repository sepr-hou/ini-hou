package seprini.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf arg1, List<Object> arg2) throws Exception {
		arg1.markReaderIndex();
		int length = arg1.readInt();
		if (arg1.readableBytes() >= length + 2) {
			int version = arg1.readByte();
			int packetId = arg1.readByte();
			ByteBuf input = arg1.readBytes(length);
			
			Packet packet = Packets.getPacket(packetId).createInstance();
			if (packet.getVersion() == version) {
				packet.readPacket(input);
				arg2.add(packet);
			}
		} else {
			arg1.resetReaderIndex();
		}
	}
}
