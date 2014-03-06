package seprini.network.packet.handler;

import java.util.HashMap;
import java.util.Map;

import seprini.network.packet.Packet;

public abstract class Handler<T extends Packet> {
	private final static Map<Integer, Handler<?>> handlers = new HashMap<>();

	public Handler(int packetId) {
		handlers.put(packetId, this);
	}

	public abstract void handle(T packet) throws Exception;

	@SuppressWarnings("unchecked")
	public void handleGeneric(Packet packet) throws Exception {
		handle((T) packet);
	}

	public static Handler<?> get(int packetId) {
		return handlers.get(packetId);
	}
}
