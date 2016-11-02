/**
 * 
 */
package bg.backgammon3.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.action.Action;
import bg.backgammon3.model.action.DiceWasRolled;
import bg.backgammon3.model.action.DiceWasUsed;
import bg.backgammon3.model.action.MoveChecker;
import bg.backgammon3.model.action.ShowRoute;
import bg.backgammon3.model.action.SingleDiceWasRolled;
import bg.backgammon3.model.boardstate.*;
import bg.backgammon3.model.place.*;
import bg.backgammon3.model.pointstate.*;
import javafx.scene.Node;

/**
 * @author philipp
 *
 */
public class Board extends GameObject {
	private Logger logger = LogManager.getLogger(Board.class);

	private Integer numberOfPlayers = null;

	private BoardState currentState;

	private Integer currentPlayer;
	
	private Place startPlace;

	private Timer timer;
	
	private Dices dices;

	// Alle Places die diese Map hat.
	private LinkedHashMap<Integer, Place> places;

	// Routen der einzelnen Spieler, der Key ist die playerId.
	private LinkedHashMap<Integer, Route> routes;
	
	// Erlaubt das hinzufügen von Aktionen zur Liste der Aktionen
	GameStatus gameStatus;

	public Board(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
		// initBoard();
	}

	/**
	 * Initialisiert das Board nach den vorgaben aus der Konfigurationsdatei
	 */
	public void initBoard() {
		// Aktuellen Spieler einstellen
		setFirstPlayer();

		// Places Laden und mit Checkers initialisieren
		loadPlaces();

		// Routen Laden
		loadRoutes();

		// Würfel Laden
		loadDices();

		// Start Zustand Einstellen
		setStartState();

	}
	
	public void setStartPlace(Place place) {
		startPlace = place; 
	}
	
	public void resetPointState() {
		for(Map.Entry<Integer, Place> p : places.entrySet()) {
			p.getValue().setState(new NormalPoint());
		}
	}
	
	public boolean markStartPlaces() {
		boolean startPlaceExists = false;
		for(Map.Entry<Integer, Place> p : places.entrySet()) {
			if(routes.get(currentPlayer).isLegalStartPlace(p.getValue(), dices)) {
				p.getValue().setState(new StartPoint(currentPlayer));
				startPlaceExists = true;
			} else {
				p.getValue().setState(new NormalPoint());
			}
		}
		return startPlaceExists;
	}
	
	public void markEndPlaces() {
		resetPointState();
		startPlace.setState(new StartPoint(currentPlayer));
		routes.get(currentPlayer).hasLegalEndPlace(startPlace, dices, true);
	}
	
	public void handle(GameObject gameObject) {
		currentState.handle(gameObject);
	}

	private void setStartState() {
		currentState = new StartState(this);
	}

	private void loadDices() {
		dices = new Dices();
	}

	/**
	 * Jeder Spieler erhält eine eigene Route die aus einer Aneinanderreihung der einzelnen Punkte besteht.
	 */
	private void loadRoutes() {
		routes = new LinkedHashMap<Integer, Route>();
		for(int i = 0; i < getNumberOfPlayers(); i++) {
			routes.put(i, new Route(i, places));
		}
	}

	/**
	 * Hier werden alle Places in die variable places geladen nach Vorgabe aus der Konfigurationsdatei.
	 */
	private void loadPlaces() {
		places = new LinkedHashMap<Integer, Place>();
		for (int i = 0; Config.getInteger("place" + i + "Type") != null; i++) {
			// Typ 0: Point; 1: Bar; 2: Goal
			if (Config.getInteger("place" + i + "Type") == 0) {
				places.put(i, new Point(Config.getInteger("place" + i + "CheckersNumber"),
						Config.getInteger("place" + i + "CheckersPlayer"), i));
			} else if (Config.getInteger("place" + i + "Type") == 1) {
				places.put(i, new Bar(Config.getInteger("place" + i + "CheckersNumber"),
						Config.getInteger("place" + i + "CheckersPlayer"), i));
			} else if (Config.getInteger("place" + i + "Type") == 2) {
				places.put(i, new Goal(Config.getInteger("place" + i + "CheckersNumber"),
						Config.getInteger("place" + i + "CheckersPlayer"), i));
			} else {
				logger.warn("Bei der erstellung der Places ist der Place " + i + " vom flaschen Typ ("
						+ Config.getInteger("place" + i + "Type") + ").");
			}
		}
	}

