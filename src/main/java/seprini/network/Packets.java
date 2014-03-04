package seprini.network;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import seprini.network.packets.client.JoinGame;

public enum Packets {
	JOIN_GAME((byte) 0, JoinGame.class);
	
	private byte packetId;
	private Class<? extends Packet> clazz;
	
	private Packets(byte packetId, Class<? extends Packet> clazz) {
		this.packetId = packetId;
		this.clazz = clazz;
	}
	
	public byte getPacketId() {
		return packetId;
	}
	
	public Packet createInstance() {
		try {
			final Constructor<? extends Packet> c = clazz.getDeclaredConstructor(new Class[] {});
			return c.newInstance();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static HashMap<Byte, Packets> map = new HashMap<Byte, Packets>();

	public static Packets getPacket(int packetId) {
		return map.get((byte) packetId);
	}

	static {
		for (final Packets pks : Packets.values()) {
			map.put(pks.getPacketId(), pks);
		}
	}
}