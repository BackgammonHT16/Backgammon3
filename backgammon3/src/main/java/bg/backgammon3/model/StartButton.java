/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.gamestate.MenuState;

/**
 * Modelliert den StartKnopf
 *
 */
public class StartButton extends ModelVisitor {
	Menu menu;
	
	/** 
	 * Konstruktor
	 * @param menu das MenuElement
	 */
	public StartButton(Menu menu) {
		this.menu = menu;
	}
	
	/**
	 * Das MenuElement
	 * @return Das MenuElement
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Besucht den MenuState
	 */
	public int visit(MenuState g) {
		g.startGame(menu);
		return 4;
	}
}
