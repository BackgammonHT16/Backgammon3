/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.ModelVisitor;

/**
 * Game ObjectView
 *
 */
public interface GameObjectView {
	
	public abstract ModelVisitor getGameObject();
}
