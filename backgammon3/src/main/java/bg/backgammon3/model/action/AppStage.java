/**
 * 
 */
package bg.backgammon3.model.action;

import java.util.ArrayList;

import bg.backgammon3.model.action.GameElement;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * 
 *
 */
public abstract class AppStage extends GameElement {
	
	public abstract ArrayList<Node> getControls();
	
	public abstract void hide(); 
	
	public abstract Stage getStage();

}
