/**
 * 
 */
package bg.backgammon3.model.action;



/**
 * 
 *
 */
public class DisplayMessage extends Action {
	private String message;
	private Integer activeTime;
	
	public DisplayMessage(String message) {
		this.message = message;
		this.activeTime = 0;
	}
	
	
	public int visit(BoardElement view) {
		view.setMessage(message);
		return activeTime;
	}
	
	public DisplayMessage(String message, Integer time) {
		this.message = message;
		this.activeTime = time;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int getTime() {
		return activeTime;
	}
	
	@Override
	public String toString() {
		return "DisplayMessage: " + message;
	}
}
