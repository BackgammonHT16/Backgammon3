/**
 * 
 */
package bg.backgammon3.model.place;

/**
 * Point
 *
 */
public class Point extends Place {

	/**
	 * Konstruktor
	 * @param numberOfCheckers Anzahl der Checker
	 * @param playerId die PlayerId
	 * @param id  Die Point id
	 */
	public Point(Integer numberOfCheckers, Integer playerId, Integer id) {
		super(id);
		this.addChecker(numberOfCheckers, playerId);
	}

	@Override
	public String toString() {
		return "Point " + super.toString().substring(6);
	}
}
