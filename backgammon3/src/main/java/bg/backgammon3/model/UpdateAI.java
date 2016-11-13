/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.boardstate.WaitingState;
import bg.backgammon3.model.player.AI;
import bg.backgammon3.model.player.AI2;

/**
 * @author philipp
 *
 */
public class UpdateAI extends GameObject {

	public int visit(AI ai) {
		ai.getBoard().accept(this);
		return 0;
	}
	

	public int visit(AI2 ai) {
		ai.getBoard().accept(this);
		return 0;
	}
	
	public int visit(WaitingState s) {
		s.moveToNextState();
		return 0;
	}
}
