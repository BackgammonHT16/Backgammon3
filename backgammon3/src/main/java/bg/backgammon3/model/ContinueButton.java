/**
 * 
 */
package bg.backgammon3.model;

/**
 * @author philipp
 *
 */
public class ContinueButton extends GameObject {
	Menu menu;
	
	public ContinueButton(Menu menu) {
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}
}
