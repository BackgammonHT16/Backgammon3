/**
 * 
 */
package bg.backgammon3.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.place.Goal;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.EndPoint;
import bg.backgammon3.model.pointstate.StartPoint;

/**
 * @author philipp
 *
 */
public class AI2 extends Player implements ModelElement {
	private Logger logger = LogManager.getLogger(AI2.class);

	private Place endPlace;
	
	public AI2(Integer id){
		super(id);
		logger.info("AI Hard erstellt mit id " + id);
	}

	@Override
	public void handle(GameObject gameObject) {
		if(gameObject instanceof UpdateAI) {
			board.handle(gameObject);
		} else if (gameObject instanceof Timer) {
			board.handle(gameObject);
		}
	}
	
	/**
	 * Bewertet einen Platz nach seiner Position in der Route von 411 bis 0
	 * @param place Der zu bewertende Platz
	 * @return Der Wert der dieser Position zugeordnet wurde
	 */
	private int rankInRoute(Place place) {
		int routeId = board.getRoute(id).getRouteId(place);
		int routeSize = board.getRoute(id).size();
		int rank = (int)(0.0009 * Math.pow((routeSize - routeId), 4));
		return rank;
	}

	@Override
	public void selectStartPlace() {
		logger.info("AI Hard wählt StartPlace.");
		Place startPlace = null; 
		int lowerBound = -1000;
		LinkedHashMap<Integer, Place> places = board.getPlaces();
		int numberPlaces = places.size();
		ArrayList<ArrayList<Integer>> ranking = new ArrayList<ArrayList<Integer>>();
		
		// Array initialisieren mit lowerBound
		for(int p = 0; p < numberPlaces; p++) {
			ranking.add(new ArrayList<Integer>(numberPlaces));
			for(int move = 0; move < numberPlaces; move++) {
				ranking.get(p).add(lowerBound);
			}
			// Gültige EndPlätze erhalten einen wert von 0
			if(places.get(p).getState() instanceof StartPoint) {
				StartPoint start = (StartPoint) places.get(p).getState();
				for(int i = 0; i < start.getEndPlaces().size(); i++) {
					ranking.get(p).set(start.getEndPlaces().get(i).getId(), 0);
				}
			}
		}
		
		
		
		// Bewertung der Züge
		for(int p = 0; p < numberPlaces; p++) {
			for(int move = 0; move < numberPlaces; move++) {
				if(ranking.get(p).get(move) > lowerBound) {
					// Es handelt sich um einen legalen StartPlatz
					
					// 8 
					// Entfernung von Checker zu Goal
					ranking.get(p).set(move, ranking.get(p).get(move) + rankInRoute(places.get(p)));
					
					if (places.get(move).getPlayerId() != -1 && places.get(move).getPlayerId() != this.id) {
						// 0
						// Gegner Schlagen
						ranking.get(p).set(move, ranking.get(p).get(move) + 50 + rankInRoute(places.get(p)));
					}
					if (places.get(p).getNumberOfCheckers() == 2) {
						// 1
						// Wenn wir hier wegziehen lassen wir einen einzelnen Checker zurück
						ranking.get(p).set(move, ranking.get(p).get(move) - 100);
					} 
					if (places.get(move).getNumberOfCheckers() == 2) {
						// 2
						// Neues Feld sind 2 Checker
						ranking.get(p).set(move, ranking.get(p).get(move) + 80);
					}
					if (places.get(move).getNumberOfCheckers() == 0) {
						// 3
						// Neues Feld ist kein Checker
						ranking.get(p).set(move, ranking.get(p).get(move) - 100);
					}
					if (places.get(move).getNumberOfCheckers() == 1) {
						// 4
						// Neues Feld ist ein Checker
						ranking.get(p).set(move, ranking.get(p).get(move) + 90);
					}
					if (places.get(move).getNumberOfCheckers() > 3) {
						// 5
						// Neues Feld sind mindestens 3 Checker
						ranking.get(p).set(move, ranking.get(p).get(move) + 75);
					}
					if (places.get(move) instanceof Goal) {
						// 6
						// Neues Feld ist das Goal
						ranking.get(p).set(move, ranking.get(p).get(move) + 300);
					}
					if (places.get(p).getNumberOfCheckers() == 3) {
						// 7
						// Zwei Steine zurücklassen
						ranking.get(p).set(move, ranking.get(p).get(move) - 20);
					}
				} 
			}
		}
		

		// Auswählen des besten Zuges
		int maxRank = lowerBound;
		int bestStartId = 0;
		int bestEndId = 0;
		for(int p = 0; p < numberPlaces; p++) {
			for(int move = 0; move < numberPlaces; move++) {
		//for(int p = numberPlaces - 1; p >= 0; p--) {
		//	for(int move = numberPlaces - 1; move >= 0; move--) {
				if(maxRank < ranking.get(p).get(move)) {
					maxRank = ranking.get(p).get(move);
					bestStartId = p;
					bestEndId = move;
				}
			}
		}
		
		// Objekt ermitteln und Speichern mit Absicherung
		startPlace = places.get(bestStartId);
		endPlace = places.get(bestEndId);
		
		if(!(startPlace.getState() instanceof StartPoint)){
			for(Map.Entry<Integer, Place> p : board.getPlaces().entrySet()) {
				if(p.getValue().getState() instanceof StartPoint) {
					startPlace = p.getValue();
				}
			}
			logger.error("AI2 chose illegal StartPlace\n\n\n\n\n\n\n\n\n\n\n\n\n");
		}
		//board.handle(startPlace);
		board.accept(startPlace);
	}
	
	@Override
	public void selectEndPlace() {
		logger.info("AI Hard wählt EndPlace.");
		if(!(endPlace.getState() instanceof EndPoint)){
			for(Map.Entry<Integer, Place> p : board.getPlaces().entrySet()) {
				if(p.getValue().getState() instanceof EndPoint) {
					endPlace = p.getValue();
				}
			}
			logger.error("AI2 chose illegal EndPlace\n\n\n\n\n\n\n\n\n\n\n\n\n");
		}
		//board.handle(endPlace);
		board.accept(endPlace);
	}

	@Override
	public void rollDice() {
		logger.info("AI Hard würfelt.");
		board.accept(board.getDices());
		//board.handle(board.getDices());
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
