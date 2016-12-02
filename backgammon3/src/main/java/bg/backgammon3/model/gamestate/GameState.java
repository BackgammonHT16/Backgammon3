/**
 * 
 */
package bg.backgammon3.model.gamestate;

import bg.backgammon3.model.*;

/**
 * Abstrakte Oberklasse der GameStates
 *
 */
public abstract class GameState implements ModelElement{
	
	protected Game game;
	protected boolean isActiv;
	
	/**
	 * Der Konstruktor
	 * @param game das Game
	 */
	public GameState(Game game)
	{
		this.game = game;
	}
	

	/**
	 * Legt fest in welchem spiel zustand wir gerade sind
	 */
	public void deactivateState() {
		isActiv = false;
	}
	
	/**
	 * Aktiviert diesen Spielzustand
	 */
	public void activateState() {
		isActiv = true;
	}
	
	/**
	 * Gibt an ob dieser Spielzustand aktiv ist
	 * @return Wahr wenn dieser Spielzustand aktiv ist
	 */
	public boolean isActiv() {
		return isActiv;
	}
	
	/**
	 * Gibt an ob das Spiel aus dem Menu fortgesetzt werden kann. Dies wird von MenuStage gebraucht 
	 * um zu wissen ob der Continue Button angezeigt werden soll.
	 * @return True falls das Spiel fortgesetzt werden kann.
	 */
	public abstract boolean getGameCanContinue();
	
	/**
	 * Startet das Spiel
	 */
	public abstract void startGame();
	
	/**
	 * Führt das spiel fort
	 */
	public abstract void continueGame();
	
	/**
	 * Beendet das Spiel
	 */
	public abstract void quitThisGame();
	
	/**
	 * Der MenuButton im Spiel wurde gepresst.
	 */
	public abstract void showMenu();

	/**
	 * Das Spiel ist beendet
	 * @param playerId der Player
	 */
	public abstract void gameIsFinished(Integer playerId);
}
