/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.gamestate.MenuState;

/**
 * Modelliert den Quitbutton
 *
 */
public class QuitButton extends ModelVisitor {
	private Menu menu;
	
	/**
	 * Konstruktor
	 * @param menu Das Menu Element
	 */
	public QuitButton(Menu menu) {
		this.menu = menu;
	}
	
	/**
	 * gibt das MenuElement zurück
	 * @return das MenuElement
	 */
	public Menu getMenu() {
		return menu;
	}
	
	/**
	 * Besucht den Menu State zustand
	 */
	public int visit(MenuState g) {
		g.quitGame();
		return 5;
	}

}
