/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.*;

/**
 * 
 *
 */
public abstract class BoardState implements ModelElement {
	protected Board board;
	
	public BoardState(Board board) {
		this.board = board;
	}
	
	//public abstract void handle(ModelVisitor gameObject);
}
