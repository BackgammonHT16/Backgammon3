/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.*;
import javafx.scene.control.Button;

/**
 * Die QuitbuttonView
 *
 */
public class QuitButtonView extends Button implements GameObjectView {
	private Menu menu;
	
	/**
	 * Der Konstruktor
	 * @param text der Text
	 * @param menu das MenuElement
	 */
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
