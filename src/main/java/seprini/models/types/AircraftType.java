package seprini.models.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AircraftType {

	private String aircraftName;
	private float initialSpeed;
	private int radius;
	private int separationRadius;
	private TextureRegion texture;
	private float maxTurningSpeed;
	private float maxClimbRate;
	private float maxSpeed;
	private float minSpeed;
	private boolean isActive;

	public float getInitialSpeed() {
		return initialSpeed;
	}

	public AircraftType setInitialSpeed(float initialSpeed) {
		this.initialSpeed = initialSpeed;
		return this;
	}

	public int getRadius() {
		return radius;
	}

	public AircraftType setRadius(int radius) {
		this.radius = radius;
		return this;
	}

	public int getSeparationRadius() {
		return separationRadius;
	}

	public AircraftType setSeparationRadius(int separationRadius) {
		this.separationRadius = separationRadius;
		return this;
	}

	public TextureRegion getTexture() {
		return texture;
	}

	public AircraftType setTexture(TextureRegion texture) {
		this.texture = texture;
		return this;
	}

	public float getMaxTurningSpeed() {
		return maxTurningSpeed;
	}

	public AircraftType setMaxTurningSpeed(float maxTurningSpeed) {
		this.maxTurningSpeed = maxTurningSpeed;
		return this;
	}

	public float getMaxClimbRate() {
		return maxClimbRate;
	}

	public AircraftType setMaxClimbRate(float maxClimbRate) {
		this.maxClimbRate = maxClimbRate;
		return this;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public AircraftType setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
		return this;
	}

	public float getMinSpeed() {
		return minSpeed;
	}

	public AircraftType setMinSpeed(float minSpeed) {
		this.minSpeed = minSpeed;
		return this;
	}

	public boolean isActive() {
		return isActive;
	}

	public AircraftType setActive(boolean isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getAircraftName() {
		return aircraftName;
	}

	public AircraftType setAircraftName(String aircraftName) {
		this.aircraftName = aircraftName;
		return this;
	}
}
