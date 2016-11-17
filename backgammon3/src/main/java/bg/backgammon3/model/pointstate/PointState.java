/**
 * 
 */
package bg.backgammon3.model.pointstate;

import bg.backgammon3.model.boardstate.*;
import bg.backgammon3.model.place.*;

/**
 * 
 *
 */
public abstract class PointState {
	public abstract boolean getSelected();
	public abstract Integer getPlayerId();

	public int visit(ChooseStartState g, Place place) {
		g.nextAccept(place);
		return 0;
	}
	
	public int visit(ChooseEndState g, Place place) {
		g.nextAccept(place);
		return 0;
	}
}
