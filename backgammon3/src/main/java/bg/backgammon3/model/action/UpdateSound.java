/**
 * 
 */
package bg.backgammon3.model.action;


/**
 * @author philipp
 *
 */
public class UpdateSound extends Action{

	@Override
	public int getTime() {
		return 0;
	}


	public int visit(GameStageElement element) {
		element.sound();
		return 0;
	}
	
}
