/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.gamestate.*;

/**
 * @author philipp
 *
 */
public class MenuButton extends GameObject{
	
	public int visit(GameRunningState gameRunningState) {
		gameRunningState.showMenu();
		return 0;
	}
}
