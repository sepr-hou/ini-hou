package seprini.network;

import seprini.network.packet.Packet;
import seprini.network.packet.codec.decoder.Decoder;
import seprini.network.packet.handler.Handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

class ChannelHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Frame frame = (Frame) msg;

		Decoder<?> decoder = Decoder.get(frame.getPacketId());
		Packet packet = (Packet) decoder.decode(frame.getData());

		try {
			Handler<?> handler = Handler.get(frame.getPacketId());
			if (handler == null) return;
			handler.handleGeneric(packet);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
