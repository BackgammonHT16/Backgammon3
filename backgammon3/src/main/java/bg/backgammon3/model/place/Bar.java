/**
 * 
 */
package bg.backgammon3.model.place;

/**
 * @author philipp
 *
 */
public class Bar extends Place{

	public Bar(Integer numberOfCheckers, Integer playerId, Integer id) {
		super(id);
		this.addChecker(numberOfCheckers, playerId);
		this.playerId = playerId;
	}

	@Override
	public String toString() {
		return "Bar " + super.toString().substring(6);
	}
}