	private void setFirstPlayer() {
		if (Config.getInteger("firstPlayer") >= getNumberOfPlayers()) {
			logger.warn("Id des ersten Spielers in der Konfigurationsdatei ist höher als die anzahl der Spieler.");
		}
		currentPlayer = Config.getInteger("firstPlayer") % getNumberOfPlayers();
		gameStatus.setPlayer(currentPlayer);
	}

	/**
	 * Zählt die Anzahl der Spieler
	 * 
	 * @return Anzahl der Spieler
	 */
	public Integer getNumberOfPlayers() {
		if (numberOfPlayers == null) {
			// Alle Spieler aus der Konfigurationsdatei zählen
			for (numberOfPlayers = 0; Config.getInteger("player" + numberOfPlayers + "Type") != null; numberOfPlayers++)
				;
		}
		return numberOfPlayers;
	}

	public LinkedHashMap<Integer, Place> getPlaces() {
		return places;
	}

	public Dices getDices() {
		return dices;
	}

	public void rollSingleDice() {
		dices.singleRole();
		addActionAtEnd(new SingleDiceWasRolled());
	}
	
	public void rollDice() {
		dices.roleDice();
		addActionAtEnd(new DiceWasRolled());
	}

	public void setState(BoardState state) {
		currentState = state;
	}

	public void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % getNumberOfPlayers();
		gameStatus.setPlayer(currentPlayer);
	}

	public void addActionAtBeginn(Action action) {
		gameStatus.addActionAtBeginn(action);
	}

	public void addActionAtEnd(Action action) {
		gameStatus.addActionAtEnd(action);
	}

	public void moveChecker(Place endPlace) {
		PointState endPoint = endPlace.getState();
		resetPointState();
		startPlace.setState(new StartPoint(currentPlayer));
		endPlace.setState(endPoint);
		addActionAtEnd(new ShowRoute());
		
		// Checker am Start Entfernen
		startPlace.removeChecker();

		
		// Actions erstellen und zwischenChecker entfernen
		ArrayList<Place> ps = ((EndPoint) endPlace.getState()).places;
		
		if(ps.get(0).getPlayerId() != currentPlayer && ps.get(0).getNumberOfCheckers() > 0) {
			// Feindlichen Checker auf Bar verschieben
			routes.get(ps.get(0).getPlayerId()).getBar().addChecker(ps.get(0).getPlayerId());
			addActionAtEnd(new MoveChecker(ps.get(0).getId(), routes.get(ps.get(0).getPlayerId()).getBarId()));
		}
		((EndPoint) endPlace.getState()).dices.get(0).setUsed();
		addActionAtEnd(new DiceWasUsed());
		addActionAtEnd(new MoveChecker(startPlace.getId(), ps.get(0).getId()));
		
		for(int i = 0; i < ps.size() - 1; i++) {
			if(ps.get(i + 1).getPlayerId() != currentPlayer && ps.get(i + 1).getNumberOfCheckers() > 0) {
				// Feindlichen Checker auf Bar verschieben
				routes.get(ps.get(i + 1).getPlayerId()).getBar().addChecker(ps.get(i + 1).getPlayerId());
				addActionAtEnd(new MoveChecker(ps.get(i + 1).getId(), routes.get(ps.get(i + 1).getPlayerId()).getBarId()));
			}
			ps.get(i).addChecker(currentPlayer);
			ps.get(i).removeChecker();
			((EndPoint) endPlace.getState()).dices.get(i + 1).setUsed();
			addActionAtEnd(new DiceWasUsed());
			addActionAtEnd(new MoveChecker(ps.get(i).getId(), ps.get(i + 1).getId()));
		}
		endPlace.addChecker(currentPlayer);

		// Würfel aktualisieren
		//for(Dice d : ((EndPoint) endPlace.getState()).dices) {
		//	d.setUsed();
		//}
			

		// startPlace zurücksetzen
		startPlace = null;
	}
	
	/**
	 * Gibt wahr zurück wenn der aktuelle Spieler gewonnen hat.
	 * @return Wahr wenn der aktuelle Spieler gewonnen hat.
	 */
	public boolean hasWon() {
		return routes.get(currentPlayer).hasWon();
	}

	public void finishGame() {
		gameStatus.gameIsFinished(currentPlayer);
	}
	
}
