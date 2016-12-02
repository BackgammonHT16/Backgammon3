/**
 * 
 */
package bg.backgammon3.view.place;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.*;
import bg.backgammon3.view.CheckerView;
import bg.backgammon3.view.GameObjectView;
import bg.backgammon3.view.helper.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * PlaceView
 *
 */
public abstract class PlaceView extends ImageView implements GameObjectView {
	private Logger logger = LogManager.getLogger(PlaceView.class);
	protected Place place;
	protected Image normalImage;
	protected ArrayList<Image> startImages = new ArrayList<Image>();
	protected ArrayList<Image> endImages = new ArrayList<Image>();
	protected ArrayList<Image> hoverStartImages = new ArrayList<Image>();
	protected ArrayList<Image> hoverEndImages = new ArrayList<Image>();
	protected Image tempImage;
	protected PointState tempPointState;
	protected int tempPlayerId;

	ArrayList<CheckerView> checkers = new ArrayList<CheckerView>();

	/**
	 * Konstruktor
	 * @param place der platz im Model
 	 */
	public PlaceView(Place place) {
		this.place = place;
		tempPointState = place.getState();
		tempPlayerId = place.getPlayerId();
	}

	/**
	 * Initialisiert die placeView
	 */
	public void initPlaceView() {
		for (int i = 0; i < place.getNumberOfCheckers(); i++) {
			checkers.add(new CheckerView(this));
		}
		tempImage = this.getImage();
		
		this.setOnMouseEntered(e -> {
			mouseEntered();
		});
		this.setOnMouseExited(e -> {
			if ((tempPlayerId == 0 && Config.getInteger("player0Type") == 1)
					|| (tempPlayerId == 1 && Config.getInteger("player1Type") == 1) || tempPointState.getSelected()) {
				return;
			}
			this.setImage(tempImage);
		});
		this.setOnMouseMoved(e -> {
			mouseEntered();
		});
	}

	/**
	 * Wird aufgerufen wenn die maus drübergeht
	 */
	protected void mouseEntered() {
		if ((tempPlayerId == 0 && Config.getInteger("player0Type") == 1)
				|| (tempPlayerId == 1 && Config.getInteger("player1Type") == 1) || tempPointState.getSelected()) {
			return;
		}
		if (tempPointState instanceof StartPoint) {
			this.setImage(hoverStartImages.get(tempPlayerId));
		} else if (tempPointState instanceof EndPoint) {
			this.setImage(hoverEndImages.get(tempPlayerId));
		} else if (tempPointState instanceof NormalPoint) {
		}
	}
	
	@Override
	public ModelVisitor getGameObject() {
		return place;
	}

	/**
	 * Gibt die checker zurück
	 * @return die Checker
	 */
	public ArrayList<CheckerView> getCheckers() {
		return checkers;
	}

	/** 
	 * ermittelt die nächste checker position
	 * @return die Position
	 */
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

	/**
	 * Fügt checker hinzu
	 * @param checker der Checker
	 * @return die Position
	 */
	public Position addChecker(CheckerView checker) {
		Position p = getNewCheckerPosition();
		checkers.add(checker);
		consistencyCheck();
		return p;
	}

	/**
	 * entfernt angegebenen 
	 * @param checker der Checker
	 */
	public void removeChecker(CheckerView checker) {
		checkers.remove(checker);
	}

	/**
	 * der Platz im model
	 * @return der ModelPlatz
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * Bewegt den Checker
	 * @param placeView die PlaceView des ziels
	 * @return
	 */
	public int moveCheckerTo(PlaceView placeView) {
		return checkers.get(checkers.size() - 1).moveTo(placeView);
	}

	/**
	 * aktualisiert den place
	 * @param showHighlights markiert ihn oder auch nicht
	 */
	public void update(boolean showHighlights) {
		if (place.getState() instanceof StartPoint && showHighlights) {
			if(place.getState().getSelected()) {
				setImage(hoverStartImages.get(((StartPoint) place.getState()).getPlayerId()));
			} else {
				setImage(startImages.get(((StartPoint) place.getState()).getPlayerId()));
			}
			this.toFront();
		} else if (place.getState() instanceof EndPoint && showHighlights) {
			if(place.getState().getSelected()) {
				setImage(endImages.get(((EndPoint) place.getState()).getPlayerId()));
			} else {
				setImage(hoverEndImages.get(((EndPoint) place.getState()).getPlayerId()));
			}
			this.toFront();
		} else {
			setImage(normalImage);
		}
		tempImage = this.getImage();
		tempPointState = place.getState();
		tempPlayerId = place.getState().getPlayerId();
		consistencyCheck();
	}


	/**
	 * Konsistenz prüfung
	 */
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
			logger.error("[ROUTE] Auf PlaceView " + place.getId() + ((i > 1) ? " sind " : " ist ") + i
					+ " Checker von Spieler " + (1 - place.getPlayerId()) + " und " + (checkers.size() - i)
					+ " Checker von Spieler " + place.getPlayerId());
		}
		if (i > 0 && checkers.size() > 1) {
			logger.error("[ROUTE][INFO] Auf PlaceView " + place.getId() + ((i > 1) ? " sind " : " ist ") + i
					+ " Checker von Spieler " + (1 - place.getPlayerId()) + " und " + (checkers.size() - i)
					+ " Checker von Spieler " + place.getPlayerId());
		}
	}

}
