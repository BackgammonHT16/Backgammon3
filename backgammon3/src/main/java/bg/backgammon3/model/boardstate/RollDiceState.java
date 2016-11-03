/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.Dices;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.action.*;

/**
 * @author philipp
 *
 */
public class RollDiceState extends BoardState {

	public RollDiceState(Board board) {
		super(board);
	}

	public void init() {
		board.addActionAtEnd(new RollDice());
		board.addActionAtEnd(new DisplayMessage("Roll Dice!"));
	}
	
	@Override
	public void handle(GameObject gameObject) {
		if(gameObject instanceof Dices) {
			board.rollDice();
			ChooseStartState c = new ChooseStartState(board);
			board.setState(c);
			c.init();
			board.getTimer().resetTimer();
			board.addActionAtEnd(new StartTimer());
		}
	}


}
