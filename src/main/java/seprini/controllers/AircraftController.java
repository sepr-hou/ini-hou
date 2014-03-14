package seprini.controllers;

import java.util.ArrayList;
import java.util.Random;

import seprini.controllers.components.FlightPlanComponent;
import seprini.controllers.components.WaypointComponent;
import seprini.data.Art;
import seprini.data.Config;
import seprini.data.Debug;
import seprini.data.GameDifficulty;
import seprini.models.Aircraft;
import seprini.models.Airport;
import seprini.models.Airspace;
import seprini.models.Map;
import seprini.models.Waypoint;
import seprini.models.types.AircraftType;
import seprini.screens.ScreenBase;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public final class AircraftController extends InputListener {

	private Random rand = new Random();

	// aircraft and aircraft type lists
	private final ArrayList<AircraftType> aircraftTypeList = new ArrayList<AircraftType>();
	private final ArrayList<Aircraft> aircraftList = new ArrayList<Aircraft>();

	private float lastGenerated, lastWarned;
	private boolean breachingSound, breachingIsPlaying;

	private Aircraft selectedAircraft;

	private final GameDifficulty difficulty;

	// helpers for this class
	private final WaypointComponent waypoints;
	private final FlightPlanComponent flightplan;

	// ui related
	private final Airspace airspace;
	private final Airport airport;
	private final ScreenBase screen;

	private boolean allowRedirection;

	private int aircraftId = 0;

	// game timer
	private float timer = 0;
	
	// game score
	public static float score = 0;
	
	//landing
	private static boolean landing = false;


	/**
	 * 
	 * @param diff
	 *            game difficulty, changes number of aircraft and time between
	 *            them
	 * @param airspace
	 *            the group where all of the waypoints and aircraft will be
	 *            added
	 * @param screen
	 */
	public AircraftController(GameDifficulty diff, Airspace airspace, Airport airport, ScreenBase screen) {
		this.difficulty = diff;
		this.airspace = airspace;
		this.airport = airport;
		this.screen = screen;

		// TODO: jcowgill - this is a massive hack but it will do at the moment
		score = 0;
		landing = false;

		// add the background
		airspace.addActor(new Map());

		// manages the waypoints
		this.waypoints = new WaypointComponent(this);

		// helper for creating the flight plan of an aircraft
		this.flightplan = new FlightPlanComponent(waypoints);

		// initialise aircraft types.
		aircraftTypeList.add(new AircraftType()
				.setMaxClimbRate(600)
				.setMinSpeed(30f)
				.setMaxSpeed(90f)
				.setMaxTurningSpeed(48f)
				.setRadius(15)
				.setSeparationRadius(diff.getSeparationRadius())
				.setTexture(Art.getTextureRegion("aircraft"))
				.setInitialSpeed(30f));
	}

	/**
	 * Updates the aircraft positions. Generates a new aircraft and adds it to
	 * the stage. Collision Detection. Removes aircraft if inactive.
	 */
	public void update(float delta) {
		// Update timer
		timer += delta;
		
		breachingSound = false;

		// wait at least 2 seconds before allowing to warn again
		breachingIsPlaying = (timer - lastWarned < 2);

		// Updates aircraft in turn
		// Removes aircraft which are no longer active from aircraftList.
		// Manages collision detection.
		for (int i = 0; i < aircraftList.size(); i++) {
			Aircraft planeI = aircraftList.get(i);

			// Update aircraft.
			planeI.act(delta);
			planeI.setBreaching(false);

			// Collision Detection + Separation breach detection.
			for (Aircraft planeJ : aircraftList) {

				// Quite simply checks if distance between the centres of both
				// the aircraft <= the radius of aircraft i + radius of aircraft j

				if (!planeI.equals(planeJ)
						// Check difference in altitude.
						&& Math.abs(planeI.getAltitude() - planeJ.getAltitude()) < Config.MIN_ALTITUDE_DIFFERENCE
						// Check difference in horizontal 2d plane.
						&& planeI.getCoords().dst(planeJ.getCoords()) < planeI
								.getRadius() + planeJ.getRadius()) {
					collisionHasOccured(planeI, planeJ);
					return;
				}

				// Checking for breach of separation.
				if (!planeI.equals(planeJ)
						// Check difference in altitude.
						&& Math.abs(planeI.getAltitude() - planeJ.getAltitude()) < planeI
								.getSeparationRadius()
						// Check difference in horizontal 2d plane.
						&& planeI.getCoords().dst(planeJ.getCoords()) < planeI
								.getSeparationRadius()) {

					separationRulesBreached(planeI, planeJ);
				}
			}

			// Remove inactive aircraft.
			if (!planeI.isActive()) {
				removeAircraft(i);
			}

			if (planeI.getAltitude() < 0) {
				screen.getGame().showEndScreen(timer, score);
			}
			
			

		}

		// make sure the breaching sound plays only when a separation breach
		// occurs. Also makes sure it start playing it only one time so there
		// aren't multiple warning sounds at the same time
		if (breachingSound && !breachingIsPlaying) {
			breachingIsPlaying = true;
			lastWarned = timer;
			Art.getSound("warning").play(1.0f);
		}

		// try to generate a new aircraft
		final Aircraft generatedAircraft = generateAircraft();

		// if the newly generated aircraft is not null (ie checking one was
		// generated), add it as an actor to the stage
		if (generatedAircraft != null) {

			// makes the aircraft clickable. Once clicked it is set as the
			// selected aircraft.
			generatedAircraft.addListener(new ClickListener() {

				@Override
				public void clicked(InputEvent event, float x, float y) {
					selectAircraft(generatedAircraft);
				}

			});

			// push the aircraft to the top so it's infront of the user created
			// waypoints
			generatedAircraft.toFront();

			// add it to the airspace (stage group) so its automatically drawn
			// upon calling root.draw()
			airspace.addActor(generatedAircraft);

			// play a sound to audibly inform the player that an aircraft as
			// spawned
			Art.getSound("ding").play(0.5f);
		}

		// sort aircraft so they appear in the right order
		airspace.sortAircraft();
	}

	/**
	 * Handles what happens after a collision
	 * 
	 * @param a
	 *            first aircraft that collided
	 * @param b
	 *            second aircraft that collided
	 */
	private void collisionHasOccured(Aircraft a, Aircraft b) {
		// stop the ambience sound and play the crash sound
		Art.getSound("ambience").stop();
		Art.getSound("crash").play(0.6f);

		// change the screen to the endScreen
		screen.getGame().showEndScreen(timer, score);
	}

	/**
	 * Handles what happens after the separation rules have been breached
	 * 
	 * @param a
	 *            first aircraft that breached
	 * @param b
	 *            second aircraft that breached
	 */
	private void separationRulesBreached(Aircraft a, Aircraft b) {
		// for scoring mechanisms, if applicable
		a.setBreaching(true);
		b.setBreaching(true);

		breachingSound = true;
	}

	/**
	 * Generates aircraft of random type with 'random' flight plan.
	 * <p>
	 * Checks if maximum number of aircraft is not exceeded. If it isn't, a new
	 * aircraft is generated with the arguments randomAircraftType() and
	 * generateFlightPlan().
	 * 
	 * @return an <b>Aircraft</b> if the following conditions have been met: <br>
	 *         a) there are no more aircraft on screen than allowed <br>
	 *         b) enough time has passed since the last aircraft has been
	 *         generated <br>
	 *         otherwise <b>null</b>
	 * 
	 */
	private Aircraft generateAircraft() {
		// number of aircraft has reached maximum, abort
		if (aircraftList.size() >= difficulty.getMaxAircraft())
			return null;

		// time difference between aircraft generated - depends on difficulty
		// selected
		if (timer - lastGenerated < difficulty.getTimeBetweenGenerations() + rand.nextInt(100))
			return null;

		int landChoice = rand.nextInt(6);
		boolean shouldLand = false;
		if (landChoice == 5){
			shouldLand = true;
		}
		
		Aircraft newAircraft = new Aircraft(randomAircraftType(),
				flightplan.generate(), aircraftId++, shouldLand, this.airport);

		aircraftList.add(newAircraft);

		// store the time when an aircraft was last generated to know when to
		// generate the next aicraft
		lastGenerated = timer;

		return newAircraft;
	}

	/**
	 * Selects random aircraft type from aircraftTypeList.
	 * 
	 * @return AircraftType
	 */
	private AircraftType randomAircraftType() {
		return aircraftTypeList.get(rand.nextInt(aircraftTypeList.size()));
	}

	/**
	 * Removes aircraft from aircraftList at index i.
	 * 
	 * @param i
	 */
	private void removeAircraft(int i) {
		Aircraft aircraft = aircraftList.get(i);

		if (aircraft.equals(selectedAircraft))
			selectedAircraft = null;
		
		if (aircraft.isMustLand()){
			score -= 1000;
		}
		// removes the aircraft from the list of aircrafts on screen
		aircraftList.remove(i);

		// removes the aircraft from the stage
		aircraft.remove();
	}

	/**
	 * Selects an aircraft.
	 * 
	 * @param aircraft
	 */
	private void selectAircraft(Aircraft aircraft) {
		// make sure old selected aircraft is no longer selected in its own
		// object
		if (selectedAircraft != null) {
			selectedAircraft.selected(false);

			// make sure the old aircraft stops turning after selecting a new
			// aircraft; prevents it from going in circles
			selectedAircraft.turnLeft(false);
			selectedAircraft.turnRight(false);
		}

		// set new selected aircraft
		selectedAircraft = aircraft;

		// make new aircraft know it's selected
		selectedAircraft.selected(true);
	}

	/**
	 * Redirects aircraft to another waypoint.
	 * 
	 * @param waypoint
	 *            Waypoint to redirect to
	 */
	public void redirectAircraft(Waypoint waypoint) {
		Debug.msg("Redirecting aircraft " + 0 + " to " + waypoint);

		if (getSelectedAircraft() == null || getSelectedAircraft().isLanded() == true)
			return;

		getSelectedAircraft().insertWaypoint(waypoint);
	}
	

	public float getTimer() {
		return timer;
	}
	
	public float getScore() {
		return score;
	}

	public static boolean isLanding() {
		return landing;
	}

	public static void setLanding(boolean landing) {
		AircraftController.landing = landing;
	}

	public Aircraft getSelectedAircraft() {
		return selectedAircraft;
	}

	public ArrayList<Aircraft> getAircraftList() {
		return aircraftList;
	}

	public Airspace getAirspace() {
		return airspace;
	}

	public boolean allowRedirection() { return allowRedirection; }

	public void setAllowRedirection(boolean value) { allowRedirection = value; }

	@Override
	/**
	 * Enables Keyboard Shortcuts as alternatives to the on screen buttons
	 */
	public boolean keyDown(InputEvent event, int keycode) {
		if (selectedAircraft != null && !screen.isPaused()) {

			if (keycode == Keys.LEFT || keycode == Keys.A)
				selectedAircraft.turnLeft(true);

			if (keycode == Keys.RIGHT || keycode == Keys.D)
				selectedAircraft.turnRight(true);

			if (keycode == Keys.UP || keycode == Keys.W)
				selectedAircraft.increaseAltitude();

			if (keycode == Keys.DOWN || keycode == Keys.S)
				selectedAircraft.decreaseAltitude();

			if (keycode == Keys.E)
				selectedAircraft.increaseSpeed();

			if (keycode == Keys.Q)
				selectedAircraft.decreaseSpeed();
			
			if (keycode == Keys.R)
				selectedAircraft.returnToPath();
			
			if (keycode == Keys.F && selectedAircraft.getAltitude() == 5000)
				selectedAircraft.landAircraft();
			
			if (keycode == Keys.T)
				selectedAircraft.takeOff();

		}
		if (keycode == Keys.SPACE)
			screen.setPaused(!screen.isPaused());

		if (keycode == Keys.ESCAPE){
			Art.getSound("ambience").stop();
			screen.getGame().showMenuScreen();
			}

		return false;
	}

	@Override
	/**
	 * Enables Keyboard Shortcuts to disable the turn left and turn right buttons on screen
	 */
	public boolean keyUp(InputEvent event, int keycode) {

		if (selectedAircraft != null) {

			if (keycode == Keys.LEFT || keycode == Keys.A)
				selectedAircraft.turnLeft(false);

			if (keycode == Keys.RIGHT || keycode == Keys.D)
				selectedAircraft.turnRight(false);

		}

		return false;
	}

}
