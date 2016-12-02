/**
 * 
 */
package bg.backgammon3.model;


import bg.backgammon3.model.boardstate.*;
import bg.backgammon3.model.gamestate.*;
import bg.backgammon3.model.player.AI2;
import bg.backgammon3.model.player.Human;

/**
 * Klasse die den Model Visitor implementiert
 *
 */
public class ModelVisitor {
	// Wird von GameController gesetzt und gibt an ob noch eine
	// animation stattfindet. Dadurch sollte sich nur der MenuButton dazu
	// entscheiden etwas zu tun.
	private boolean busy;


	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(AI2 view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(Board view){
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(Game view){
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(GameControllerElement view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(Human view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(ChooseEndState view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(ChooseStartState view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(GameOverState view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(WaitingState view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(RollDiceState view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(StartSecondDiceState view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(StartState view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(GameRunningState view) {
		return view.nextAccept(this);
	}
	/**
	 * Besucht das Objekt
	 * @param view Das zu besuchende Objekt
	 * @return der GameState
	 */
	public int visit(MenuState view) {
		return view.nextAccept(this);
	}

	/**
	 * Gibt an ob der Controller Busy ist.
	 * @return Wahr wenn der Busy ist
	 */
	public boolean getBusy() {
		return busy;
	}

	/**
	 * Setzt das Objekt auf Busy
	 * @param busy Wahr wenn der Controller busy ist
	 */
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
