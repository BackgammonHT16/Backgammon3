/**
 * 
 */
package bg.backgammon3.model.gamestate;

import bg.backgammon3.model.*;
import bg.backgammon3.model.action.*;

/**
 * @author philipp
 *
 */
public class MenuState extends GameState {

	public MenuState(Game game)
	{
		super(game);
		game.addActionAtBeginn(new ShowMenu());
	}
	

	public boolean gameCanContinue()
	{
		// TODO implementieren
		return false;
	}


	@Override
	public void startGame() {
		// Spiel Initialisieren
		game.initBoard();
		
		// Zustand Ändern
		game.setState(new GameRunningState(game));
		
		// Spielfeld anzeigen und Menu Verstecken
		game.addActionAtBeginn(new StartGame());
	}


	@Override
	public void continueGame() {
		game.addActionAtBeginn(new ContinueGame());
	}


	@Override
	public void quitGame() {
		game.addActionAtBeginn(new Quit());
	}


	/**
	 * Das Menu wird bereits angezeigt, also soll nichts unternommen werden.
	 */
	@Override
	public void showMenu() {
	}
}
