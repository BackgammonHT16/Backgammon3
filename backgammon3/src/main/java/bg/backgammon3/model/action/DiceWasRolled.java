/**
 * 
 */
package bg.backgammon3.model.action;



/**
 * 
 *
 */
public class DiceWasRolled extends Action {

	@Override
	public int getTime() {
		return 0;
	}


	
	public int visit(BoardElement view) {
		return view.diceWasRolled();
	}

}
