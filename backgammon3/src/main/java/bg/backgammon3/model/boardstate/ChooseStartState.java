/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.action.*;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.*;

/**
 * @author philipp
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
			board.addActionAtEnd(new DisplayMessage("No Possible Moves!"));
			board.nextPlayer();
			RollDiceState c = new RollDiceState(board);
			board.setState(c);
			c.init();
			return;
		}
		board.addActionAtEnd(new DisplayMessage("Choose start!"));
		board.addActionAtEnd(new SelectStartPlace());
	}

	@Override
	public void handle(GameObject gameObject) {
		logger.info("ChooseStartState handle aufgerufen mit: " + gameObject.toString());
		if(gameObject instanceof Place) {
			if(((Place) gameObject).getState() instanceof StartPoint) {
				board.setStartPlace((Place) gameObject);
				ChooseEndState c = new ChooseEndState(board);
				board.setState(c);
				c.init();
			}
		}
	}

}
