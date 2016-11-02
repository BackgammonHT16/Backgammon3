/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.*;
import javafx.scene.control.Button;

/**
 * @author philipp
 *
 */
public class QuitButtonView extends Button implements GameObjectView {
	private Menu menu;
	
	public QuitButtonView(String text, Menu menu)
	{
		super(text);
		this.menu = menu;
	}
	
	public QuitButton getGameObject()
	{
		return new QuitButton(menu);
	}
}
