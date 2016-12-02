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
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Die View eines Checkers
 *
 */
public class CheckerView {
	private Logger logger = LogManager.getLogger(CheckerView.class);
	private PlaceView placeView;
	private ImageView image;

	// Wird nur für die Konsistenz Überprüfung in PlaceView benötigt
	private Integer playerId;

	/** 
	 * Konstruktor
	 * @param placeView Der Startplatz
	 */
	public CheckerView(PlaceView placeView) {
		this.placeView = placeView;
		initCheckerView();
	}

	/**
	 * Initialisiert den Checker
	 */
	private void initCheckerView() {
		Position position = placeView.getNewCheckerPosition();
		playerId = placeView.getPlace().getPlayerId();
		image = new ImageHelper(Config.getString("checker" + placeView.getPlace().getPlayerId() + "Image"),
				Config.getInteger("checkerWidth"), false, position.x, position.y);
	}

	/**
	 * Das Bild des checkers
	 * @return Das Bild
	 */
	public ImageView getImage() {
		return image;
	}

	/**
	 * Bewegt den Checker zur neuen Placeview
	 * @param placeView die placeview
	 * @return Die zeit
	 */
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

	/**
	 * Beginnt die Animation
	 * @param p1 punkt 1
	 * @param a1 richtung 1
	 * @param p2 punkt 2
	 * @param a2 richtung 2
	 * @return Die Zeit
	 */
	private int startAnimationTo(MathVector p1, Double a1, MathVector p2, Double a2) {
		// Code für gerade Linie

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

	/**
	 * Gibt die Id des Spielers zurück
	 * @return die Id des spielers
	 */
	public Integer getPlayerId() {
		return playerId;
	}

}
