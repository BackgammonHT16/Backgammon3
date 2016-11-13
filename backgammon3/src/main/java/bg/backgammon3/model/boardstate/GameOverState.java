/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.ModelElement;
import bg.backgammon3.model.action.CloseGame;

/**
 * @author philipp
 *
 */
public class GameOverState  extends BoardState implements ModelElement {

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

	@Override
	public int accept(GameObject gameObject) {
		gameObject.visit(this);
		return 0;
	}

	@Override
	public int nextAccept(GameObject gameObject) {
		return 0;
	}

}
