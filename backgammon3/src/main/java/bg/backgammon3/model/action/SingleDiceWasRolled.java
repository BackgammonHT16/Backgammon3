/**
 * 
 */
package bg.backgammon3.model.action;


/**
 * @author philipp
 *
 */
public class SingleDiceWasRolled extends Action {

	@Override
	public int getTime() {
		return 0;
	}

	public int visit(BoardElement view) {
		return view.singleDiceWasRolled();
	}


}
