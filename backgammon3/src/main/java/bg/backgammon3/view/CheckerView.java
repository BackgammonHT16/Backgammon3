/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.config.Config;
import bg.backgammon3.view.helper.*;
import bg.backgammon3.view.place.PlaceView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * @author philipp
 *
 */
public class CheckerView {
	private PlaceView placeView;
	private ImageView image;

	public CheckerView(PlaceView placeView) {
		this.placeView = placeView;
		initCheckerView();
	}

	private void initCheckerView() {
		Position position = placeView.getNewCheckerPosition();
		image = new ImageHelper(Config.getString("checker" + placeView.getPlace().getPlayerId() + "Image"),
				Config.getInteger("checkerWidth"), false, position.x, position.y);
	}

	public ImageView getImage() {
		return image;
	}

	public void moveTo(PlaceView placeView) {
		this.placeView.removeChecker(this);
		this.placeView = placeView;
		image.toFront();
		startAnimationTo(placeView.addChecker(this));
	}
	
	private void startAnimationTo(Position p)
	{

		final Timeline t = new Timeline();
		t.getKeyFrames().addAll(
	            new KeyFrame(Duration.ZERO, 
	                new KeyValue(image.translateXProperty(), image.getTranslateX()),
	                new KeyValue(image.translateYProperty(), image.getTranslateY())
	            ),
	            new KeyFrame(new Duration(Config.getInteger("checkerMoveTime")),
	                new KeyValue(image.translateXProperty(), p.x),
	                new KeyValue(image.translateYProperty(), p.y)
	            )
	        );
		t.play();
	}

}
