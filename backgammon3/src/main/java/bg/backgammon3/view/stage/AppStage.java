/**
 * 
 */
package bg.backgammon3.view.stage;

import java.util.ArrayList;

import bg.backgammon3.model.*;
import bg.backgammon3.model.action.Action;
import javafx.scene.Node;

/**
 * @author philipp
 *
 */
public abstract class AppStage {
	protected Game game;
	
	public AppStage(Game game)
	{
		this.game = game;
	}
	
	public abstract ArrayList<Node> getControls();

	public abstract void update(Action action);
	
	public abstract void hide();

}
