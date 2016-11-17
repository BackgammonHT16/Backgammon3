/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.gamestate.MenuState;

/**
 * 
 *
 */
public class QuitButton extends ModelVisitor {
	private Menu menu;
	
	public QuitButton(Menu menu) {
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public int visit(MenuState g) {
		g.quitGame();
		return 0;
	}

}
