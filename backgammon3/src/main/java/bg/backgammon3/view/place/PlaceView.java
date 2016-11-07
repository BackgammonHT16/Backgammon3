/**
 * 
 */
package bg.backgammon3.view.place;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.*;
import bg.backgammon3.view.CheckerView;
import bg.backgammon3.view.GameObjectView;
import bg.backgammon3.view.helper.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author philipp
 *
 */
public abstract class PlaceView extends ImageView implements GameObjectView {
	private Logger logger = LogManager.getLogger(PlaceView.class);
	protected Place place;
	protected Image normalImage;
	protected ArrayList<Image> startImages = new ArrayList<Image>();
	protected ArrayList<Image> endImages = new ArrayList<Image>();

	ArrayList<CheckerView> checkers = new ArrayList<CheckerView>();

	public PlaceView(Place place) {
		this.place = place;
	}

	public void initPlaceView() {
		for (int i = 0; i < place.getNumberOfCheckers(); i++) {
			checkers.add(new CheckerView(this));
		}
	}

	@Override
	public GameObject getGameObject() {
		return place;
	}

	public ArrayList<CheckerView> getCheckers() {
		return checkers;
	}

	public Position getNewCheckerPosition() {

		double x0 = 1.0 * Config.getInteger("checkerWidth") * (int) ((checkers.size() - 0) % 3 - 1);
		double y0 = 1.0 * Config.getInteger("checkerWidth") * (int) ((checkers.size() - 0) / 3 + 1);

		double angle = -Math.toRadians(getRotate());

		double x1 = Math.cos(angle) * x0 + Math.sin(angle) * y0;
		double y1 = -Math.sin(angle) * x0 + Math.cos(angle) * y0;

		double x = getTranslateX() + x1;
		double y = getTranslateY() + y1;

		return new Position(x, y, getRotate());
	}

	public Position addChecker(CheckerView checker) {
		Position p = getNewCheckerPosition();
		checkers.add(checker);
		consistencyCheck();
		return p;
	}

	public void removeChecker(CheckerView checker) {
		checkers.remove(checker);
	}

	public Place getPlace() {
		return place;
	}

	public int moveCheckerTo(PlaceView placeView) {
		return checkers.get(checkers.size() - 1).moveTo(placeView);
	}

	public void update(boolean showHighlights) {
		if (place.getState() instanceof StartPoint && showHighlights) {
			setImage(startImages.get(((StartPoint) place.getState()).getPlayerId()));
			this.toFront();
		} else if (place.getState() instanceof EndPoint && showHighlights) {
			setImage(endImages.get(((EndPoint) place.getState()).getPlayerId()));
			this.toFront();
		} else {
			setImage(normalImage);
		}
		consistencyCheck();
	}

	public void consistencyCheck() {
		if (checkers.size() != place.getNumberOfCheckers()) {
			logger.error("[ROUTE] Checker zahl auf PlaceView " + place.getId() + " ist ungleich der auf Place ("
					+ checkers.size() + " zu " + place.getNumberOfCheckers() + ")");
		}
		int i = 0;
		for (CheckerView c : checkers) {
			if (c.getPlayerId() != place.getPlayerId()) {
				i++;
			}
		}
		if (i > 0) {
			logger.error("[ROUTE] Auf PlaceView " + place.getId() + ((i > 1) ? " sind " : " ist ") + i + " Checker von Spieler "
					+ (1 - place.getPlayerId()) + " und " + (checkers.size() - i) + " Checker von Spieler "
					+ place.getPlayerId());
		}		
		if (i > 0 && checkers.size() > 1) {
			logger.error("[ROUTE][ERROR] Auf PlaceView " + place.getId() + ((i > 1) ? " sind " : " ist ") + i + " Checker von Spieler "
					+ (1 - place.getPlayerId()) + " und " + (checkers.size() - i) + " Checker von Spieler "
					+ place.getPlayerId());
		}
	}

}
