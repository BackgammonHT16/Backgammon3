/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.place.Place;

/**
 * Modelliert einen Move
 *
 */
public class Move {
	private int startPlace;
	private int endPlace;
	
	/**
	 * Konstruktor
	 * @param startPlace startPlatz
	 * @param endPlace endPlatz
	 */
	public Move(int startPlace, int endPlace) {
		this.startPlace = startPlace;
		this.endPlace = endPlace;
	}
	
	/**
	 * Gibt den StartPlatz zurück
	 * @return der StartPlatz
	 */
	public int getStartPlace() {
		return startPlace;
	}
	
	/**
	 * Gibt den Endplatz zurück
	 * @return der Endplatz
	 */
	public int getEndPlace() {
		return endPlace;
	}
}
