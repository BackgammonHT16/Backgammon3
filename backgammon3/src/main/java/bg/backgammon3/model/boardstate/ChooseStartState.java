/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.Board;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.action.*;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.*;

/**
 * 
 *
 */
public class ChooseStartState extends BoardState {
	private Logger logger = LogManager.getLogger(ChooseStartState.class);

	public ChooseStartState(Board board) {
		super(board);
		logger.info("ChooseStartState erstellt.");
	}
	
	public void init() {
		if(!board.markStartPlaces()) {
			// if(board.isHumanPlayer()) {
			if(!board.getDices().allDicesUsed()){
				board.addActionAtEnd(new DisplayMessage("No Possible Moves!", Config.getInteger("noPossibleMovesTime")));
				board.getDices().setAllUsed();
				board.addActionAtEnd(new DiceWasUsed());
			}
			board.getTimer().resetTimer();
			board.nextPlayer();
			RollDiceState c = new RollDiceState(board);
			board.setState(c);
			c.init();
			return;
		}

		if(board.isHumanPlayer()) {
			board.addActionAtEnd(new DisplayMessage("Choose start!"));
		}
		board.addActionAtEnd(new SelectStartPlace());
	}
	
	public void selectStartPlace(Place place) {
		board.setStartPlace(place);
		ChooseEndState c = new ChooseEndState(board);
		board.setState(c);
		c.init();
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

}
