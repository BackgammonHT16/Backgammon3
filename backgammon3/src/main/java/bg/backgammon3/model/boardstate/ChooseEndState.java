/**
 * 
 */
package bg.backgammon3.model.boardstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.place.Place;

/**
 * Der EndPlace muss gesucht werden
 *
 */
public class ChooseEndState extends BoardState {
	private Logger logger = LogManager.getLogger(ChooseEndState.class);

	/**
	 * Konstruktor
	 * @param board das Board
	 */
	public ChooseEndState(Board board) {
		super(board);
	}


	/**
	 * Initialisierung
	 */
	public void init() {
		board.markEndPlaces();
		if(board.isHumanPlayer()) {
//			board.addActionAtEnd(new DisplayMessage("Choose Goal!"));
		}
//		board.addActionAtEnd(new SelectEndPlace());
	}


	@Override
	public int accept(ModelVisitor gameObject) {
		return gameObject.visit(this);
	}


	@Override
	public int nextAccept(ModelVisitor gameObject) {
		return deselectStartPlace();
	}

	/**
	 * Endplace auswählen
	 * @param place der endplace
	 * @return Gamestate
	 */
	public int selectEndPlace(Place place) {
		board.moveChecker(place);
		WaitingState c = new WaitingState(board);
		board.setState(c);
//		board.addActionAtEnd(new UpdateModel());
		c.init();
		return 3;
	}

	/**
	 * Wählt den Startplace ab
	 * @return der Gamestate
	 */
	public int deselectStartPlace() {
		ChooseStartState c = new ChooseStartState(board);
		board.setState(c);
		c.init();
		return 2;
	}

}
