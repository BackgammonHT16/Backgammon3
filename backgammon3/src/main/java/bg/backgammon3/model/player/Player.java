/**
 * 
 */
package bg.backgammon3.model.player;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.ModelElement;

/**
 * Abstrakte oberklasse
 *
 */
public abstract class Player implements ModelElement {
	final Integer id;
	protected Board board;
	
	/**
	 * Der Player Konstruktor
	 * @param id die Id des Players
	 */
	public Player(Integer id)
	{
		this.id = id;
	}
	
	//public abstract void handle(ModelVisitor gameObject);

	/**
	 * Setzt das Board
	 * @param board das Board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Setze den Startplace
	 * @return 0
	 */
	public int selectStartPlace() {
		return 0;
	}
	
	/**
	 * Der endplace
	 * @return 0
	 */
	public int selectEndPlace() {
		return 0;
	}
	
	/**
	 * Rollt den würfel
	 * @return 0
	 */
	public int rollDice() {
		return 0;
	}

	/**
	 * Gibt das Board zurück
	 * @return das Board
	 */
	public Board getBoard() {
		return board;
	}
	
}
