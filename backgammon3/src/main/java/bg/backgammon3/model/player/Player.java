/**
 * 
 */
package bg.backgammon3.model.player;

import bg.backgammon3.model.Board;
import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.ModelElement;

/**
 * 
 *
 */
public abstract class Player implements ModelElement {
	final Integer id;
	protected Board board;
	
	public Player(Integer id)
	{
		this.id = id;
	}
	
	//public abstract void handle(ModelVisitor gameObject);

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void selectStartPlace() {
		
	}
	
	public void selectEndPlace() {
		
	}
	
	public void rollDice() {
		
	}

	public Board getBoard() {
		return board;
	}
	
}
