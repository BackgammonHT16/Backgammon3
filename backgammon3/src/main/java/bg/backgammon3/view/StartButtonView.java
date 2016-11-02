/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.StartButton;
import javafx.scene.control.Button;

/**
 * @author philipp
 *
 */
public class StartButtonView extends Button {
	public StartButtonView(String text)
	{
		super(text);
	}
	
	public StartButton getGameObject()
	{
		return new StartButton();
	}
}
