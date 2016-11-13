/**
 * 
 */
package bg.backgammon3.controller;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.model.action.*;
import bg.backgammon3.view.*;
import bg.backgammon3.view.stage.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * @author philipp
 *
 */
public class GameController extends GameControllerElement implements EventHandler<Event>, ActionInterface {
	private Logger logger = LogManager.getLogger(GameController.class);

	private boolean busy = false;
	private AtomicBoolean atomicBusy = new AtomicBoolean(false);
	private Game game;
	private AppStage appStage;
	private AppStage gameStage;

	public GameController() {
		initGameController();
	}

	/**
	 * Erstellt Modell sowie Stage Klasse
	 */
	private void initGameController() {
		// Modell erstellen und initialisieren
		game = new Game();

		handleAllActions();
	}

	/**
	 * Verarbeitet alle durch Modell angefallenen Aktionen
	 */
	private void handleAllActions() {
		logger.info("Auf dem Aktions Stack: " + game.getAction());

		// GUI wird verwendet
		if (Config.getInteger("graphics") == 1) {

			Action action = game.getAction();
			/*
			 * if(game.getAction() instanceof ShowMenu) {
			 * handleAction(game.popAction()); } else if (game.getAction()
			 * instanceof StartGame) { handleAction(game.popAction()); } else if
			 * (game.getAction() instanceof ContinueGame) {
			 * handleAction(game.popAction()); } else if (game.getAction()
			 * instanceof Quit) { handleAction(game.popAction()); } else if
			 * (game.getAction() instanceof CloseGame) {
			 * handleAction(game.popAction()); } else if (game.getAction()
			 * instanceof DisableContinueButton) {
			 * handleAction(game.popAction()); }
			 */

			if (action == null) {
				return;
			}

			if (action.visitImmediately()) {
				game.popAction();
				action.visit(this);
				handleAllActions();
			} else if (atomicBusy.compareAndSet(false, true)) {
				game.checkActionForAI(action);
				game.popAction();
				int time = action.visit(this);
				logger.info("Warte Zeit für diese Aktion: " + time);
				Timeline timeline = new Timeline();
				timeline.getKeyFrames().add(new KeyFrame(new Duration((double) time + 1), e -> {
					atomicBusy.set(false);
					handleAllActions();
				}));
				timeline.play();
			}
		}
		// GUI wird nicht verwendet
		else {
			while (game.getAction() != null) {
				game.popAction().visit(this);
			}
			// handleAllActions();
		}
	}

	/**
	 * Prüft ob durch das Modell die Anzeige einer neuen Stage notwendig wird.
	 * Ansonsten wird die Aktion an die AppStage durchgereicht.
	 * 
	 * @param action
	 *            Nächste durchzuführende Aktion
	 */
	private int handleAction(Action action) {
		game.checkActionForAI(action);

		// GUI wird verwendet
		if (Config.getInteger("graphics") == 1) {
			if (action instanceof ShowMenu) {
				initMenuView();
			} else if (action instanceof StartGame) {
				initGameView();
			} else if (action instanceof ContinueGame) {
				continueGame();
			} else if (action instanceof Quit) {
				quitGame();
			} else if (action instanceof CloseGame) {
				closeGame();
			} else if (action instanceof DisableContinueButton) {
				if (appStage != null) {
					appStage.accept(action);
					// appStage.update(action);
				}
			} else if (action instanceof UpdateModel) {
				UpdateAI updateAI = new UpdateAI();
				updateAI.setBusy(false);
				game.accept(updateAI);
				// game.handle(new UpdateAI(), false);
			} else {
				return gameStage.accept(action);
				// return gameStage.update(action);
			}
		}
		// GUI wird nicht verwendet
		else {
			if (action instanceof UpdateModel) {
				UpdateAI updateAI = new UpdateAI();
				updateAI.setBusy(false);
				game.accept(updateAI);
				// game.handle(new UpdateAI(), false);
			}
		}
		return 0;
	}

