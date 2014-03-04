package seprini.network;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import seprini.network.packets.client.Packet0JoinGame;
import seprini.network.packets.client.Packet1SetAltitude;
import seprini.network.packets.client.Packet2SetDirection;
import seprini.network.packets.client.Packet3SetVelocity;
import seprini.network.packets.server.Packet128PlayerJoin;
import seprini.network.packets.server.Packet129SpawnPlane;
import seprini.network.packets.server.Packet130PlaneUpdate;
import seprini.network.packets.server.Packet131ScoreUpdate;

public enum Packets {
	JOIN_GAME(		(byte) 0x00, Packet0JoinGame.class),
	SET_ALTITUDE(	(byte) 0x01, Packet1SetAltitude.class),
	SET_DIRECTION(	(byte) 0x02, Packet2SetDirection.class),
	SET_VELOCITY(	(byte) 0x03, Packet3SetVelocity.class),
	
	PLAYER_JOIN(	(byte) 0x80, Packet128PlayerJoin.class),
	SPAWN_PLANE(	(byte) 0x81, Packet129SpawnPlane.class),
	PLANE_UPDATE(	(byte) 0x82, Packet130PlaneUpdate.class),
	SCORE_UPDATE(	(byte) 0x83, Packet131ScoreUpdate.class);
	
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