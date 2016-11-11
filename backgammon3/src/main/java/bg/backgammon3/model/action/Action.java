/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.view.View;

/**
 * @author philipp
 *
 */
public abstract class Action {
	
	public abstract int getTime();
	
	public int visit(GameElement view) {
		return view.nextAccept(this);
	}
}
