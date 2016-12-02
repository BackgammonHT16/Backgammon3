/**
 * 
 */
package bg.backgammon3.model.place;

/**
 * Die Bar
 *
 */
public class Bar extends Place{

	/**
	 * Konstruktor
	 * @param numberOfCheckers anzahl der Checker
	 * @param playerId die PlayerId
	 * @param id  Die Bar id
	 */
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
