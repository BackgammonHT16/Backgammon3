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
public class AI2 extends Player {
	private Logger logger = LogManager.getLogger(AI2.class);

	public AI2(Integer id){
		super(id);
		logger.info("AI Hard erstellt mit id " + id);
	}

	@Override
	public void handle(GameObject gameObject) {
		if(gameObject instanceof UpdateAI) {
			board.handle(gameObject);
		}
	}
	
	public void selectStartPlace() {
		logger.info("AI Hard wählt StartPlace.");
		Place startPlace = null; 
		for(Map.Entry<Integer, Place> p : board.getPlaces().entrySet()) {
			if(p.getValue().getState() instanceof StartPoint) {
				startPlace = p.getValue();
			}
		}
		board.handle(startPlace);
	}
	
	public void selectEndPlace() {
		logger.info("AI Hard wählt EndPlace.");
		Place endPlace = null; 
		for(Map.Entry<Integer, Place> p : board.getPlaces().entrySet()) {
			if(p.getValue().getState() instanceof EndPoint) {
				endPlace = p.getValue();
			}
		}
		board.handle(endPlace);
	}
	
	public void rollDice() {
		logger.info("AI Hard würfelt.");
		board.handle(board.getDices());
	}
}
