/**
 * 
 */
package bg.backgammon3.model.place;

/**
 * 
 *
 */
public class Point extends Place {

	public Point(Integer numberOfCheckers, Integer playerId, Integer id) {
		super(id);
		this.addChecker(numberOfCheckers, playerId);
	}

	@Override
	public String toString() {
		return "Point " + super.toString().substring(6);
	}
}
