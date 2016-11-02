/**
 * 
 */
package bg.backgammon3.model;

/**
 * @author philipp
 *
 */
public class Human extends Player{

	public Human(Integer id){
		super(id);
	}

	@Override
	public void handle(GameObject gameObject) {
		board.handle(gameObject);
	}
}
