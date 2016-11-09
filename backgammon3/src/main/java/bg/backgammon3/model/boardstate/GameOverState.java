/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.action.CloseGame;
import bg.backgammon3.model.action.ShowPlacesWithoutMarks;

/**
 * @author philipp
 *
 */
public class GameOverState  extends BoardState {

	public GameOverState(Board board) {
		super(board);
	}

	public void init() {
		board.addActionAtEnd(new CloseGame());
	}


	@Override
	public void handle(GameObject gameObject) {
		// Nichts tun
	}

}
