/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.view.helper.StaticImageHelper;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * @author philipp
 *
 */
public class MenuButtonView extends ImageView implements GameObjectView {
	private MenuButton menuButton;
	
	public MenuButtonView(MenuButton menuButton)
	{
		this.menuButton = menuButton;
		StaticImageHelper.loadImageView(
				Config.getString("menuButtonImage"),
				Config.getInteger("menuButtonWidth"),
				true,
				Config.getInteger("menuButtonPositionX"),
				Config.getInteger("menuButtonPositionY"), this);

		// MouseClick wird auch auf transperentem Hintergrund ausgelöst.
		setPickOnBounds(true);
	}
	
	@Override
	public GameObject getGameObject() {
		return menuButton;
	}
}
