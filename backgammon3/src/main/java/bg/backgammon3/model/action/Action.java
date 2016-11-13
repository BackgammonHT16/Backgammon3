/**
 * 
 */
package bg.backgammon3.model.action;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author philipp
 *
 */
public class Action {
	// false wenn die Aktion noch ausgeführt werden muss
	public AtomicBoolean actionIsDone = new AtomicBoolean(false);
	
	public int getTime() {
		return 0;
	}
	
	public boolean visitImmediately() {
		return false;
	}

	public int visit(GameControllerElement g) {
		return g.nextAccept(this);
	}
	public int visit(BoardElement view) {
		return view.nextAccept(this);
	}
	public int visit(GameStageElement view) {
		return view.nextAccept(this);
	}
	public int visit(MenuElement view){
		return view.nextAccept(this);
	}
}
