/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.Dices;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.action.RollDice;
import bg.backgammon3.model.action.StartTimer;

/**
 * @author philipp
 *
 */
public class StartSecondDiceState extends BoardState {
	private Logger logger = LogManager.getLogger(StartState.class);

	public StartSecondDiceState(Board board) {
		super(board);
		logger.info("StartSecondDiceState erstellt.");
	}

	@Override
	public void handle(GameObject gameObject) {
		if(gameObject instanceof Dices) {
			board.rollSingleDice();
			if(board.getDices().getDice(0).getValue() > board.getDices().getDice(1).getValue()) {
				board.nextPlayer();
			}
			ChooseStartState c = new ChooseStartState(board);
			board.setState(c);
			board.getTimer().resetTimer();
			c.init();
			board.addActionAtEnd(new StartTimer());
		}
	}

}
