/**
 * 
 */
package bg.backgammon3.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private Logger logger = LogManager.getLogger(CheckerView.class);
	private PlaceView placeView;
	private ImageView image;
	
	// Wird nur für die Konsistenz Überprüfung in PlaceView benötigt
	private Integer playerId;

	public CheckerView(PlaceView placeView) {
		this.placeView = placeView;
		initCheckerView();
	}

	private void initCheckerView() {
		Position position = placeView.getNewCheckerPosition();
		playerId = placeView.getPlace().getPlayerId();
		image = new ImageHelper(Config.getString("checker" + placeView.getPlace().getPlayerId() + "Image"),
				Config.getInteger("checkerWidth"), false, position.x, position.y);
	}

	public ImageView getImage() {
		return image;
	}

	public int moveTo(PlaceView placeView) {
		this.placeView.removeChecker(this);
		this.placeView = placeView;
		image.toFront();
		return  startAnimationTo(placeView.addChecker(this));
	}
	
	private int startAnimationTo(Position p)
	{
		double dx = image.getTranslateX() - p.x;
		double dy = image.getTranslateY() - p.y;
		double distance = Math.sqrt(dx*dx + dy*dy);
		int time =  (int) ((distance / (double) Config.getInteger("checkerMoveSpeed")) * 1000);
		logger.info("Checker wird über " + distance + " bewegt in der Zeit " + time);
		
		final Timeline t = new Timeline();
		t.getKeyFrames().addAll(
	            new KeyFrame(Duration.ZERO, 
	                new KeyValue(image.translateXProperty(), image.getTranslateX()),
	                new KeyValue(image.translateYProperty(), image.getTranslateY())
	            ),
	            new KeyFrame(new Duration(time),
	                new KeyValue(image.translateXProperty(), p.x),
	                new KeyValue(image.translateYProperty(), p.y)
	            )
	        );
		t.play();
		return time;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}

}
