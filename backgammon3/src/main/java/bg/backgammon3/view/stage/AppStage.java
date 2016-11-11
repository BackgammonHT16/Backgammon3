/**
 * 
 */
package bg.backgammon3.view.stage;

import java.util.ArrayList;

import bg.backgammon3.model.*;
import bg.backgammon3.model.action.Action;
import bg.backgammon3.model.action.GameElement;
import bg.backgammon3.view.View;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author philipp
 *
 */
public abstract class AppStage extends GameElement {
	
	public abstract ArrayList<Node> getControls();

	public abstract int update(Action action);
	
	public abstract void hide(); 
	
	public abstract Stage getStage();

}
