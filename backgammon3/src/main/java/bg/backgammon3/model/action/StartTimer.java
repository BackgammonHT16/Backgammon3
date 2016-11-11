/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.view.BoardView;

/**
 * @author philipp
 *
 */
public class StartTimer extends Action {

	@Override
	public int getTime() {
		return 0;
	}

	public int visit(BoardView view) {
		view.startTimer();
		return 0;
	}
}
