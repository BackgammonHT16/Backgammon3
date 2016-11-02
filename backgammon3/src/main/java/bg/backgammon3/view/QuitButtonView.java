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
public class QuitButtonView extends Button {

	public QuitButtonView(String text)
	{
		super(text);
	}
	
	public QuitButton getGameObject()
	{
		return new QuitButton();
	}
}
