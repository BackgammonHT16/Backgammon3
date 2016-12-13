/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.Dices;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.ModelElement;

/**
 * 
 *
 */
public class RollDiceState extends BoardState implements ModelElement {

	/**
	 * Der Konstruktor
	 * @param board das Board
	 */
	public RollDiceState(Board board) {
		super(board);
	}

	/**
	 * Initialisiert den zustand
	 */
	public void init() {
//		board.addActionAtEnd(new RollDice());

		if(board.isHumanPlayer()) {
//			board.addActionAtEnd(new DisplayMessage("Roll Dice!"));
			board.setMessage("Roll Dice!");
		} else {
//			board.addActionAtEnd(new DisplayMessage("AI Turn"));
			board.setMessage("AI Turn");
		}
		board.getTimer().resetTimer();
//		board.addActionAtEnd(new StartTimer());
		
		board.getTimer().acitvateTimer();
	}

	@Override
	public int accept(ModelVisitor gameObject) {
		return gameObject.visit(this);
		//return 1;
	}

	@Override
	public int nextAccept(ModelVisitor gameObject) {
		return 0;
	}

	/**
	 * Rollt die Würfel
	 */
	public int rollDices() {
		board.rollDice();
		ChooseStartState c = new ChooseStartState(board);
		board.setState(c);
		return c.init();
	}


}
