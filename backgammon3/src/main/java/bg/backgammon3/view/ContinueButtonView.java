/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.*;
import javafx.scene.control.Button;

/**
 * Die View des Continue Buttons
 *
 */
public class ContinueButtonView extends Button implements GameObjectView {
	private Menu menu;
	/**
	 * Konstruktor
	 * @param text der Text
 	 * @param menu das Menu Element
	 */
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
