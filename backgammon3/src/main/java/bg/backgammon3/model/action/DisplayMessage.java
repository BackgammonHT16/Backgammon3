/**
 * 
 */
package bg.backgammon3.model.action;

/**
 * @author philipp
 *
 */
public class DisplayMessage extends Action {
	private String message;
	
	public DisplayMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int getTime() {
		return 0;
	}
}
