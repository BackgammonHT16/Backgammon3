/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.GameObject;

/**
 * @author philipp
 *
 */
public class NextState extends BoardState {

	public NextState(Board board) {
		super(board);
	}


	@Override
	public void handle(GameObject gameObject) {
		if(board.hasWon()) {
			board.finishGame();
		} else {
			ChooseStartState c = new ChooseStartState(board);
			board.setState(c);
			c.init();
		}
	}

}
