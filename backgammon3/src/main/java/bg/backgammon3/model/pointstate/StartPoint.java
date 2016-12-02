/**
 * 
 */
package bg.backgammon3.model.pointstate;

import java.util.ArrayList;

import bg.backgammon3.model.boardstate.ChooseStartState;
import bg.backgammon3.model.place.Place;

/**
 * Punkt ist StartPlatz
 *
 */
public class StartPoint extends PointState {
	private Integer playerId;
	private ArrayList<Place> endPlaces = new ArrayList<Place>();
	private boolean selected;
	
	/**
	 * Konstruktor
	 * @param playerId Id des Players
	 * @param endPlaces die Endplätze
	 */
	public StartPoint(Integer playerId, ArrayList<Place> endPlaces) {
		this.playerId = playerId;
		this.endPlaces = endPlaces;
		this.selected = false;
	}	
	
	/**
	 * Der StartPunktZustand Konstruktor
	 * @param playerId Id des Players
	 * @param endPlaces die Endplätze
	 * @param selected wahr wenn der Platz ausgewählt wurde
	 */
	public StartPoint(Integer playerId, ArrayList<Place> endPlaces, boolean selected) {
		this.playerId = playerId;
		this.endPlaces = endPlaces;
		this.selected = selected;
	}

	public Integer getPlayerId() {
		return playerId;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	/** 
	 * Gibt die möglichen endplaces zurück
	 * @return die Endplaces
	 */
	public ArrayList<Place> getEndPlaces() {
		return endPlaces;
	}

	@Override
	public int visit(ChooseStartState g, Place place) {
		return g.selectStartPlace(place);
	}
}
