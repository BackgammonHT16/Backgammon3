package bg.backgammon3.view;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.Dice;
import bg.backgammon3.model.Dices;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.view.helper.ImageHelper;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DiceView extends Group implements GameObjectView {
	private Image[] image;
	private Image[] imageUsed;
	private ImageView[] imageView;
	//private Group box;
	private Dices dices;
	
	public DiceView(Dices dices)
	{
		this.dices = dices;
		//box = new Group();
		image = new Image[7];
		imageUsed = new Image[7];
		for(int i = 0; i < 7; i++)
		{
			image[i] = new Image("file:" + Config.getString("diceImage") + i + ".png");
			imageUsed[i] = new Image("file:" + Config.getString("diceImageUsed") + i + ".png");
		}
		setTranslateX(Config.getInteger("dicePositionX"));
		setTranslateY(Config.getInteger("dicePositionY"));
		// MouseClick wird auch auf transperentem Hintergrund ausgelöst.
		setPickOnBounds(true);
		setMouseTransparent(false);
		
		imageView = new ImageView[4];
		for(int i = 0; i < 4; i++)
		{
			imageView[i] = new ImageHelper(imageUsed[0], Config.getInteger("diceWidth"), false);
			imageView[i].setTranslateX((double)i* Config.getInteger("diceWidth"));
		}
		update();
		imageView[0].setImage(image[1]);
		getChildren().addAll(imageView);
		setPickOnBounds(true);
		setMouseTransparent(false);
	}
	
	public void update() {
		for(int i = 0; i < 4; i++) {
			Dice dice = dices.getDice(i);
			if(dice.getIsActive()) {
				if(dice.getIsUsed()) {
					imageView[i].setImage(imageUsed[dice.getValue()]);
				} else {
					imageView[i].setImage(image[dice.getValue()]);
				}
			} else {
				imageView[i].setImage(image[0]);
			}
		}
	}

	@Override
	public GameObject getGameObject() {
		return dices;
	}

	public void singleDiceWasRolled() {
		update();
		// TODO Animation einfügen
	}
	
	public void diceWasRolled() {
		update();
		// TODO Animation einfügen
	}

	public void diceWasUsed() {
		update();
	}
	
}
