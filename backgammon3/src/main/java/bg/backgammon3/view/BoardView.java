/**
 * 
 */
package bg.backgammon3.view;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.action.*;
import bg.backgammon3.model.place.*;
import bg.backgammon3.view.helper.BackGroundHelper;
import bg.backgammon3.view.helper.StaticImageHelper;
import bg.backgammon3.view.place.*;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * 
 *
 */
public class BoardView extends BoardElement implements GameObjectView {
	private Logger logger = LogManager.getLogger(BoardView.class);
	private Board board;
	private ArrayList<Node> controls = new ArrayList<Node>();
	private ArrayList<PlaceView> places = new ArrayList<PlaceView>();
	
	private DiceView diceView;

	private Text text;
	
	private Text timer;
	private Pane root;
	
	private ImageView timerbg = new ImageView();
	private ImageView messagebg = new ImageView();

	public ArrayList<Node> getControls() {
		return controls;
	}

	public BoardView(Board board, Pane root)
	{
		//this.board = board;
		this.root = root;
		initBoardView(board);
	}
	
	/**
	 * Initialisiert die Komponenten der BoardView.
	 */
	public void initBoardView(Board board) {
		if(this.board == board) {
			return;
		}
		this.board = board;
		// Stage initialisieren
		
		// Hintergrund
		BackgroundView bgv = new BackgroundView(board);
		root.getChildren().add(bgv);
		controls.add(bgv);
		bgv.setFocusTraversable(true);
		BackGroundHelper.initBackground(root, bgv);
		
		
		// Places und Checker
		initPlaces(root);
		
		// Würfel
		diceView = new DiceView(board.getDices());
		root.getChildren().add(diceView);
		controls.add(diceView);
		
		// Menu Button
		MenuButtonView menuButtonView = new MenuButtonView(new MenuButton(),root);
		root.getChildren().add(menuButtonView);
		controls.add(menuButtonView);
		
		// Text Anzeige
		initText(root);
		
		
		// Timer Anzeige
		initTimer(root);
		
	}
	
	private void initText(Pane root) {
		StaticImageHelper.loadImageView(
				Config.getString("messageImage"),
				Config.getInteger("messageWidth"),
				false,
				Config.getInteger("textX"),
				Config.getInteger("textY"), messagebg);
		text = new Text();
		text.setTranslateX(Config.getInteger("textX"));
		text.setTranslateY(Config.getInteger("textY"));
		text.setFont(Font.font(Config.getString("Font"),Config.getInteger("textSize")));	
		root.getChildren().add(text);
		root.getChildren().add(messagebg);
	}

	private void initTimer(Pane root) {
		StaticImageHelper.loadImageView(
				Config.getString("timerImage"),
				Config.getInteger("timerWidth"),
				false,
				Config.getInteger("timerX"),
				Config.getInteger("timerY"), timerbg);
				
		timer = new Text();
		timer.setTranslateX(Config.getInteger("timerX"));
		timer.setTranslateY(Config.getInteger("timerY"));
		timer.setFont(Font.font (Config.getString("Font"),Config.getInteger("timerSize")));
		if(Config.getInteger("maximumTime") != -1) {
			timer.textProperty().bind(board.getTimer().getTime().asString());
		} else {
			timer.setVisible(false);
			timerbg.setVisible(false);
		}
		root.getChildren().add(timer);
		root.getChildren().add(timerbg);
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
	public ModelVisitor getGameObject() {
		return board;
	}

	/**
	 * Schließt das Spiel. Wird zum Beenden benötigt.
	 */
	public void hide() {
		
		
	}
	
	public boolean update() {
		update(true);
		return false;
	}
	
	/**
	 * Geht alle Places durch und setzt die Bilder entsprechend.
	 * TODO Überlegen ob das hier überhaupt gebraucht wird.
	 */
	public void update(boolean showHighlights) {
		for(PlaceView p : places) {
			p.update(showHighlights);
		}
		
		Move move = board.popMove();
		
		while(move != null) {
			moveChecker(move.getStartPlace(), move.getEndPlace());
			move = board.popMove();
		}
		
		diceView.update();
	}
	
	/**
	 * Führt die geforderte Aktion aus.
	 * @param action Die durchzuführende Aktion
	 */
/*	public int update(Action action) {
		if(action instanceof DisplayMessage) {
			text.setText(((DisplayMessage) action).getMessage());
			return ((DisplayMessage) action).getTime();
		} else if (action instanceof SelectStartPlace) {
			update();
		} else if (action instanceof SelectEndPlace) {
			update();
		} else if (action instanceof ShowRoute) {
			update();
		} else if (action instanceof ShowPlacesWithoutMarks) {
			update(false);
		} else if (action instanceof SingleDiceWasRolled) {
			return diceView.singleDiceWasRolled();
		} else if (action instanceof DiceWasRolled) {
			return diceView.diceWasRolled();
		} else if (action instanceof DiceWasUsed) {
			diceView.diceWasUsed();
		} else if (action instanceof MoveChecker) {
			return moveChecker(((MoveChecker) action).getStartId(), ((MoveChecker) action).getEndId());
		} else if (action instanceof Move2Checkers) {
			return Math.max(moveChecker(((Move2Checkers) action).getStartId1(), ((Move2Checkers) action).getEndId1()), 
					moveChecker(((Move2Checkers) action).getStartId2(), ((Move2Checkers) action).getEndId2()));
		} else if (action instanceof StartTimer) {
			timer.textProperty().bind(board.getTimer().acitvateTimer().asString());
		}
		return 0;
	}
	*/
	public void setMessage(String message) {
		text.setText(message);
	}
	
	public int singleDiceWasRolled() {
		return diceView.singleDiceWasRolled();
	}
	
	public int diceWasRolled() {
		return diceView.diceWasRolled();
	}
	
	public void diceWasUsed() {
		diceView.diceWasUsed();
	}
	
	public void startTimer() {
		//timer.textProperty().bind(board.getTimer().acitvateTimer().asString());
		timer.textProperty().bind(board.getTimer().getTime().asString());
	}
	
	/**
	 * Verschiebt einen Checker von startId nach endId
	 * @param startId Start Platz.
	 * @param endId End Platz.
	 */
	public int moveChecker(Integer startId, Integer endId) {
		logger.info("[ROUTE][LOGROUTE] Würfel: " + diceView);
		logger.info("[ROUTE][LOGROUTE] MoveChecker von Spieler " + places.get(endId).getPlace().getPlayerId() + " von Place " + startId + " nach " + endId + ".");
		return places.get(startId).moveCheckerTo(places.get(endId));
	}

	
	@Override
	public int accept(Action action) {
		return action.visit(this);
	}
	
	@Override
	public int nextAccept(Action action) {
		return 0;
	}

}
