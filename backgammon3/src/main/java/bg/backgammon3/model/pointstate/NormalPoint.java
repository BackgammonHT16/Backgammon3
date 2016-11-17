/**
 * 
 */
package bg.backgammon3.model.pointstate;

/**
 * 
 *
 */
public class NormalPoint extends PointState {
	private boolean selected;

	public NormalPoint() {
		this.selected = false;
	}
	
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
