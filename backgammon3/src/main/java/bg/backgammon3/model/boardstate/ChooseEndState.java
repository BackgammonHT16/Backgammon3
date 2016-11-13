/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.ModelElement;
import bg.backgammon3.model.action.DisplayMessage;
import bg.backgammon3.model.action.*;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.*;

/**
 * @author philipp
 *
 */
public class ChooseEndState extends BoardState {
	private Logger logger = LogManager.getLogger(ChooseEndState.class);

	public ChooseEndState(Board board) {
		super(board);
	}


	public void init() {
		board.markEndPlaces();
		if(board.isHumanPlayer()) {
			board.addActionAtEnd(new DisplayMessage("Choose Goal!"));
		}
		board.addActionAtEnd(new SelectEndPlace());
	}
	
	@Override
	public void handle(GameObject gameObject) {
		logger.info("ChooseEndState handle aufgerufen mit: " + gameObject.toString());
		if(gameObject instanceof Place) {
			if(((Place) gameObject).getState() instanceof EndPoint) {
				board.moveChecker((Place) gameObject);
				NextState c = new NextState(board);
				board.setState(c);
				board.addActionAtEnd(new UpdateModel());
				c.init();
			}
		} else if(gameObject instanceof Board){
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
		deselectStartPlace();
		return 0;
	}

	public void selectEndPlace(Place place) {
		board.moveChecker(place);
		NextState c = new NextState(board);
		board.setState(c);
		board.addActionAtEnd(new UpdateModel());
		c.init();
	}

	public void deselectStartPlace() {
		ChooseStartState c = new ChooseStartState(board);
		board.setState(c);
		c.init();
	}

}
