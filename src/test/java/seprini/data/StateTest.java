package seprini.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateTest {

	@Test
	public void testTick() {
	}

	@Test
	public void testTime() {
		assertEquals(State.time(), 0f, 0);
	}

	@Test
	public void testReset() {
		State.reset();
		assertEquals(State.time(), 0f, 0);

	}
}
