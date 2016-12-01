/**
 * 
 */
package bg.backgammon3.model.action;

/**
 * Diese klasse stellt die Funktionen aus der View dar sodass diese von
 * den Action Elementen aufgerufen werden können ohne dass diese die View
 * kennen müssen
 * 
 *
 */
public abstract class GameElement {
	
	public void sound() {

	}
	
	public void setMessage(String message) {
		
	}
	
	public boolean update() {
		return false;
	}
	
	public void update(boolean showHighlights) {
		
	}
	
	public int singleDiceWasRolled() {
		return 0;
	}
	
	public int diceWasRolled() {
		return 0;
	}
	
	public void diceWasUsed() {
		
	}
	
	public int moveChecker(Integer startId, Integer endId) {
		return 0;
	}
	
	public void startTimer() {
		
	}
	
	public void disableContinueButton() {
		
	}
	
	public abstract int accept(Action action);
	
	public abstract int nextAccept(Action action);
}
