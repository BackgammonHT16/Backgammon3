/**
 * 
 */
package bg.backgammon3.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class GameController implements EventHandler<Event> {
	private Logger logger = LogManager.getLogger(GameController.class);

	private boolean busy = false;
	private Game game;
	private AppStage appStage;

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
		if(game.getAction() == null) {
			busy = false;
			return;
		} else {
			busy = true;
			logger.info("Warte Zeit für diese Aktion: " + game.getAction().getTime());
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(new Duration((double)game.getAction().getTime() + 1), e -> handleAllActions()));
			handleAction(game.popAction());
			timeline.play();
		}
		//while (game.getAction() != null) {
		//	handleAction(game.popAction());
		//}
	}

	/**
	 * Prüft ob durch das Modell die Anzeige einer neuen Stage notwendig wird.
	 * Ansonsten wird die Aktion an die AppStage durchgereicht.
	 * 
	 * @param action
	 *            Nächste durchzuführende Aktion
	 */
	private void handleAction(Action action) {
		game.checkActionForAI(action);
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
		} else if (action instanceof UpdateModel) {
			game.handle(new UpdateAI(), false);
		} else {
			appStage.update(action);
		}
	}
	
	private void closeGame() {
		if(appStage != null) {
			// Ein eventuell noch vorhandenes Spiel wird beendet.
			if (appStage instanceof MenuStage) {
				AppStage gameStage = ((MenuStage) appStage).getGameStage();
				if(gameStage != null)
				{
					gameStage.hide();
				}
			}
		}
	}
	
	private void quitGame()
	{
		logger.info("Spiel wird beendet.");
		// Das Spiel wird beendet.
		if (appStage instanceof MenuStage) {
			AppStage gameStage = ((MenuStage) appStage).getGameStage();
			if(gameStage != null)
			{
				gameStage.hide();
			}
		} else {
			logger.warn("Unerwartete Klasse in appStage abgespeichert. MenuStage erwartet.");
		}
		appStage.hide();
	}

	private void continueGame() {
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

	private void initMenuView() {
		logger.info("Menu wird angezeigt.");
		appStage = new MenuStage(game, appStage);
		initControls();
	}

	private void initGameView() {
		logger.info("Spielfeld wird angezeigt.");
		// Falls Menu noch angezeigt wird, menu verstecken
		if(appStage != null) {
			// Ein eventuell noch vorhandenes Spiel wird beendet.
			if (appStage instanceof MenuStage) {
				AppStage gameStage = ((MenuStage) appStage).getGameStage();
				if(gameStage != null)
				{
					gameStage.hide();
				}
			}
			appStage.hide();
		}
		appStage = new GameStage(game);
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
			if(appStage instanceof MenuStage) {
				game.handle(new ContinueButton(((MenuStage) appStage).getMenu()), busy);
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
			game.handle(((GameObjectView) event.getSource()).getGameObject(), busy);
		} else {
			logger.error("Unbekannte Event Quelle.");
		}

		// Anschließend Änderungen durch das Modell verarbeiten
		//if(!busy) {
			handleAllActions();
		//}
	}
}
