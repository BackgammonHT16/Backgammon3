/**
 * 
 */
package bg.backgammon3.model;


/**
 * @author philipp
 *
 */
public interface ModelElement {

	public abstract int accept(GameObject gameObject);
	
	public abstract int nextAccept(GameObject gameObject);
}
