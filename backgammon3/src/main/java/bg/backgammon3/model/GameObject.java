/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.action.GameControllerElement;
import bg.backgammon3.model.boardstate.*;
import bg.backgammon3.model.gamestate.*;
import bg.backgammon3.model.player.AI;
import bg.backgammon3.model.player.AI2;
import bg.backgammon3.model.player.Human;

/**
 * @author philipp
 *
 */
public class GameObject {
	// Wird von GameController gesetzt und gibt an ob noch eine
	// animation stattfindet. Dadurch sollte sich nur der MenuButton dazu
	// entscheiden etwas zu tun.
	private boolean busy;

	public int visit(AI view) {
		return view.nextAccept(this);
	}
	public int visit(AI2 view) {
		return view.nextAccept(this);
	}
	public int visit(Board view){
		return view.nextAccept(this);
	}
	public int visit(Game view){
		return view.nextAccept(this);
	}
	public int visit(GameControllerElement view) {
		return view.nextAccept(this);
	}
	public int visit(Human view) {
		return view.nextAccept(this);
	}
	public int visit(ChooseEndState view) {
		return view.nextAccept(this);
	}
	public int visit(ChooseStartState view) {
		return view.nextAccept(this);
	}
	public int visit(GameOverState view) {
		return view.nextAccept(this);
	}
	public int visit(WaitingState view) {
		return view.nextAccept(this);
	}
	public int visit(RollDiceState view) {
		return view.nextAccept(this);
	}
	public int visit(StartSecondDiceState view) {
		return view.nextAccept(this);
	}
	public int visit(StartState view) {
		return view.nextAccept(this);
	}
	public int visit(GameRunningState view) {
		return view.nextAccept(this);
	}
	public int visit(MenuState view) {
		return view.nextAccept(this);
	}

	
	public boolean getBusy() {
		return busy;
	}

	
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
