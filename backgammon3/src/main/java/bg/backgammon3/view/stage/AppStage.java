/**
 * 
 */
package bg.backgammon3.view.stage;

import java.util.ArrayList;

import bg.backgammon3.model.*;
import bg.backgammon3.model.action.Action;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author Philipp Sammeck
 *
 */
public abstract class AppStage {
	protected Game game;
	
	public AppStage(Game game)
	{
		this.game = game;
	}
	
	public abstract ArrayList<Node> getControls();

	public abstract int update(Action action);
	
	public abstract void hide(); 
	
	public abstract Stage getStage();

}
