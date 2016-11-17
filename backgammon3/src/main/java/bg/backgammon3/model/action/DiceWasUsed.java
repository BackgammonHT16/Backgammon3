/**
 * 
 */
package bg.backgammon3.model.action;



/**
 * 
 *
 */
public class DiceWasUsed extends Action {

	@Override
	public int getTime() {
		return 0;
	}


	
	public int visit(BoardElement view) {
		view.diceWasUsed();
		return 0;
	}

}
