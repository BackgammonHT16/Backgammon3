/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.ModelElement;
import bg.backgammon3.model.UpdateAI;
import bg.backgammon3.model.action.*;

/**
 * @author philipp
 *
 */
public class WaitingState extends BoardState implements ModelElement {
	private Logger logger = LogManager.getLogger(WaitingState.class);

	public WaitingState(Board board) {
		super(board);
	}

	public void init() {
		board.addActionAtEnd(new ShowPlacesWithoutMarks());
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

	public void moveToNextState() {
		if(board.hasWon()) {
			board.finishGame();
		} else {
			ChooseStartState c = new ChooseStartState(board);
			board.setState(c);
			c.init();
		}
	}

}
