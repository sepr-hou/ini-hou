package seprini.network.packet.handler;

import seprini.network.packet.JoinGamePacket;
import seprini.network.server.Client;

public final class JoinGamePacketHandler extends Handler<JoinGamePacket, Client> {
	public JoinGamePacketHandler() {
		super(0x00);
	}

	@Override
	public void handle(JoinGamePacket packet, Client attachment) throws Exception {
		// TODO Not trust the client.
		// TODO Assign player an unused ID.
		attachment.setName(packet.getName());
	}

}
