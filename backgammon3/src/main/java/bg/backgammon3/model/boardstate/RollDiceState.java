/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.Dices;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.ModelElement;
import bg.backgammon3.model.action.*;

/**
 * @author philipp
 *
 */
public class RollDiceState extends BoardState implements ModelElement {

	public RollDiceState(Board board) {
		super(board);
	}

	public void init() {
		board.addActionAtEnd(new RollDice());

		if(board.isHumanPlayer()) {
			board.addActionAtEnd(new DisplayMessage("Roll Dice!"));
		} else {
			board.addActionAtEnd(new DisplayMessage("AI Turn"));
		}
		board.getTimer().resetTimer();
		board.addActionAtEnd(new StartTimer());
	}
	
	@Override
	public void handle(GameObject gameObject) {
		if(gameObject instanceof Dices) {
			board.rollDice();
			ChooseStartState c = new ChooseStartState(board);
			board.setState(c);
			c.init();
		}
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

	public void rollDices() {
		board.rollDice();
		ChooseStartState c = new ChooseStartState(board);
		board.setState(c);
		c.init();
	}


}
