/**
 * 
 */
package bg.backgammon3.model.pointstate;

/**
 * @author philipp
 *
 */
public class StartPoint extends PointState {
	private Integer playerId;
	
	public StartPoint(Integer playerId) {
		this.playerId = playerId;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}
}
