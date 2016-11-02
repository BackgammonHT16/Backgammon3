/**
 * 
 */
package bg.backgammon3.model;

/**
 * @author philipp
 *
 */
public class StartButton extends GameObject {
	Menu menu;
	
	public StartButton(Menu menu) {
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}

}
