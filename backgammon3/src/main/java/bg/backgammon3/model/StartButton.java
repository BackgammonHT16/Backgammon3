/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.gamestate.MenuState;

/**
 * 
 *
 */
public class StartButton extends ModelVisitor {
	Menu menu;
	
	public StartButton(Menu menu) {
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}

	public int visit(MenuState g) {
		g.startGame(menu);
		return 4;
	}
}
