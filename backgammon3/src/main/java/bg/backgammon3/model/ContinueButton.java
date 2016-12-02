/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.gamestate.MenuState;

/**
 * ContinueButton Klasse modelliert den Continue Button
 *
 */
public class ContinueButton extends ModelVisitor {
	Menu menu;
	
	/**
	 * Konstruktor
	 * @param menu Menu Element
	 */
	public ContinueButton(Menu menu) {
		this.menu = menu;
	}
	
	/**
	 * Menu
	 * @return Menu Element
	 */
	public Menu getMenu() {
		return menu;
	}
	
	@Override
	public int visit(MenuState g) {
		g.continueGame(menu);
		return 6;
	}
}
