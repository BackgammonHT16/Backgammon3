/**
 * 
 */
package bg.backgammon3.model.place;

/**
 * 
 *
 */
public class Goal extends Place {
	public Goal(Integer numberOfCheckers, Integer playerId, Integer id) {
		super(id);
		this.addChecker(numberOfCheckers, playerId);
		this.playerId = playerId;
	}

	@Override
	public String toString() {
		return "Goal " + super.toString().substring(6);
	}
}
