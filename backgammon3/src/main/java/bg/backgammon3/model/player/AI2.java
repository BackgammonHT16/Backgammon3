/**
 * 
 */
package bg.backgammon3.model.player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.ModelElement;
import bg.backgammon3.model.Timer;
import bg.backgammon3.model.UpdateAI;
import bg.backgammon3.model.place.Goal;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.EndPoint;
import bg.backgammon3.model.pointstate.StartPoint;

/**
 * Die Ai
 *
 */
public class AI2 extends Player implements ModelElement {
	private Logger logger = LogManager.getLogger(AI2.class);

	private Place endPlace;
	
	private AIHelper aiHelper;
	
	/**
	 * Der Konstruktor
	 * @param id die Id
	 * @param aiHelper die Hilfsklasse
	 */
	public AI2(Integer id, AIHelper aiHelper){
		super(id);
		logger.info("AI Hard erstellt mit id " + id);
		this.aiHelper = aiHelper;
	}
	
	/**
	 * Bewertet einen Platz nach seiner Position in der Route von 411 bis 0
	 * @param place Der zu bewertende Platz
	 * @return Der Wert der dieser Position zugeordnet wurde
	 */
	private int rankInRoute(Place place) {
		int routeId = board.getRoute(id).getRouteId(place);
		int routeSize = board.getRoute(id).size();
		int rank = (int)(((double) aiHelper.getRule(8)) / 100000  * Math.pow((routeSize - routeId), 4));
		return rank;
	}

	@Override
	public int selectStartPlace() {
		logger.info("AI Hard wählt StartPlace.");
		Place startPlace = null; 
		int lowerBound = aiHelper.getBottom();
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
						ranking.get(p).set(move, ranking.get(p).get(move) + aiHelper.getRule(0) + rankInRoute(places.get(p)));
					}
					if (places.get(p).getNumberOfCheckers() == 2) {
						// 1
						// Wenn wir hier wegziehen lassen wir einen einzelnen Checker zurück
						ranking.get(p).set(move, ranking.get(p).get(move) + aiHelper.getRule(1));
					} 
					if (places.get(move).getNumberOfCheckers() == 2) {
						// 2
						// Neues Feld sind 2 Checker
						ranking.get(p).set(move, ranking.get(p).get(move) + aiHelper.getRule(2));
					}
					if (places.get(move).getNumberOfCheckers() == 0) {
						// 3
						// Neues Feld ist kein Checker
						ranking.get(p).set(move, ranking.get(p).get(move) + aiHelper.getRule(3));
					}
					if (places.get(move).getNumberOfCheckers() == 1) {
						// 4
						// Neues Feld ist ein Checker
						ranking.get(p).set(move, ranking.get(p).get(move) + aiHelper.getRule(4));
					}
					if (places.get(move).getNumberOfCheckers() > 3) {
						// 5
						// Neues Feld sind mindestens 3 Checker
						ranking.get(p).set(move, ranking.get(p).get(move) + aiHelper.getRule(5));
					}
					if (places.get(move) instanceof Goal) {
						// 6
						// Neues Feld ist das Goal
						ranking.get(p).set(move, ranking.get(p).get(move) + aiHelper.getRule(6));
					}
					if (places.get(p).getNumberOfCheckers() == 3) {
						// 7
						// Zwei Steine zurücklassen
						ranking.get(p).set(move, ranking.get(p).get(move) + aiHelper.getRule(7));
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
		
		// Objekt ermitteln und Speichern
		startPlace = places.get(bestStartId);
		endPlace = places.get(bestEndId);
		
		board.accept(startPlace);
		return 2;
	}
	
	@Override
	public int selectEndPlace() {
		logger.info("AI wählt EndPlace.");
		
		board.accept(endPlace);
		return 1;
	}

	@Override
	public int rollDice() {
		logger.info("AI Hard würfelt.");
		return board.accept(board.getDices());
		//return 1;
	}

	@Override
	public int accept(ModelVisitor gameObject) {
		return gameObject.visit(this);
	}

	@Override
	public int nextAccept(ModelVisitor gameObject) {
		return 0;
	}
	
}
