/**
 * 
 */
package bg.backgammon3.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.place.Bar;
import bg.backgammon3.model.place.Goal;
import bg.backgammon3.view.helper.*;
import bg.backgammon3.view.place.PlaceView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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
		MathVector p1 = new MathVector(image.translateXProperty().get(), image.translateYProperty().get());
		Double a1 = this.placeView.rotateProperty().get();
		Position newPos = placeView.addChecker(this);
		MathVector p2 = new MathVector(newPos.x, newPos.y);
		Double a2 = placeView.rotateProperty().get();
		if(this.placeView.getGameObject() instanceof Bar) {
			if(p2.sub(p1).getY() > 0) {
				a1 += 180;
			}
		}
		if(placeView.getGameObject() instanceof Bar) {
			if(p1.sub(p2).getY() > 0) {
				a2 += 180;
			}
		}
		if(placeView.getGameObject() instanceof Goal) {
			a2 += 180;
		}
		this.placeView.removeChecker(this);
		this.placeView = placeView;
		image.toFront();
		return startAnimationTo(p1, a1, p2, a2);
	}

	private int startAnimationTo(MathVector p1, Double a1, MathVector p2, Double a2) {
/*		double dx = image.getTranslateX() - p.x;
		double dy = image.getTranslateY() - p.y;
		double distance = Math.sqrt(dx * dx + dy * dy);
		int time = (int) ((distance / (double) Config.getInteger("checkerMoveSpeed")) * 1000);
		logger.info("Checker wird über " + distance + " bewegt in der Zeit " + time);

		final Timeline t = new Timeline();
		t.getKeyFrames()
				.addAll(new KeyFrame(Duration.ZERO, new KeyValue(image.translateXProperty(), image.getTranslateX()),
						new KeyValue(image.translateYProperty(), image.getTranslateY())),
						new KeyFrame(new Duration(time), new KeyValue(image.translateXProperty(), p.x),
								new KeyValue(image.translateYProperty(), p.y)));
		t.play();

		MathVector p1 = new MathVector(image.translateXProperty().get(), image.translateYProperty().get());
		MathVector p2 = new MathVector(p.x, p.y);
		Double x1 = image.translateXProperty().get();
		Double y1 = image.translateYProperty().get();
		Double x2 = p.x;
		Double y2 = p.y;
		Double vx = x2 - x1;
		Double vy = y2 - y1;
		Double rx = vx/Math.sqrt(vx*vx+vy*vy);
		Double ry = vy/Math.sqrt(vx*vx+vy*vy);
		Double controlx1 = x2 - x1;
		Double controly1;
		Double controlx2;
		Double controly2;*/

		MathVector correction = new MathVector(image.getFitWidth() / 2, image.getFitWidth() / 2);
		p1 = p1.add(correction);
		p2 = p2.add(correction);
		Double alpha = 0.25;
		MathVector r1 = new MathVector(0, -1);
		r1 = (r1.turn(a1)).scale(p1.sub(p2).length() * alpha);
		r1 = p1.add(r1);
		MathVector r2 = new MathVector(0, -1);
		r2 = (r2.turn(a2)).scale(p1.sub(p2).length() * alpha);
		r2 = p2.add(r2);

		
		// Abschätzung der Länge der Kurve nach d = d + a*a/(a+d)
		Double distance = p2.sub(p1).length();
		Double distanceCorrection = 100d;
		distance += distanceCorrection*distanceCorrection/(distanceCorrection + distance);
		int time = (int) ((distance / (double) Config.getInteger("checkerMoveSpeed")) * 1000);
		
		Path path = new Path();
		path.getElements().add(new MoveTo(p1.getX(), p1.getY()));
		path.getElements().add(new CubicCurveTo(r1.getX(), r1.getY(), r2.getX(), r2.getY(), p2.getX(), p2.getY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(time));
		pathTransition.setNode(image);
		pathTransition.setPath(path);
		//pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(1);
		pathTransition.setAutoReverse(false);

		pathTransition.play();

		return time;
	}

	public Integer getPlayerId() {
		return playerId;
	}

}
