/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.UpdateAI;
import bg.backgammon3.model.action.*;

/**
 * @author philipp
 *
 */
public class NextState extends BoardState {
	private Logger logger = LogManager.getLogger(NextState.class);

	public NextState(Board board) {
		super(board);
	}

	public void init() {
		board.addActionAtEnd(new ShowPlacesWithoutMarks());
	}


	@Override
	public void handle(GameObject gameObject) {
		logger.info("Game Object die NextState weiter ausführt: " + gameObject);
			if(gameObject instanceof UpdateAI) {
			if(board.hasWon()) {
				board.finishGame();
			} else {
				ChooseStartState c = new ChooseStartState(board);
				board.setState(c);
				c.init();
			}
		}
	}

}
