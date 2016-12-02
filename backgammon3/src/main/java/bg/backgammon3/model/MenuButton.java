/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.gamestate.*;

/**
 * Modelliert den MenuButton
 *
 */
public class MenuButton extends ModelVisitor{
	/**
	 * Besucher
	 */
	public int visit(GameRunningState gameRunningState) {
		gameRunningState.showMenu();
		return 0;
	}
}
