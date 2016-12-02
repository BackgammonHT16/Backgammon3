/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.Board;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.view.helper.StaticImageHelper;
import javafx.scene.image.ImageView;

/**
 * Das Hintergrund Bild
 *
 */
public class BackgroundView extends ImageView implements GameObjectView {
	Board board;
	
	/**
	 * Konstruktor
	 * @param board Das Board
	 */
	public BackgroundView(Board board) {
		this.board = board;
		StaticImageHelper.loadImageView(Config.getString("bgImage"), this);
	}
	
	
	@Override
	public ModelVisitor getGameObject() {
		return board;
	}

}
