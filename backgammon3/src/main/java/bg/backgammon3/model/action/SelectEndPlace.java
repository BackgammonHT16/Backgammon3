/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.config.Config;
import bg.backgammon3.view.BoardView;

/**
 * @author philipp
 *
 */
public class SelectEndPlace extends Action{

	@Override
	public int getTime() {
		return Config.getInteger("animationTime");
	}


	public int visit(BoardElement view) {
		view.update();
		return 0;
	}
	
}
