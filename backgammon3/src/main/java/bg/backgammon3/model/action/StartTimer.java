/**
 * 
 */
package bg.backgammon3.model.action;



/**
 * @author philipp
 *
 */
public class StartTimer extends Action {

	@Override
	public int getTime() {
		return 0;
	}

	public int visit(BoardElement view) {
		view.startTimer();
		return 0;
	}


}
