/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.boardstate.WaitingState;
import bg.backgammon3.model.player.AI;
import bg.backgammon3.model.player.AI2;

/**
 * 
 *
 */
public class UpdateAI extends ModelVisitor {

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
