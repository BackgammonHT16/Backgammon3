/**
 * 
 */
package bg.backgammon3.model.boardstate;

import bg.backgammon3.model.*;

/**
 * @author philipp
 *
 */
public abstract class BoardState {
	protected Board board;
	
	public BoardState(Board board) {
		this.board = board;
	}
	
	public abstract void handle(GameObject gameObject);
}
