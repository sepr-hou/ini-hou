package seprsaw.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import seprini.network.Client;
import seprini.network.Server;
import seprini.network.packets.client.Packet0JoinGame;

/**
 * Test class for networking
 */
@RunWith(JUnit4.class)
public class NetworkTest {
	
	Client cli;
	
	@Test
	public void commsTest() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new Server(11111).run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Connecting with client");
		cli = new Client("127.0.0.1", 11111);
		cli.setOnConnect(new Runnable() {
			@Override
			public void run() {
				try {
					cli.sendPacket(new Packet0JoinGame().setPlayerName("Test"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		cli.start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
}
