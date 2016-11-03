/**
 * 
 */
package bg.backgammon3.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author philipp
 *
 */
public class Human extends Player{
	private Logger logger = LogManager.getLogger(Human.class);

	public Human(Integer id){
		super(id);
		logger.info("Human erstellt mit id " + id);
	}

	@Override
	public void handle(GameObject gameObject) {
		board.handle(gameObject);
	}
}
