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
public class StartState extends BoardState {
	private Logger logger = LogManager.getLogger(StartState.class);
	
	public StartState(Board board) {
		super(board);
		logger.info("StartState erstellt.");
		board.addActionAtEnd(new DisplayMessage("Roll Dice!"));
		board.addActionAtEnd(new RollDice());
	}

	@Override
	public void handle(GameObject gameObject) {
		if(gameObject instanceof Dices) {
			board.rollSingleDice();
			board.nextPlayer();
			board.setState(new StartSecondDiceState(board));
			board.addActionAtEnd(new RollDice());
		}
	}

}
