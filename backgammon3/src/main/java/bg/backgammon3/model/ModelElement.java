/**
 * 
 */
package bg.backgammon3.model;


/**
 * @author philipp
 *
 */
public interface ModelElement {

	public abstract int accept(ModelVisitor gameObject);
	
	public abstract int nextAccept(ModelVisitor gameObject);
}
