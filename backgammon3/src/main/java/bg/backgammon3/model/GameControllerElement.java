/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.ModelElement;

/**
 * 
 *
 */
public abstract class GameControllerElement implements ModelElement {

	//public abstract void initMenuView();
	//public abstract void initGameView();
	//public abstract void continueGame();
	//public abstract void quitGame();
	//public abstract void closeGame();
	/**
	 * Ob der Controller gerade Animationen ausführt
	 * @return Wahr fall gerade noch eine animation ausgeführt wird.
	 */
	public abstract boolean getBusy();
}
