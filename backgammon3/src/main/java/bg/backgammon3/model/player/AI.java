/**
 * 
 */
package bg.backgammon3.model.player;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.ModelElement;
import bg.backgammon3.model.Timer;
import bg.backgammon3.model.UpdateAI;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.EndPoint;
import bg.backgammon3.model.pointstate.StartPoint;

/**
 * 
 *
 */
public class AI extends Player implements ModelElement {
	private Logger logger = LogManager.getLogger(AI.class);

	public AI(Integer id){
		super(id);
		logger.info("AI Easy erstellt mit id " + id);
	}

	@Override
	public void selectStartPlace() {
		logger.info("AI Easy wählt StartPlace.");
		Place startPlace = null; 
		for(Map.Entry<Integer, Place> p : board.getPlaces().entrySet()) {
			if(p.getValue().getState() instanceof StartPoint) {
				startPlace = p.getValue();
			}
		}
		board.accept(startPlace);
	}

	@Override
	public void selectEndPlace() {
		logger.info("AI Easy wählt EndPlace.");
		Place endPlace = null; 
		for(Map.Entry<Integer, Place> p : board.getPlaces().entrySet()) {
			if(p.getValue().getState() instanceof EndPoint) {
				endPlace = p.getValue();
			}
		}
		board.accept(endPlace);
	}

	@Override
	public void rollDice() {
		logger.info("AI Easy würfelt.");
		board.accept(board.getDices());
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
