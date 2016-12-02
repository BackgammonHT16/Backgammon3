/**
 * 
 */
package bg.backgammon3.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.boardstate.*;
import bg.backgammon3.model.place.*;
import bg.backgammon3.model.pointstate.*;

/**
 * 
 *
 */
public class Board extends ModelVisitor implements TimerInterface, ModelElement {
	private Logger logger = LogManager.getLogger(Board.class);

	private Integer numberOfPlayers = null;

	private BoardState currentState;

	private Integer currentPlayer;
	
	private Place startPlace;

	private Timer timer = new Timer(this);
	
	// Verhindert das der Timer ein zweites GameOver auslöst
	private AtomicBoolean atomicGameOver = new AtomicBoolean(false);
	
	private Dices dices;

	// Alle Places die diese Map hat.
	private LinkedHashMap<Integer, Place> places;

	// Routen der einzelnen Spieler, der Key ist die playerId.
	private LinkedHashMap<Integer, Route> routes;
	
	private ArrayList<Move> moves;
	
	// Erlaubt das hinzufügen von Aktionen zur Liste der Aktionen
	GameStatus gameStatus;
	
	private static int gameNumber = 0;

	/**
	 * Konstruktor
	 * @param gameStatus Enthält den GameStatus
	 */
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
		
		moves = new ArrayList<Move>();

		logger.info("[ROUTE][INFO] Spiel " + gameNumber++ + " mit Dice Seed  " + dices.getSeed());
	}
	
	/**
	 * Wählt den StartPlatz
	 * @param place Der startplatz
	 */
	public void setStartPlace(Place place) {
		startPlace = place; 
	}
	
	/**
	 * Setzt den StartPlatz zurück
	 */
	public void resetPointState() {
		for(Map.Entry<Integer, Place> p : places.entrySet()) {
			p.getValue().setState(new NormalPoint());
		}
	}
	
	/**
	 * Markiert alle Places entweder als StartPlace oder als NormalPoint. Falls der Place ein StartPlace ist 
	 * dann wird ein Liste mit legalen EndPlaces hinzugefügt. Diese wird für die KI später benötigt.
	 * @return Wahr wenn es einen legalen StartPlace gibt.
	 */
	public boolean markStartPlaces() {
		boolean startPlaceExists = false;
		// Schleife die durch alle Places iteriert
		for(Map.Entry<Integer, Place> p : places.entrySet()) {
			// isLegalStartPlace gibt mir zu einem bestimmten StartPlace und Würfel alle gültigen EndPlaces
			ArrayList<Place> endPlaces = routes.get(currentPlayer).isLegalStartPlace(p.getValue(), dices);
			if(endPlaces.size() > 0) {
				// Falls gültige EndPlaces existieren werden diese im StartPoint abgespeichert.
				p.getValue().setState(new StartPoint(currentPlayer, endPlaces));
				startPlaceExists = true;
			} else {
				p.getValue().setState(new NormalPoint());
			}
		}
		return startPlaceExists;
	}
	
	/**
	 * Markiert die möglichen endplätze
	 */
	public void markEndPlaces() {
		resetPointState();
		startPlace.setState(new StartPoint(currentPlayer, new ArrayList<Place>(), true));
		routes.get(currentPlayer).hasLegalEndPlace(startPlace, dices, true);
	}
	
	/**
	 * Legt den StartPlatz fest
	 */
	private void setStartState() {
		currentState = new StartState(this);
	}

	/**
	 * Lädt die würfel
	 */
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

	
	/**
	 * Legt den ersten Spieler fest
	 */
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

	/**
	 * Gibt die Plätze zurück
	 * @return Die Plätze
	 */
	public LinkedHashMap<Integer, Place> getPlaces() {
		return places;
	}

	/**
	 * gibt die Würfel zurück
	 * @return Die Würfel
	 */
	public Dices getDices() {
		return dices;
	}

	/**
	 * Rollt einen einzelnen Würfel
	 * @return GameState
	 */
	public int rollSingleDice() {
		dices.singleRole();
		//addActionAtEnd(new SingleDiceWasRolled());
		return 3;
	}
	
	/**
	 * Rollt beide würfel
	 * @return GameState
	 */
	public int rollDice() {
		dices.roleDice();
		//addActionAtEnd(new DiceWasRolled());
		return 1;
	}

	/**
	 * Legt den State fest
	 * @param state Der neue State
	 */
	public void setState(BoardState state) {
		currentState = state;
	}

	/**
	 * Aktiviert den nächsten spieler
	 */
	public void nextPlayer() {
		currentPlayer = getNextPlayer();
		gameStatus.setPlayer(currentPlayer);
	}

	/**
	 * Ermittelt den nächsten Spieler
	 * @return der nächste Spieler
	 */
	public Integer getNextPlayer() {
		return (currentPlayer + 1) % getNumberOfPlayers();
	}
	
