package seprini.models;

import seprini.data.Art;

import com.badlogic.gdx.math.Vector2;

public class Entrypoint extends Waypoint {

	public Entrypoint(Vector2 position) {
		super(position, true);
		this.texture = Art.getTextureRegion("entrypoint");
	}

}
