/**
 * 
 */
package bg.backgammon3.model.player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.GameObject;
import bg.backgammon3.model.ModelElement;

/**
 * @author philipp
 *
 */
public class Human extends Player implements ModelElement {
	private Logger logger = LogManager.getLogger(Human.class);

	public Human(Integer id){
		super(id);
		logger.info("Human erstellt mit id " + id);
	}

	@Override
	public void handle(GameObject gameObject) {
		board.handle(gameObject);
	}

	@Override
	public int accept(GameObject gameObject) {
		gameObject.visit(this);
		return 0;
	}

	@Override
	public int nextAccept(GameObject gameObject) {
		board.accept(gameObject);
		return 0;
	}
}
