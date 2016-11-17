/**
 * 
 */
package bg.backgammon3.model.place;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.boardstate.*;
import bg.backgammon3.model.pointstate.*;

/**
 * 
 *
 */
public abstract class Place extends ModelVisitor {
	private Logger logger = LogManager.getLogger(Place.class);
	protected Integer playerId = -1;
	protected Integer numberOfCheckers;
	protected Integer id;
	protected PointState pointState;
	
	public Place(Integer id) {
		this.id = id;
		this.pointState = new NormalPoint();
		this.numberOfCheckers = 0;
	}
	
	public void setState(PointState pointState) {
		this.pointState = pointState;
	}
	
	/**
	 * Gibt die id des Platzes zurück.
	 * @return id dieses Platzes.
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Id des Spielers der das Feld belegt.</br>
	 * Der Wert ist bei unbelegten Points stets -1, bei Bar und Goal unabhängig von der 
	 * Belegung konstant der Wert des Spielers dem es gehört.
	 * @return Gibt die playerId des Spielers zurück der das Feld Belegt.
	 */
	public Integer getPlayerId(){
		return playerId;
	}
	
	public Integer addChecker(Integer playerId) {
		return addChecker(1, playerId);
	}
	
	/**
	 * Fügt numberOfCheckers auf diesen Platz hinzu. Falls der Platz eingenommen wird, 
	 * wird der Belegende Checker als Integer zurückgegeben. PlayerId wird entsprechend geändert.
	 * @param numberOfCheckers Anzahl der hinzuzufügenden Checker
	 * @param playerId Spieler dem die Checker gehören
	 * @return Anzahl der verdrängten Checker. Diese sollte stets <= 1 sein.
	 */
	public Integer addChecker(Integer numberOfCheckers, Integer playerId){
		logger.info(numberOfCheckers + " Checker von Spieler " + playerId + " bei Place " + id + " hinzugefügt.");
		if(numberOfCheckers == 0) {
			return 0;
		}
		if(playerId != this.playerId) {
			this.playerId = playerId;
			if(this.numberOfCheckers > 1) {
				logger.error("Regelverletzung: Mehr als ein Checker verdrängt.");
			}
			Integer oldNumberOfCheckers = this.numberOfCheckers;
			this.numberOfCheckers = numberOfCheckers;
			return oldNumberOfCheckers;
		}
		else {
			this.numberOfCheckers += numberOfCheckers;
			return 0;
		}
	}
	
	public void removeChecker() {
		removeChecker(1);
	}
	
	/**
	 * Entfernt die vorgegebene Anzahl an Checker von diesem Place.
	 * @param numberOfCheckers Anzahl der zu entfernenden Checker.
	 */
	public void removeChecker(Integer numberOfCheckers) {
		logger.info(numberOfCheckers + " Checker bei Place " + id + " entfernt.");
		if(numberOfCheckers > this.numberOfCheckers) {
			logger.info("Regelverletzung: Mehr Checker von Point genommen als drauf waren.");
		}else if(numberOfCheckers == this.numberOfCheckers) {
			this.numberOfCheckers = 0;
			this.playerId = -1;
		} else {
			this.numberOfCheckers -= numberOfCheckers;
		}
	}
	
	/**
	 * Gibt die Anzahl der Checker auf diesem Place zurück.
	 * @return Die Anzahl der Checker auf diesem Place.
	 */
	public Integer getNumberOfCheckers() {
		return numberOfCheckers;
	}

	public PointState getState() {
		return pointState;
	}
	
	@Override
	public String toString() {
		return "Place " + id + " im Zustand " + pointState;
	}

	@Override
	public int visit(ChooseStartState g) {
		return pointState.visit(g, this);
	}
	
	@Override
	public int visit(ChooseEndState g) {
		return pointState.visit(g, this);
	}
}
