package seprini.models;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExitpointTest {

	private Exitpoint exitpoint;

	@Before
	public void setUp() throws Exception {

		Exitpoint testexitpoint = new Exitpoint(new Vector2(3f, 3f));
		exitpoint = testexitpoint;

	}

	@Test
	public void testExitpoint() {
		Vector2 testcoordinates = new Vector2(3f, 3f);
		Vector2 result = exitpoint.coords;
		assertEquals(testcoordinates.x, result.x, 0);
		assertEquals(testcoordinates.y, result.y, 0);
	}

}
