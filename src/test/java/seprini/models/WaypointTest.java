package seprini.models;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WaypointTest {

	private Waypoint waypoint;

	@Before
	public void setUp() throws Exception {

		Waypoint testwaypoint = new Waypoint(3f, 3f, true);
		waypoint = testwaypoint;

	}

	@Test
	public void testWaypointVector2Boolean() {
		// This is just intialization of the waypoint which works as done in the
		// setup
	}

	@Test
	public void testWaypointFloatFloatBoolean() {
		Vector2 testCoordinates = new Vector2(3f, 3f);
		Vector2 result = waypoint.coords;
		assertEquals(testCoordinates.x, result.x, 0);
		assertEquals(testCoordinates.y, result.y, 0);

	}

	@Test
	public void testCpy() {
		assertTrue(waypoint.cpy().toString().equals(waypoint.toString()));

	}
}