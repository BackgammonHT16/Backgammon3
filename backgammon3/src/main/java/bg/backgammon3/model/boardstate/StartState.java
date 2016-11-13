/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.*;
import bg.backgammon3.model.action.*;

/**
 * @author philipp
 *
 */
public class StartState extends BoardState implements ModelElement {
	private Logger logger = LogManager.getLogger(StartState.class);
	
	public StartState(Board board) {
		super(board);
		logger.info("StartState erstellt.");
		if(board.isHumanPlayer()) {
			board.addActionAtEnd(new DisplayMessage("Roll Dice!"));
		} else {
			board.addActionAtEnd(new DisplayMessage("AI Turn"));
		}
		board.addActionAtEnd(new RollDice());
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
	
	public void rollSingleDice() {
		board.rollSingleDice();
		board.nextPlayer();
		board.setState(new StartSecondDiceState(board));
		board.addActionAtEnd(new RollDice());
	}

}
