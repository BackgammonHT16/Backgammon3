/**
 * 
 */
package bg.backgammon3.model;


/**
 * 
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
//	public abstract void addActionAtBeginn(Action action);
	
	/**
	 * Fügt das Element action an das Ende der Liste
	 * 
	 * @param action
	 *            Dieses Element wird am Ende der Liste angefügt.
	 */
//	public abstract void addActionAtEnd(Action action);
	

	/**
	 * Gibt an ob der Spieler mit der Id playerId ein Menschlicher Spieler ist.
	 * @param playerId Die id des zu prüfenden Spielers.
	 * @return Wahr wenn der Spieler ein Mensch ist.
	 */
	public abstract boolean isHumanPlayer(Integer playerId);
}
