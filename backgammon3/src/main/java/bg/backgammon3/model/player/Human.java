/**
 * 
 */
package bg.backgammon3.model.player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.model.ModelVisitor;
import bg.backgammon3.model.ModelElement;

/**
 * 
 *
 */
public class Human extends Player implements ModelElement {
	private Logger logger = LogManager.getLogger(Human.class);

	public Human(Integer id){
		super(id);
		logger.info("Human erstellt mit id " + id);
	}

	/*@Override
	public void handle(ModelVisitor gameObject) {
		board.handle(gameObject);
	}*/

	@Override
	public int accept(ModelVisitor gameObject) {
		return gameObject.visit(this);
	}

	@Override
	public int nextAccept(ModelVisitor gameObject) {
		return board.accept(gameObject);
	}
}
