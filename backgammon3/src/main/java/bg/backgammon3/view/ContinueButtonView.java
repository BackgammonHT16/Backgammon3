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
public class ContinueButtonView extends Button {
	public ContinueButtonView(String text)
	{
		super(text);
	}
	

	public ContinueButton getGameObject()
	{
		return new ContinueButton();
	}
}
