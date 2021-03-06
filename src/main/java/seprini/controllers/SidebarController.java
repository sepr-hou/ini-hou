package seprini.controllers;

import java.util.HashMap;

import seprini.data.Art;
import seprini.data.Config;
import seprini.models.Aircraft;
import seprini.screens.ScreenBase;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.esotericsoftware.tablelayout.Cell;

/**
 * Controls the sidebar in the GameScreen 
 */
public final class SidebarController extends ChangeListener {

	private final AircraftController aircrafts;

	private Aircraft selectedAircraft;

	private final HashMap<String, TextButton> buttons = new HashMap<String, TextButton>();
	private final HashMap<String, Label> labels = new HashMap<String, Label>();

	private final ScreenBase screen;

	// UI wrappers for the controls and the buttons at the bottom
	private Table sidebar, aircraftControls, bottomButtons;

	// stores state of the turn left/right buttons
	private boolean turningLeft, turningRight;

	/**
	 * 
	 * 
	 * @param sidebar
	 *            the sidebar layout, so the controller can add all of the
	 *            buttons
	 * @param aircrafts
	 *            so this can get the selected aircraft
	 * @param screen
	 *            for changing screens once Menu or Pause have been clicked
	 */
	public SidebarController(Table sidebar, AircraftController aircrafts, ScreenBase screen) {
		this.sidebar = sidebar;
		this.aircrafts = aircrafts;
		this.screen = screen;
		this.init();
	}

	/**
	 * Initialise all the buttons and labels
	 */
	private void init() {

		// wrapper for aicraft controls
		aircraftControls = new Table();
		aircraftControls.setFillParent(true);

		if (Config.DEBUG_UI)
			aircraftControls.debug();

		aircraftControls.top();
		sidebar.addActor(aircraftControls);

		// wrapper for bottom buttons
		bottomButtons = new Table();

		bottomButtons.setFillParent(true);

		if (Config.DEBUG_UI)
			aircraftControls.debug();

		bottomButtons.bottom();
		sidebar.addActor(bottomButtons);

		// adding labels to aircraft controls
		createLabel("speed", " Speed: ", aircraftControls).width(200)
				.colspan(2);

		aircraftControls.row();

		createLabel("altitude", " Altitude: ", aircraftControls).width(200)
				.colspan(2);

		aircraftControls.row();

		// adding buttons to aircraft controls

		createButton("assignWaypoint", " Assign Waypoint", aircraftControls,
				true).width(200).colspan(2);

		aircraftControls.row();

		createButton("returnToPath", " Return to Path (R)", aircraftControls, false)
				.width(200).colspan(2);

		aircraftControls.row();

		createButton("accelerate", " Accelerate (E)", aircraftControls, false)
				.width(200).colspan(2);
		
		aircraftControls.row().colspan(2);

		createButton("decelerate", " Decelerate (Q)", aircraftControls, false)
				.width(200);
		
		aircraftControls.row().colspan(2);
		
		createButton("takeOff", "Take Off (T)", aircraftControls, false).width(200);

		aircraftControls.row().colspan(2);
		
		createButton("land", "Land (G)", aircraftControls, false).width(200);

		aircraftControls.row().spaceTop(100);

		createButton("up", " Up (W)", aircraftControls, false).width(100)
				.colspan(2);

		aircraftControls.row();

		createButton("left", " Left (A)", aircraftControls, true).width(100);
		createButton("right", "Right (D)", aircraftControls, true).width(100);

		aircraftControls.row();

		createButton("down", "Down (S)", aircraftControls, false).width(100)
				.colspan(2);

		aircraftControls.row();

		createLabel("", " Time:", bottomButtons).width(100);
		createLabel("timer", "..", bottomButtons).width(100);
		
		bottomButtons.row();
		
		createLabel("", " Score:", bottomButtons).width(100);
		createLabel("score", "..", bottomButtons).width(100);
		
		bottomButtons.row();

		// adding buttons to bottom
		createButton("menu", " Menu", bottomButtons, false).width(100);
		createButton("pause", " Pause", bottomButtons, false).width(100);
	}

	/**
	 * Update the sidebar according to changes in the AircraftController
	 */
	public void update() {
		String altitudeText;
		String speedText;

		// update timer
		labels.get("timer").setText("" + Math.round(aircrafts.getTimer()));
		
		// update score
		labels.get("score").setText("" + Math.round(aircrafts.getScore()));

		// if there is no selected aircraft, return immediately to avoid errors
		// otherwise set it to the local selectedAircraft variable and update
		// the text
		if ((selectedAircraft = aircrafts.getSelectedAircraft()) == null) {
			altitudeText = " Altitude: ";
			speedText = " Speed: ";
		} else {
			altitudeText = " Altitude: " + selectedAircraft.getAltitude() + "m";
			speedText = " Speed: " + Math.round(selectedAircraft.getSpeed()
							* Config.AIRCRAFT_SPEED_MULTIPLIER) + "km/h";
		}

		// force left + right buttons to be checked correctly
		buttons.get("left").setChecked (selectedAircraft != null && selectedAircraft.isTurningLeft());
		buttons.get("right").setChecked(selectedAircraft != null && selectedAircraft.isTurningRight());

		// update aircraft altitude text
		labels.get("altitude").setText(altitudeText);

		// update aircraft speed text
		labels.get("speed").setText(speedText);
	}

	/**
	 * Convinience method to create buttons and add them to the sidebar
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	private Cell<?> createButton(String name, String text, Table parent,
			boolean toggle) {
		TextButton button = new TextButton(text, Art.getSkin(), (toggle)
				? "toggle"
				: "default");
		button.pad(3);
		button.addListener(this);

		buttons.put(name, button);

		return parent.add(button);
	}

	/**
	 * Convinience method to create labels and add them to the sidebar
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	@SuppressWarnings("unused")
	private Cell<?> createLabel(String name, String text) {
		Label label = new Label(text, Art.getSkin());
		labels.put(name, label);

		return sidebar.add(label);
	}

	/**
	 * Convinience method to create labels and add them to the sidebar
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	private Cell<?> createLabel(String name, String text, Table parent) {
		Label label = new Label(text, Art.getSkin());

		labels.put(name, label);

		return parent.add(label);
	}

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		if (!screen.isPaused()) {

			if (actor.equals(buttons.get("assignWaypoint")))
				aircrafts.setAllowRedirection(!aircrafts.allowRedirection());

			if (selectedAircraft != null) {
				if (actor.equals(buttons.get("returnToPath")))
					selectedAircraft.returnToPath();

				if (actor.equals(buttons.get("left")))
					selectedAircraft.turnLeft(turningLeft = (!turningLeft));

				if (actor.equals(buttons.get("right")))
					selectedAircraft.turnRight(turningRight = (!turningRight));

				if (actor.equals(buttons.get("land")))
					selectedAircraft.landAircraft();
				
				if (actor.equals(buttons.get("takeOff")))
					selectedAircraft.takeOff();
				
				if (actor.equals(buttons.get("up")))
					selectedAircraft.increaseAltitude();

				if (actor.equals(buttons.get("down")))
					selectedAircraft.decreaseAltitude();

				if (actor.equals(buttons.get("accelerate")))
					selectedAircraft.increaseSpeed();

				if (actor.equals(buttons.get("decelerate")))
					selectedAircraft.decreaseSpeed();

			}
		}

		if (actor.equals(buttons.get("menu"))) {
			Art.getSound("ambience").stop();
			screen.getGame().showMenuScreen();
		}

		if (actor.equals(buttons.get("pause"))) {
			screen.setPaused(!screen.isPaused());
		}

	}
}
