/**
 * 
 */
package bg.backgammon3.model;

/**
 * @author philipp
 *
 */
public abstract class Player implements ModelElement {
	final Integer id;
	protected Board board;
	
	public Player(Integer id)
	{
		this.id = id;
	}
	
	public abstract void handle(GameObject gameObject);

	public void setBoard(Board board) {
		this.board = board;
	}
	
}
