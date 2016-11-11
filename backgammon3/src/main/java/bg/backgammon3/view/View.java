/**
 * 
 */
package bg.backgammon3.view;

import bg.backgammon3.model.action.Action;

/**
 * @author philipp
 *
 */
public interface View {
	
	public int accept(Action action);
	
	public int nextAccept(Action action);
}
