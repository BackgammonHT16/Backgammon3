/**
 * 
 */
package bg.backgammon3.model;

/**
 * @author philipp
 *
 */
public class QuitButton extends GameObject {
	private Menu menu;
	
	public QuitButton(Menu menu) {
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}

}
