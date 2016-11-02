/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.Board;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.view.helper.StaticImageHelper;
import javafx.scene.image.ImageView;

/**
 * @author philipp
 *
 */
public class BackgroundView extends ImageView implements GameObjectView {
	Board board;
	
	public BackgroundView(Board board) {
		this.board = board;
		StaticImageHelper.loadImageView(Config.getString("bgImage"), this);
	}
	
	
	@Override
	public GameObject getGameObject() {
		return board;
	}

}
