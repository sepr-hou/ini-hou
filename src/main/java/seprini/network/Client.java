package seprini.network;

import seprini.network.packet.Packet;
import seprini.network.packet.codec.encoder.Encoder;
import lombok.Setter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client extends Thread {
	
	private ChannelFuture f;
	private String host;
	private int port;
	@Setter private Runnable onConnect;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void run() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new FrameDecoder(), new FrameEncoder(), new ChannelHandler());
				}
			});

			try {
				// Start the client.
				f = b.connect(host, port).sync();
				
				if (onConnect != null) {
					onConnect.run();
				}
				
				// Wait until the connection is closed.
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
	
	public void sendPacket(Packet packet) throws Exception {
		Encoder<?> encoder = Encoder.get(packet.getId());
		ByteBuf buf = encoder.encodeGeneric(f.channel().alloc(), packet);
		Frame frame = new Frame(packet.getId(), buf);

		f.channel().writeAndFlush(frame);
	}
}
