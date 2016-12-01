/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.place.Place;

/**
 * @author philipp
 *
 */
public class Move {
	private int startPlace;
	private int endPlace;
	
	public Move(int startPlace, int endPlace) {
		this.startPlace = startPlace;
		this.endPlace = endPlace;
	}
	
	public int getStartPlace() {
		return startPlace;
	}
	
	public int getEndPlace() {
		return endPlace;
	}
}
