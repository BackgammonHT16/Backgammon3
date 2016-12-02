/**
 * 
 */
package bg.backgammon3.model.pointstate;

/**
 * Ein Normaler Punkt
 *
 */
public class NormalPoint extends PointState {
	private boolean selected;

	/**
	 * Konstruktor
	 */
	public NormalPoint() {
		this.selected = false;
	}
	
	/**
	 * Der konstruktor
	 * @param selected wahr wenn der Punkt ausgewählt ist
	 */
	public NormalPoint(boolean selected) {
		this.selected = selected;
	}
	
	public boolean getSelected() {
		return selected;
	}

	@Override
	public Integer getPlayerId() {
		return -1;
	}
}
