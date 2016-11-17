/**
 * 
 */
package bg.backgammon3.model.action;



/**
 * 
 *
 */
public class ShowRoute extends Action{

	@Override
	public int getTime() {
		return 0;
	}


	public int visit(BoardElement view) {
		view.update();
		return 0;
	}
	
}
