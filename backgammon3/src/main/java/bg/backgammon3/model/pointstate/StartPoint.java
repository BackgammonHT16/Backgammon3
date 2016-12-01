/**
 * 
 */
package bg.backgammon3.model.pointstate;

import java.util.ArrayList;

import bg.backgammon3.model.boardstate.ChooseStartState;
import bg.backgammon3.model.place.Place;

/**
 * 
 *
 */
public class StartPoint extends PointState {
	private Integer playerId;
	private ArrayList<Place> endPlaces = new ArrayList<Place>();
	private boolean selected;
	
	public StartPoint(Integer playerId, ArrayList<Place> endPlaces) {
		this.playerId = playerId;
		this.endPlaces = endPlaces;
		this.selected = false;
	}	
	
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
	
	public ArrayList<Place> getEndPlaces() {
		return endPlaces;
	}

	@Override
	public int visit(ChooseStartState g, Place place) {
		return g.selectStartPlace(place);
	}
}
