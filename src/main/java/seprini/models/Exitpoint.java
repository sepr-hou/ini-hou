package seprini.models;

import seprini.data.Art;

import com.badlogic.gdx.math.Vector2;

public final class Exitpoint extends Waypoint {

	public Exitpoint(Vector2 position) {
		super(position, true);
		this.texture = Art.getTextureRegion("exitpoint");
	}
}
