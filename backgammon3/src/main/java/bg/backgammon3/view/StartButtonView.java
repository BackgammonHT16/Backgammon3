/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.Menu;
import bg.backgammon3.model.StartButton;
import javafx.scene.control.Button;

/**
 * @author philipp
 *
 */
public class StartButtonView extends Button implements GameObjectView {
	private Menu menu;
	
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
