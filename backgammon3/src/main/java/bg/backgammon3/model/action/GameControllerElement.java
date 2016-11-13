/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.model.ModelElement;

/**
 * @author philipp
 *
 */
public abstract class GameControllerElement extends GameElement implements ModelElement {

	public abstract void initMenuView();
	public abstract void initGameView();
	public abstract void continueGame();
	public abstract void quitGame();
	public abstract void closeGame();
	public abstract boolean getBusy();
}
