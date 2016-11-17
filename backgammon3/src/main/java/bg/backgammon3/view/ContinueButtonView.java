/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.*;
import javafx.scene.control.Button;

/**
 * 
 *
 */
public class ContinueButtonView extends Button implements GameObjectView {
	private Menu menu;
	public ContinueButtonView(String text, Menu menu)
	{
		super(text);
		this.menu = menu;
	}
	

	public ContinueButton getGameObject()
	{
		return new ContinueButton(menu);
	}
}
