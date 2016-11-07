/**
 * 
 */
package bg.backgammon3.model.pointstate;

import java.util.ArrayList;

import bg.backgammon3.model.place.Place;

/**
 * @author philipp
 *
 */
public class StartPoint extends PointState {
	private Integer playerId;
	private ArrayList<Place> endPlaces = new ArrayList<Place>();
	
	public StartPoint(Integer playerId, ArrayList<Place> endPlaces) {
		this.playerId = playerId;
		this.endPlaces = endPlaces;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}
	
	public ArrayList<Place> getEndPlaces() {
		return endPlaces;
	}
}
