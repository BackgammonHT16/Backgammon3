/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.action.Action;

/**
 * @author philipp
 *
 */
public abstract class GameStatus {

	/**
	 * Hier wird der aktuelle Spieler durch das Board gesetzt.
	 * @param player der Spieler der jetzt dran ist.
	 */
	public abstract void setPlayer(Integer playerId);
	
	/**
	 * Board teilt mit dieser Funktion dem Game mit das player gewonnen hat und beendet das Spiel
	 * @param player Der Spieler der gewonnen hat.
	 */
	public abstract void gameIsFinished(Integer playerId);
	
	/**
	 * Fügt das Element action an den Beginn der Liste
	 * 
	 * @param action
	 *            Dieses Element wird am Anfang der Liste angefügt.
	 */	
	public abstract void addActionAtBeginn(Action action);
	
	/**
	 * Fügt das Element action an das Ende der Liste
	 * 
	 * @param action
	 *            Dieses Element wird am Ende der Liste angefügt.
	 */
	public abstract void addActionAtEnd(Action action);
}
