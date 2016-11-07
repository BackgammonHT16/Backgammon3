/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.view.helper.StaticImageHelper;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author philipp
 *
 */
public class MenuButtonView extends ImageView implements GameObjectView {
	private MenuButton menuButton;
	private Text menu;
	public MenuButtonView(MenuButton menuButton,Pane root)
	{
		menu = new Text();
		menu.setTranslateX(Config.getInteger("menuButtonPositionX"));
		menu.setTranslateY(Config.getInteger("menuButtonPositionY"));
		menu.setFont(Font.font(Config.getString("Font"),Config.getInteger("timerSize")));
		menu.setText(Config.getString("menuText"));
		root.getChildren().add(menu);
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
