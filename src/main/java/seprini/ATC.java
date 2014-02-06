package seprini;

import com.badlogic.gdx.Game;
import seprini.data.Art;
import seprini.data.GameDifficulty;
import seprini.screens.EndScreen;
import seprini.screens.GameScreen;
import seprini.screens.MenuScreen;

/**
 * Main class, calls all subsequent classes. Initialises Input, Art classes,
 * first and last class to be called
 * 
 * @author Crembo
 */
public class ATC extends Game
{
	private MenuScreen menuScreen;

	@Override
	public void create()
	{
		Art.load();
		showMenuScreen();
	}

	/**
	 * Shows the menu screen
	 */
	public void showMenuScreen()
	{
		if (menuScreen == null)
			menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	/**
	 * Shows the game screen
	 */
	public void showGameScreen(GameDifficulty difficulty)
	{
		setScreen(new GameScreen(this, difficulty));
	}

	/**
	 * Shows the end screen
	 *
	 * @param time final time
	 */
	public void showEndScreen(float time)
	{
		setScreen(new EndScreen(this, time));
	}
}
