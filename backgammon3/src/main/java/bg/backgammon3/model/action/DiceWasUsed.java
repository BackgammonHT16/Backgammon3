/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.view.BoardView;

/**
 * @author philipp
 *
 */
public class DiceWasUsed extends Action {

	@Override
	public int getTime() {
		return 0;
	}

	public int visit(BoardView view) {
		view.singleDiceWasRolled();
		return 0;
	}
}
