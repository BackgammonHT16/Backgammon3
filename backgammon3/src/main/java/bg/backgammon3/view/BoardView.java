/**
 * 
 */
package bg.backgammon3.view;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.place.*;
import bg.backgammon3.view.helper.BackGroundHelper;
import bg.backgammon3.view.helper.StaticImageHelper;
import bg.backgammon3.view.place.*;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
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
 * BoardView Klasse. Stellt das Board dar.
 *
 */
public class BoardView implements GameObjectView {
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
	private AtomicBoolean atomicBusy = new AtomicBoolean(false);

	/**
	 * Gibt die Liste der Controls zurück
	 * @return Liste der Controls
	 */
	public ArrayList<Node> getControls() {
		return controls;
	}

	/**
	 * Konstruktor der BoardView
	 * @param board Model Board
	 * @param root Root der Scene
	 */
	public BoardView(Board board, Pane root)
	{
		//this.board = board;
		this.root = root;
		initBoardView(board);
	}
	
	/**
	 * Initialisiert die Komponenten der BoardView.
	 * @param board Model Board
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
	
	/**
	 * Stellt den Text dar
	 * @param root Pane auf den der Text gezeichnet werden soll.
	 */
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


	/**
	 * Stellt den Timer dar
	 * @param root Pane auf dem der Timer angezeigt wird.
	 */
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
	
	/**
	 * Stellt die Places dar.
	 * @param root Pane auf dem die Places angezeigt werden.
	 */
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
	 * @return Gibt das Board zurück.
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
	
	/**
	 * Aktualisiert die View
	 * @return Gibt false zurück
	 */
	public boolean update() {
		update(true);
		return false;
	}
	
	/**
	 * Geht alle Places durch und setzt die Bilder entsprechend.
	 * @param showHighlights zeigt die hervorgehobenen Places an
	 */
	public void update(boolean showHighlights) {
		for(PlaceView p : places) {
			p.update(showHighlights);
		}
		
		
		moveAll();
		
		diceView.update();
	}
	
	/**
	 * Bewegt alle Checker
	 */
	public void moveAll() {
		if (atomicBusy.compareAndSet(false, true)) {
			Move move = board.popMove();
			if(move == null) {
				atomicBusy.set(false);
				return;
			}
			double time = moveChecker(move.getStartPlace(), move.getEndPlace());
			logger.info("Zeit für bewegung: " + time);
			Timeline t = new Timeline();
			t.getKeyFrames().add(new KeyFrame(Duration.millis(time), e->{
				atomicBusy.set(false);
				moveAll();
			}));
			t.play();
		}
	}
	

	/**
	 * Verschiebt einen Checker von startId nach endId
	 * @param startId Start Platz.
	 * @param endId End Platz.
	 * @return Die zeit die zum bewegen benötigt wird
	 */
	public int moveChecker(Integer startId, Integer endId) {
		logger.info("[ROUTE][LOGROUTE] Würfel: " + diceView);
		logger.info("[ROUTE][LOGROUTE] MoveChecker von Spieler " + places.get(endId).getPlace().getPlayerId() + " von Place " + startId + " nach " + endId + ".");
		return places.get(startId).moveCheckerTo(places.get(endId));
	}

	
//	@Override
//	public int accept(Action action) {
//		return action.visit(this);
//	}
//	
//	@Override
//	public int nextAccept(Action action) {
//		return 0;
//	}

}
