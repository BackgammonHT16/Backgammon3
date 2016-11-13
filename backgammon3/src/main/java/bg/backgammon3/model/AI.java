/**
 * 
 */
package bg.backgammon3.model;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.EndPoint;
import bg.backgammon3.model.pointstate.StartPoint;

/**
 * @author philipp
 *
 */
public class AI extends Player implements ModelElement {
	private Logger logger = LogManager.getLogger(AI.class);

	public AI(Integer id){
		super(id);
		logger.info("AI Easy erstellt mit id " + id);
	}

	@Override
	public void handle(GameObject gameObject) {
		if(gameObject instanceof UpdateAI) {
			board.handle(gameObject);
		} else if (gameObject instanceof Timer) {
			board.handle(gameObject);
		}
	}
	
	public void selectStartPlace() {
		logger.info("AI Easy wählt StartPlace.");
		Place startPlace = null; 
		for(Map.Entry<Integer, Place> p : board.getPlaces().entrySet()) {
			if(p.getValue().getState() instanceof StartPoint) {
				startPlace = p.getValue();
			}
		}
		//board.handle(startPlace);
		board.accept(startPlace);
	}
	
	public void selectEndPlace() {
		logger.info("AI Easy wählt EndPlace.");
		Place endPlace = null; 
		for(Map.Entry<Integer, Place> p : board.getPlaces().entrySet()) {
			if(p.getValue().getState() instanceof EndPoint) {
				endPlace = p.getValue();
			}
		}
		//board.handle(endPlace);
		board.accept(endPlace);
	}
	
	public void rollDice() {
		logger.info("AI Easy würfelt.");
		//board.handle(board.getDices());
		board.accept(board.getDices());
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
}
