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

/**
 * 
 *
 */
public class WaitingState extends BoardState implements ModelElement {
	private Logger logger = LogManager.getLogger(WaitingState.class);

	/**
	 * Konstruktor
	 * @param board Das Board
	 */
	public WaitingState(Board board) {
		super(board);
	}

	/**
	 * Initialisiert den zustand
	 * @return der Gamestate
	 */
	public int init() {
//		board.addActionAtEnd(new ShowPlacesWithoutMarks());
		
		// Change
		moveToNextState();
		return 3;
	}


	@Override
	public int accept(ModelVisitor gameObject) {
		gameObject.visit(this);
		return 3;
	}

	@Override
	public int nextAccept(ModelVisitor gameObject) {
		return 0;
	}

	/**
	 * Geht über zum nächsten zustand
	 */
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
