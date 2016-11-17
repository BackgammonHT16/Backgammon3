/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.config.Config;

/**
 * 
 *
 */
public class Quit extends Action {

	@Override
	public int getTime() {
		return 0;
	}
	


	public boolean visitImmediately() {
		return true;
	}
	public int visit(GameControllerElement g) {
		if(Config.getInteger("graphics") == 1){
			g.quitGame();
		}
		return 0;
	}
	
}
