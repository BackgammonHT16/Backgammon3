/**
 * 
 */
package bg.backgammon3.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.action.*;
import bg.backgammon3.model.gamestate.*;

/**
 * Ausgangsklasse des Modells
 * 
 * @author philipp
 *
 */
public class Game extends GameStatus {
	private Logger logger = LogManager.getLogger(Game.class);

	// Liste der durch View durchzuführender Aktionen.
	private List<Action> actions = new ArrayList<Action>();

	// Spieler

	private LinkedHashMap<Integer, Player> players;

	// Aktueller Spieler
	private Player currentPlayer;

	// Aktueller Zustand des Spiels.
	private GameState currentState;

	// Das SpielBrett
	private Board board;
	
	// Das Menu
	private Menu menu = new Menu();

	public Game() {
		initGame();
	}

	/**
	 * Initialisiert die Modell Klassen und zeigt zu Spielbeginn das Menu an.
	 */
	private void initGame() {
		currentState = new MenuState(this, false);
	}

	/**
	 * Fügt das Element action an den Beginn der Liste
	 * 
	 * @param action
	 *            Dieses Element wird am Anfang der Liste angefügt.
	 */
	@Override
	public void addActionAtBeginn(Action action) {
		//checkActionForAI(action);
		actions.add(0, action);
	}

	/**
	 * Fügt das Element action an das Ende der Liste
	 * 
	 * @param action
	 *            Dieses Element wird am Ende der Liste angefügt.
	 */
	@Override
	public void addActionAtEnd(Action action) {
		//checkActionForAI(action);
		actions.add(action);
	}
	
	/**
	 * Falls das Board von der AI eine Aktion erwartet weisen wir die AI hier an
	 * diese Aktion auszuführen.
	 * @param action Die zu überprüfende Aktion
	 */
	public void checkActionForAI(Action action) {
		if(currentPlayer instanceof AI) {
			// Die AI ist am Zug
			if(action instanceof SelectStartPlace) {
				((AI) currentPlayer).selectStartPlace();
			} else if(action instanceof SelectEndPlace) {
				((AI) currentPlayer).selectEndPlace();
			} else if(action instanceof RollDice) {
				((AI) currentPlayer).rollDice();
			}
		}
	}

	/**
	 * Gibt die nächste Aktion zurück
	 * 
	 * @return Nächste Aktion auf der actions liste.
	 */
	public Action getAction() {
		if (actions.size() == 0) {
			return null;
		} else {
			return actions.get(0);
		}
	}

	/**
	 * Gibt die nächste Aktion zurück und entfernt diese von der Liste
	 * 
	 * @return Nächste Aktion auf der actions liste.
	 */
	public Action popAction() {
		if (actions.size() == 0) {
			return null;
		} else {
			return actions.remove(0);
		}
	}

	/**
	 * Gibt das durch den Controller übergebene Event an die entsprechende
	 * Stelle weiter.
	 * 
	 * @param event Der zu verarbeitende Event
	 */
	public void handle(GameObject gameObject, boolean busy) {
		currentState.handle(gameObject, busy);
	}

	/**
	 * Hier wird der aktuelle Spieler durch das Board gesetzt.
	 */
	@Override
	public void setPlayer(Integer playerId) {
		logger.info("Spieler in Game geändert auf Spieler: " + playerId);
		currentPlayer = players.get(playerId);
	}

	/**
	 * Board teilt mit dieser Funktion dem Game mit das player gewonnen hat und
	 * beendet das Spiel
	 */
	@Override
	public void gameIsFinished(Integer playerId) {
		String playerColor = "Blue";
		if(playerId == 1) {
			playerColor = "Red";
		}
		addActionAtEnd(new DisplayMessage(playerColor + " player  Won."));
		addActionAtEnd(new CloseGame());
		currentState = new MenuState(this, false);
	}

	/**
	 * Gibt an ob das Spiel aus dem Menu fortgesetzt werden kann. Dies wird von
	 * MenuStage gebraucht um zu wissen ob der Continue Button angezeigt werden
	 * soll.
	 * 
	 * @return True falls das Spiel fortgesetzt werden kann.
	 */
	public boolean gameCanContinue() {
		return currentState.getGameCanContinue();
	}

	public void startGame() {
		currentState.startGame();

	}

	public void continueGame() {
		currentState.continueGame();
	}

	public void quitGame() {
		currentState.quitThisGame();
	}

	public void setState(GameState gameState) {
		this.currentState = gameState;
	}

	/**
	 * Initialisiert das Spielfeld
	 */
	public void initBoard() {
		// Spieler Laden
		initPlayers();
		
		
		// Brett Laden
		board = new Board(this);
		

		for(Map.Entry<Integer, Player> p : players.entrySet()) {
			p.getValue().setBoard(board);
		}
		
		board.initBoard();
		
	}
	

	/**
	 * Ladet die Spieler und setzt den aktuellen Spieler fest. Es werden so
	 * viele Spieler geladen wie in der Konfigurationsdatei angegeben sind
	 */
	private void initPlayers() {
		players = new LinkedHashMap<Integer, Player>();
		// Alle Spieler aus der Konfigurationsdatei hinzufügen
		for (int i = 0; Config.getInteger("player" + i + "Type") != null; i++) {
			// Es wird unterschieden zwischen 
			// Menschlichen Spielern (0), 
			// Einfacher AI (1) und  
			// Komplexer AI (2).
			if (Config.getInteger("player" + i + "Type") == 0) {
				players.put(i, new Human(i));
			} else if (Config.getInteger("player" + i + "Type") == 1) {
				players.put(i, new AI(i));
			} else if (Config.getInteger("player" + i + "Type") == 2) {
				players.put(i, new AI2(i));
			}
		}
		// Der Spieler der anfängt wird festgelegt.
		if (Config.getInteger("firstPlayer") != null && Config.getInteger("firstPlayer") < players.size()) {
			currentPlayer = players.get(Config.getInteger("firstPlayer"));
		}
		else {
			currentPlayer = players.get(0);
			logger.warn("Anfangsspieler ist in der Konfigurationsdatei falsch definiert.");
		}
	}
	
	/**
	 * Gibt das Board Objekt zurück
	 * @return Das Board
	 */
	public Board getBoard() {
		return board;
	}

	public Menu getMenu() {
		return menu;
	}

	public void letPlayerHandle(GameObject gameObject) {
		currentPlayer.handle(gameObject);
	}
}
