/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.ModelVisitor;

/**
 * @author philipp
 *
 */
public interface GameObjectView {
	
	public abstract ModelVisitor getGameObject();
}
