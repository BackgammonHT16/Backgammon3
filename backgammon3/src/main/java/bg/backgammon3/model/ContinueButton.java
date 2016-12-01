/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.gamestate.MenuState;

/**
 * 
 *
 */
public class ContinueButton extends ModelVisitor {
	Menu menu;
	
	public ContinueButton(Menu menu) {
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public int visit(MenuState g) {
		g.continueGame(menu);
		return 6;
	}
}
