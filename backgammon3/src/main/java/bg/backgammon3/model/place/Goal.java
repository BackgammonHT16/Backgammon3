/**
 * 
 */
package bg.backgammon3.model.place;

/**
 * Das Goal
 *
 */
public class Goal extends Place {

	/**
	 * Konstruktor
	 * @param numberOfCheckers Anzahl der Checker
	 * @param playerId die PlayerId
	 * @param id  Die Goal id
	 */
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
