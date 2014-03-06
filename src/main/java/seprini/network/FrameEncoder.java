package seprini.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class FrameEncoder extends MessageToByteEncoder<Frame> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Frame frame, ByteBuf buf) throws Exception {
		buf.writeInt(frame.getData().readableBytes());
		buf.writeByte(FrameDecoder.VERSION);
		buf.writeByte(frame.getPacketId());
		buf.writeBytes(frame.getData());
	}

}
