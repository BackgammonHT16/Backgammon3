/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.ModelVisitor;
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
	public void handle(ModelVisitor gameObject) {
		// Nichts tun
	}

	@Override
	public int accept(ModelVisitor gameObject) {
		gameObject.visit(this);
		return 0;
	}

	@Override
	public int nextAccept(ModelVisitor gameObject) {
		return 0;
	}

}