	public void closeGame() {
		if (appStage != null) {
			// Ein eventuell noch vorhandenes Spiel wird beendet.
			if (appStage instanceof MenuStage) {
				AppStage gameStage = ((MenuStage) appStage).getGameStage();
				if (gameStage != null) {
					gameStage.hide();
				}
			}
		}
	}

	public void quitGame() {
		logger.info("Spiel wird beendet.");
		// Das Spiel wird beendet.
		if (appStage instanceof MenuStage) {
			AppStage gameStage = ((MenuStage) appStage).getGameStage();
			if (gameStage != null) {
				gameStage.hide();
			}
		} else {
			logger.warn("Unerwartete Klasse in appStage abgespeichert. MenuStage erwartet.");
		}
		appStage.hide();
	}

	public void continueGame() {
		logger.info("Spiel wird weiter ausgeführt.");
		// Das Spiel wird weiter ausgeführt.
		if (appStage instanceof MenuStage) {
			MenuStage menuStage = (MenuStage) appStage;
			appStage = menuStage.getGameStage();
			menuStage.hide();
			initControls();
		} else {
			logger.warn("Unerwartete Klasse in appStage abgespeichert. MenuStage erwartet.");
		}
	}

	public void initMenuView() {
		logger.info("Menu wird angezeigt.");
		appStage = new MenuStage(game, appStage);
		initControls();
	}

	public void initGameView() {
		logger.info("Spielfeld wird angezeigt.");
		// Falls Menu noch angezeigt wird, menu verstecken
		if (appStage != null) {
			// Ein eventuell noch vorhandenes Spiel wird beendet.
			if (appStage instanceof MenuStage) {
				AppStage gameStage = ((MenuStage) appStage).getGameStage();
				if (gameStage != null) {
					gameStage.hide();
				}
			}
			appStage.hide();
		}
		appStage = new GameStage(game);
		game.getBoard().getTimer().setActionInterface(this);
		gameStage = appStage;
		initControls();
	}

	/**
	 * Bindet die Kontrollen einer neuen Stage an diese Klasse
	 */
	private void initControls() {
		for (Node node : appStage.getControls()) {
			node.setOnMouseClicked(this);
		}
		appStage.getStage().setOnCloseRequest(e -> {
			if (appStage instanceof MenuStage) {
				GameObject gameObject = new ContinueButton(((MenuStage) appStage).getMenu());
				gameObject.setBusy(busy);
				game.accept(gameObject);

				// game.handle(new ContinueButton(((MenuStage)
				// appStage).getMenu()), busy);
				appStage.hide();
			}
		});
	}

	/**
	 * Behandelt die in der View entstandenen Eingaben.
	 * 
	 * @param event
	 */
	public void handle(Event event) {
		if (event.getSource() instanceof GameObjectView) {
			GameObject gameObject = ((GameObjectView) event.getSource()).getGameObject();
			gameObject.setBusy(busy);
			game.accept(gameObject);
			// game.handle(((GameObjectView) event.getSource()).getGameObject(),
			// busy);
		} else {
			logger.error("Unbekannte Event Quelle.");
		}

		// Stack ausgeben
		logger.info("Stack Dump für " + event.getSource() + ":");
		logger.info(game.stackToString());
		logger.info("Ende Stack Dump");
		// Anschließend Änderungen durch das Modell verarbeiten
		handleAllActions();
	}

	@Override
	public void addActionAtBeginn(Action action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addActionAtEnd(Action action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void timeOver() {
		GameObject gameObject = game.getBoard().getTimer();
		gameObject.setBusy(false);
		game.accept(gameObject);

		// game.handle(game.getBoard().getTimer(), false);
		handleAllActions();
	}

	@Override
	public int accept(GameObject gameObject) {
		gameObject.visit(this);
		return 0;
	}

	@Override
	public int nextAccept(GameObject gameObject) {
		game.accept(gameObject);
		return 0;
	}

	@Override
	public int nextAccept(Action action) {
		if (Config.getInteger("graphics") == 1) {
			return appStage.accept(action);
		}
		return 0;
	}

	@Override
	public int accept(Action action) {
		action.visit(this);
		return 0;
	}

	@Override
	public boolean getBusy() {
		return atomicBusy.get();
	}
}
