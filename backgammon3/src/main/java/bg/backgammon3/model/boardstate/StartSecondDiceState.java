/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.Dices;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.ModelElement;

/**
 * Wähle den zweiten Startzustand
 *
 */
public class StartSecondDiceState extends BoardState implements ModelElement {
	private Logger logger = LogManager.getLogger(StartState.class);

	/**
	 * Kontruktor
	 * @param board das board
	 */
	public StartSecondDiceState(Board board) {
		super(board);
		logger.info("StartSecondDiceState erstellt.");
	}

	@Override
	public int accept(ModelVisitor gameObject) {
		return gameObject.visit(this);
	}

	@Override
	public int nextAccept(ModelVisitor gameObject) {
		return 0;
	}
	
	/**
	 * Rolle einen einzelnen Würfel
	 */
	public void rollSingleDice() {
		board.rollSingleDice();
		if(board.getDices().getDice(0).getValue() > board.getDices().getDice(1).getValue()) {
			board.nextPlayer();
		}
		ChooseStartState c = new ChooseStartState(board);
		board.setState(c);
		c.init();
		board.getTimer().resetTimer();
//		board.addActionAtEnd(new StartTimer());
		
		board.getTimer().acitvateTimer();
		
		if(!board.isHumanPlayer()) {
//			board.addActionAtEnd(new DisplayMessage("AI Turn"));
			board.setMessage("AI Turn");
		}
	}

}
