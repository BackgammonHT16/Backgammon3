/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.Menu;
import bg.backgammon3.model.StartButton;
import javafx.scene.control.Button;

/**
 * Die StartButtonView
 *
 */
public class StartButtonView extends Button implements GameObjectView {
	private Menu menu;
	
	/**
	 * Der Konstruktor
	 * @param text der Text
	 * @param menu das menu
	 */
	public StartButtonView(String text, Menu menu)
	{
		super(text);
		this.menu = menu;
	}
	
	public StartButton getGameObject()
	{
		return new StartButton(menu);
	}
}
