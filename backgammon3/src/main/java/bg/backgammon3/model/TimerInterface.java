/**
 * 
 */
package bg.backgammon3.model;

import bg.backgammon3.model.action.Action;

/**
 * @author philipp
 *
 */
public interface ActionInterface {
	
	public void addActionAtBeginn(Action action);

	public void addActionAtEnd(Action action);
	
	public void timeOver();
}
