/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.model.UpdateAI;

/**
 * @author philipp
 *
 */
public class UpdateModel extends Action {

	@Override
	public int getTime() {
		return 0;
	}

	@Override
	public int visit(GameControllerElement g) {
		UpdateAI updateAI = new UpdateAI();
		updateAI.setBusy(false);
		g.nextAccept(updateAI);
		return 0;
	}


}
