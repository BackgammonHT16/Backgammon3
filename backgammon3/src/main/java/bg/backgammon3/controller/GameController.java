/**
 * 
 */
package bg.backgammon3.controller;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.view.*;
import bg.backgammon3.view.stage.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * 
 *
 */
public class GameController implements ModelElement, EventHandler<Event>, TimerInterface {
	private Logger logger = LogManager.getLogger(GameController.class);

	private boolean busy = false;
	private AtomicBoolean atomicBusy = new AtomicBoolean(false);
	private Game game;
	//private AppStage appStage;
	private MenuStage menuStage;
	private GameStage gameStage;

	public GameController() {
		initGameController();
	}

	/**
	 * Erstellt Modell sowie Stage Klasse
	 */
	private void initGameController() {
		// Modell erstellen und initialisieren
		game = new Game();
		
		gameStage = new GameStage(game);
		menuStage = new MenuStage(game, gameStage);

		initControls();
		//handleAllActions();
		updateUI();
	}
//
//	/**
//	 * Verarbeitet alle durch Modell angefallenen Aktionen
//	 */
//	private void handleAllActions() {
//		logger.info("Auf dem Aktions Stack: " + game.getAction());
//
//		// GUI wird verwendet
//		if (Config.getInteger("graphics") == 1) {
//
//			Action action = game.getAction();
//
//			if (action == null) {
//				return;
//			}
//
//			if (action.visitImmediately()) {
//				game.popAction();
//				action.visit(this);
//				handleAllActions();
//			} else if (atomicBusy.compareAndSet(false, true)) {
//				game.checkActionForAI(action);
//				game.popAction();
//				int time = action.visit(this);
//				logger.info("Warte Zeit für diese Aktion: " + time);
//				Timeline timeline = new Timeline();
//				timeline.getKeyFrames().add(new KeyFrame(new Duration((double) time + 1), e -> {
//					atomicBusy.set(false);
//					handleAllActions();
//				}));
//				timeline.play();
//			}
//		}
//		// GUI wird nicht verwendet
//		else {
//			while (game.getAction() != null) {
//				if(Config.getInteger("aiOptimizationFinished") == 1) {
//					Platform.exit();
//					return;
//				}
//				Action action = game.popAction();
//				game.checkActionForAI(action);
//				action.visit(this);
//			}
//			//handleAllActions();
//		}
//	}
//
//
//	public void closeGame() {
//		if (appStage != null) {
//			// Ein eventuell noch vorhandenes Spiel wird beendet.
//			if (appStage instanceof MenuStage) {
//				AppStage gameStage = ((MenuStage) appStage).getGameStage();
//				if (gameStage != null) {
//					gameStage.hide();
//				}
//			}
//		}
//	}
//
//	public void quitGame() {
//		logger.info("Spiel wird beendet.");
//		// Das Spiel wird beendet.
//		if (appStage instanceof MenuStage) {
//			AppStage gameStage = ((MenuStage) appStage).getGameStage();
//			if (gameStage != null) {
//				gameStage.hide();
//			}
//		} else {
//			logger.warn("Unerwartete Klasse in appStage abgespeichert. MenuStage erwartet.");
//		}
//		appStage.hide();
//	}
//
//	public void continueGame() {
//		logger.info("Spiel wird weiter ausgeführt.");
//		// Das Spiel wird weiter ausgeführt.
//		if (appStage instanceof MenuStage) {
//			MenuStage menuStage = (MenuStage) appStage;
//			appStage = menuStage.getGameStage();
//			menuStage.hide();
//			initControls();
//		} else {
//			logger.warn("Unerwartete Klasse in appStage abgespeichert. MenuStage erwartet.");
//		}
//	}
//
//	public void initMenuView() {
//		logger.info("Menu wird angezeigt.");
//		appStage = new MenuStage(game, appStage);
//		initControls();
//	}
//
//	public void initGameView() {
//		logger.info("Spielfeld wird angezeigt.");
//		// Falls Menu noch angezeigt wird, menu verstecken
//		if (appStage != null) {
//			// Ein eventuell noch vorhandenes Spiel wird beendet.
//			if (appStage instanceof MenuStage) {
//				AppStage gameStage = ((MenuStage) appStage).getGameStage();
//				if (gameStage != null) {
//					gameStage.hide();
//				}
//			}
//			appStage.hide();
//		}
//		appStage = new GameStage(game);
//		game.getBoard().getTimer().setActionInterface(this);
//		//gameStage = appStage;
//		initControls();
//	}

	/**
	 * Bindet die Kontrollen einer neuen Stage an diese Klasse
	 */
	private void initControls() {
		for (Node node : menuStage.getControls()) {
			node.setOnMouseClicked(this);
		}
		for (Node node : gameStage.getControls()) {
			node.setOnMouseClicked(this);
		}
		gameStage.getStage().setOnCloseRequest(e -> {
			menuStage.hide();
		});
	}

	/**
	 * Behandelt die in der View entstandenen Eingaben.
	 * 
	 * @param event Der zu bearbeitende Event
	 */
	public void handle(Event event) {
		Integer modelPlace = 0;
		if (event.getSource() instanceof GameObjectView) {
			ModelVisitor gameObject = ((GameObjectView) event.getSource()).getGameObject();
			gameObject.setBusy(busy);
			//modelPlace = this.accept(gameObject);
			modelPlace = game.accept(gameObject);
			
		} else {
			logger.error("Unbekannte Event Quelle.");
		}

		// Stack ausgeben
		logger.info("Stack Dump für " + event.getSource() + ":");
//		logger.info(game.stackToString());
		logger.info("Ende Stack Dump");
		// Anschließend Änderungen durch das Modell verarbeiten
		while(modelPlace != 0) {
			updateUI();
			modelPlace = game.checkActionForAI(modelPlace);
		}
		updateUI();
		
	}
	
	/**
	 * Aktualisiert das UI
	 */
	private void updateUI() {
		menuStage.update();
		if(gameStage.update()) {
			initControls();
		}
	}

	/**
	 * Zeit ist vorüber
	 */
	@Override
	public void timeOver() {
		ModelVisitor gameObject = game.getBoard().getTimer();
		gameObject.setBusy(false);
		game.accept(gameObject);

		//handleAllActions();
	}

	/**
	 * GameController wird besucht
	 */
	public int accept(ModelVisitor gameObject) {
		//return gameObject.visit(this);
		return game.accept(gameObject);
	}

	/**
	 * Model wird besucht
	 */
	public int nextAccept(ModelVisitor gameObject) {
		return game.accept(gameObject);
	}


	//@Override
	public boolean getBusy() {
		return atomicBusy.get();
	}
}
