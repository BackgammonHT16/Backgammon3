/**
 * 
 */
package bg.backgammon3.model.gamestate;

import bg.backgammon3.model.*;

/**
 * @author philipp
 *
 */
public abstract class GameState implements ModelElement{
	
	protected Game game;
	
	public GameState(Game game)
	{
		this.game = game;
	}
	
	/**
	 * Gibt an ob das Spiel aus dem Menu fortgesetzt werden kann. Dies wird von MenuStage gebraucht 
	 * um zu wissen ob der Continue Button angezeigt werden soll.
	 * @return True falls das Spiel fortgesetzt werden kann.
	 */
	public abstract boolean getGameCanContinue();
	
	public abstract void startGame();
	
	public abstract void continueGame();
	
	public abstract void quitThisGame();
	
	/**
	 * Der MenuButton im Spiel wurde gepresst.
	 */
	public abstract void showMenu();

	public abstract void handle(ModelVisitor gameObject, boolean busy);

	public abstract void gameIsFinished(Integer playerId);
}
