/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.config.Config;

/**
 * 
 *
 */
public class SelectPlace extends Action {

	@Override
	public int getTime() {
		return Config.getInteger("animationTime");
	}


}
