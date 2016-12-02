package bg.backgammon3.view;

import java.util.Random;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.Dice;
import bg.backgammon3.model.Dices;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.view.helper.ImageHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
/**
 * Die DiceView
 *
 */
public class DiceView extends Group implements GameObjectView {
	private Image[] image;
	private Image[] imageUsed;
	private ImageView[] imageView;
	//private Group box;
	private Dices dices;
	
	/**
	 * Konstruktor
	 * @param dices die Dices
	 */
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
	
	/**
	 * Update der DiceView
	 */
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
		
		if(dices.getDice(0).getIsActive() == false) {
			imageView[0].setImage(image[1]);
		}
	}

	@Override
	public ModelVisitor getGameObject() {
		return dices;
	}

	/**
	 * Einzelner würfel wurde gerollt
	 * @return zeit
	 */
	public int singleDiceWasRolled() {
		int time = Config.getInteger("diceRollTime");
		Timeline timeline = new Timeline();
		imageView[2].setImage(image[0]);
		imageView[3].setImage(image[0]);
		timeline.getKeyFrames().add(new KeyFrame(new Duration((double)time), e -> {
			Random r = new Random();
			int diceToRole = 0;
			if(dices.getDice(1).getIsActive()) {
				diceToRole = 1;
			}
			imageView[diceToRole].setImage(image[r.nextInt(6) + 1]);
			}));
		timeline.setCycleCount(6);
		timeline.setOnFinished(e -> {update();});
		timeline.play();
		return time * 6;
	}
	
	/**
	 * Würfel wurde gerollt
	 * @return zeit
	 */
	public int diceWasRolled() {
		int time = Config.getInteger("diceRollTime");
		Timeline timeline = new Timeline();
		imageView[2].setImage(image[0]);
		imageView[3].setImage(image[0]);
		timeline.getKeyFrames().add(new KeyFrame(new Duration((double)time), e -> {
			Random r = new Random();
			imageView[0].setImage(image[r.nextInt(6) + 1]);
			imageView[1].setImage(image[r.nextInt(6) + 1]);
			}));
		timeline.setCycleCount(6);
		timeline.setOnFinished(e -> {update();});
		timeline.play();
		return time * 6;
	}

	/**
	 * Die Würfel wurden benutzt
	 */
	public void diceWasUsed() {
		update();
	}

	@Override
	public String toString() {
		return dices.toString();
	}
}
