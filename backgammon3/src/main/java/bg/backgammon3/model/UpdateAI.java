/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.boardstate.WaitingState;

/**
 * @author philipp
 *
 */
public class UpdateAI extends GameObject {

	public int visit(AI ai) {
		ai.board.accept(this);
		return 0;
	}
	

	public int visit(AI2 ai) {
		ai.board.accept(this);
		return 0;
	}
	
	public int visit(WaitingState s) {
		s.moveToNextState();
		return 0;
	}
}
