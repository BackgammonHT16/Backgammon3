/**
 * 
 */
package bg.backgammon3.view;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.action.*;
import bg.backgammon3.model.place.*;
import bg.backgammon3.view.place.*;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author philipp
 *
 */
public class BoardView extends ImageView implements GameObjectView {
	private Logger logger = LogManager.getLogger(BoardView.class);
	private Board board;
	private ArrayList<Node> controls = new ArrayList<Node>();
	private ArrayList<PlaceView> places = new ArrayList<PlaceView>();
	
	private DiceView diceView;
	
	private Text text;

	public ArrayList<Node> getControls() {
		return controls;
	}

	public BoardView(Board board, Pane root)
	{
		this.board = board;
		initBoardView(root);
	}
	
	/**
	 * Initialisiert die Komponenten der BoardView.
	 */
	public void initBoardView(Pane root) {
		// Stage initialisieren
		
		// Hintergrund
		BackgroundView bgv = new BackgroundView(board);
		root.getChildren().add(bgv);
		controls.add(bgv);
		
		// Places und Checker
		initPlaces(root);
		
		// Würfel
		diceView = new DiceView(board.getDices());
		root.getChildren().add(diceView);
		controls.add(diceView);
		
		// Menu Button
		MenuButtonView menuButtonView = new MenuButtonView(new MenuButton());
		root.getChildren().add(menuButtonView);
		controls.add(menuButtonView);
		
		// Text Anzeige
		initText(root);
		
		
		// Timer Anzeige
		
		
	}
	
	public void initText(Pane root) {
		text = new Text();
		text.setTranslateX(Config.getInteger("textX"));
		text.setTranslateY(Config.getInteger("textY"));
		text.setFont(new Font(Config.getInteger("textSize")));	
		root.getChildren().add(text);
	}
	
	public void initPlaces(Pane root) {
		logger.info("PlaceView wird Initialisiert.");
		for(int i = 0; i < board.getPlaces().size(); i++) {
			Place place = board.getPlaces().get(i);
			PlaceView placeView;
			if(place instanceof Point) {
				placeView = new PointView(place);
			} else if (place instanceof Bar) {
				placeView = new BarView(place);
			} else if (place instanceof Goal) {
				placeView = new GoalView(place);
			} else {
				placeView = new PointView(place);
				logger.warn("Unerwarteter Place Typ");
			}
			places.add(placeView);
			controls.add(placeView);
			root.getChildren().add(placeView);
			for(CheckerView c : placeView.getCheckers()) {
				root.getChildren().add(c.getImage());
			}
		}
	}
	
	/**
	 * Gibt das zum Modell zugehörige Objekt zurück. Wird gebraucht damit das Model weiß auf was geklickt wurde.
	 */
	@Override
	public GameObject getGameObject() {
		return board;
	}

	/**
	 * Schließt das Spiel. Wird zum Beenden benötigt.
	 */
	public void hide() {
		
		
	}
	
	/**
	 * Geht alle Places durch und setzt die Bilder entsprechend.
	 * TODO Überlegen ob das hier überhaupt gebraucht wird.
	 */
	public void update() {
		for(PlaceView p : places) {
			p.update();
		}
	}
	
	/**
	 * Führt die geforderte Aktion aus.
	 * @param action Die durchzuführende Aktion
	 */
	public void update(Action action) {
		if(action instanceof DisplayMessage) {
			text.setText(((DisplayMessage) action).getMessage());
		} else if (action instanceof SelectStartPlace) {
			update();
		} else if (action instanceof SelectEndPlace) {
			update();
		} else if (action instanceof ShowRoute) {
			update();
		} else if (action instanceof SingleDiceWasRolled) {
			diceView.singleDiceWasRolled();
		} else if (action instanceof DiceWasRolled) {
			diceView.diceWasRolled();
		} else if (action instanceof DiceWasUsed) {
			diceView.diceWasUsed();
		} else if (action instanceof MoveChecker) {
			moveChecker(((MoveChecker) action).getStartId(), ((MoveChecker) action).getEndId());
		}
	}
	
	/**
	 * Verschiebt einen Checker von startId nach endId
	 * @param startId Start Platz.
	 * @param endId End Platz.
	 */
	public void moveChecker(Integer startId, Integer endId) {
		logger.info("MoveChecker von " + startId + " nach " + endId + ".");
		places.get(startId).moveCheckerTo(places.get(endId));
	}

}
