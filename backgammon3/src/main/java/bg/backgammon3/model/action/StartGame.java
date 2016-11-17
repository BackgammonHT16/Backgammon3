/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.config.Config;

/**
 * 
 *
 */
public class StartGame extends Action {

	@Override
	public int getTime() {
		return 0;
	}
	

	public boolean visitImmediately() {
		return true;
	}
	
	@Override
	public int visit(GameControllerElement g) {
		if(Config.getInteger("graphics") == 1){
			g.initGameView();
		}
		return 0;
	}

}
