/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.config.Config;

/**
 * @author philipp
 *
 */
public class DisableContinueButton extends Action {

	@Override
	public int getTime() {
		return 0;
	}

	public boolean visitImmediately() {
		return true;
	}

	public int visit(MenuElement view) {
		if(Config.getInteger("graphics") == 1){
			view.disableContinueButton();
		}
		return 0;
	}
}