//	public void addActionAtBeginn(Action action) {
//		gameStatus.addActionAtBeginn(action);
//	}
//
//	public void addActionAtEnd(Action action) {
//		gameStatus.addActionAtEnd(action);
//	}
	
	/**
	 * Prüft ob der Spieler KI ist oder nicht
	 * @return Wahr wenn es sich nicht um die KI  handelt
	 */
	public boolean isHumanPlayer() {
		return gameStatus.isHumanPlayer(currentPlayer);
	}

	/**
	 * Bewegt einen Checker zum angegebenen Endplatz
	 * @param endPlace Der Endplatz an den der Checker bewegt werden soll
	 */
	public void moveChecker(Place endPlace) {
		PointState endPoint = endPlace.getState();
		resetPointState();
		startPlace.setState(new StartPoint(currentPlayer, new ArrayList<Place>(), true));
		if(endPoint instanceof EndPoint) {
			((EndPoint) endPoint).setSelected(true);
		}
		endPlace.setState(endPoint);
//		addActionAtEnd(new ShowRoute());
		
		// Checker am Start Entfernen
		startPlace.removeChecker();

		
		// Actions erstellen und zwischenChecker entfernen
		ArrayList<Place> ps = ((EndPoint) endPlace.getState()).places;
		
		if(ps.get(0).getPlayerId() != currentPlayer && ps.get(0).getNumberOfCheckers() > 0) {
			// Feindlichen Checker auf Bar verschieben
			routes.get(ps.get(0).getPlayerId()).getBar().addChecker(ps.get(0).getPlayerId());
//			addActionAtEnd(new Move2Checkers(ps.get(0).getId(), routes.get(ps.get(0).getPlayerId()).getBarId(), startPlace.getId(), ps.get(0).getId()));
			addMove(new Move(ps.get(0).getId(), routes.get(ps.get(0).getPlayerId()).getBarId()));
			addMove(new Move(startPlace.getId(), ps.get(0).getId()));
			((EndPoint) endPlace.getState()).dices.get(0).setUsed();
//			addActionAtEnd(new DiceWasUsed());
		} else {
			((EndPoint) endPlace.getState()).dices.get(0).setUsed();
//			addActionAtEnd(new DiceWasUsed());
//			addActionAtEnd(new MoveChecker(startPlace.getId(), ps.get(0).getId()));
			addMove(new Move(startPlace.getId(), ps.get(0).getId()));
		}
		
		for(int i = 0; i < ps.size() - 1; i++) {
			if(ps.get(i + 1).getPlayerId() != currentPlayer && ps.get(i + 1).getNumberOfCheckers() > 0) {
				// Feindlichen Checker auf Bar verschieben
				routes.get(ps.get(i + 1).getPlayerId()).getBar().addChecker(ps.get(i + 1).getPlayerId());
//				addActionAtEnd(new Move2Checkers(ps.get(i + 1).getId(), routes.get(ps.get(i + 1).getPlayerId()).getBarId(), ps.get(i).getId(), ps.get(i + 1).getId()));
				addMove(new Move(ps.get(i + 1).getId(), routes.get(ps.get(i + 1).getPlayerId()).getBarId()));
				addMove(new Move(ps.get(i).getId(), ps.get(i + 1).getId()));
				ps.get(i).addChecker(currentPlayer);
				ps.get(i).removeChecker();
				((EndPoint) endPlace.getState()).dices.get(i + 1).setUsed();
//				addActionAtEnd(new DiceWasUsed());
			} else {
				ps.get(i).addChecker(currentPlayer);
				ps.get(i).removeChecker();
				((EndPoint) endPlace.getState()).dices.get(i + 1).setUsed();
//				addActionAtEnd(new DiceWasUsed());
//				addActionAtEnd(new MoveChecker(ps.get(i).getId(), ps.get(i + 1).getId()));
				addMove(new Move(ps.get(i).getId(), ps.get(i + 1).getId()));
			}
		}
		endPlace.addChecker(currentPlayer);

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

	/**
	 * Beendet das Spiel
	 * @param winnerId Die Id des gewinners
	 */
	public void finishGame(Integer winnerId) {
		timer.killTimer();
		if(atomicGameOver.compareAndSet(false, true)) {
			gameStatus.gameIsFinished(winnerId);
			currentState = new GameOverState(this);
		}
	}
	
	/**
	 * Das Spiel soll beendet werden
	 */
	public void finishGame() {
		finishGame(currentPlayer);
	}
	
	/**
	 * Der Timer ist beendet
	 */
	public void timeOver() {
		if(!atomicGameOver.get()){
			// Nachricht nur anzeigen wenn das Spiel Vorbei ist.

			
//			addActionAtEnd(new DisplayMessage("Time Over!", Config.getInteger("displayMessageTime")));
				
			finishGame(getNextPlayer());
		}
	}
	
	/**
	 * Den Timer von diesem Board
	 * @return der Timer
	 */
	public Timer getTimer() {
		return timer;
	}
	
	/** 
	 * Die Route des Spielers
	 * @param playerId Id des Spielers
	 * @return Die Route des Spielers
	 */
	public Route getRoute(int playerId) {
		return routes.get(playerId);
	}

	/**
	 * Besuche das Board
	 */
	@Override
	public int accept(ModelVisitor gameObject) {
		return gameObject.visit(this);
	}

	/**
	 * Besuche den BoardState
	 */
	@Override
	public int nextAccept(ModelVisitor gameObject) {
		return currentState.accept(gameObject);
	}
	
	/** 
	 * Besuche das übergeben Objekt
	 */
	@Override
	public int visit(ChooseEndState g) {
		g.deselectStartPlace();
		return 0;
	}
	
	/**
	 * Füge move hinzu
	 * @param move
	 */
	private void addMove(Move move) {
		moves.add(move);
	}
	
	/**
	 * Entnehme Move
	 * @return
	 */
	public Move popMove() {
		if(moves.size() == 0) {
			return null;
		}
		return moves.remove(0);
	}
	
}
