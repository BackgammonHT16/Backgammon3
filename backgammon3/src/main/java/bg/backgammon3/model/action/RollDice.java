/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.AI;

/**
 * @author philipp
 *
 */
public class RollDice extends Action{

	@Override
	public int getTime() {
		return Config.getInteger("animationTime");
	}
	

}
