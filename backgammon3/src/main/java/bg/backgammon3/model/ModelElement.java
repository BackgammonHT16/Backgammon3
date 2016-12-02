/**
 * 
 */
package bg.backgammon3.model;


/**
 * 
 *
 */
public interface ModelElement {

	/**
	 * Wendet den visitor an
	 * @param gameObject das besuchende Objekt
	 * @return der GameState
	 */
	public abstract int accept(ModelVisitor gameObject);
	
	/**
	 * Das nächste zu besuchende objekt
	 * @param gameObject das besuchende Objekt
	 * @return der GameState
	 */
	public abstract int nextAccept(ModelVisitor gameObject);
}
