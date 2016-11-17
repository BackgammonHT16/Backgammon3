/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.config.Config;


/**
 * 
 *
 */
public class MoveChecker extends Action {
	private Integer startId;
	private Integer endId;
	
	public MoveChecker(Integer startId, Integer endId) {
		this.startId = startId;
		this.endId = endId;
	}
	
	
	public int visit(BoardElement view) {
		return view.moveChecker(startId, endId);
	}

	public Integer getStartId() {
		return startId;
	}
	
	public Integer getEndId() {
		return endId;
	}

	@Override
	public int getTime() {
		return Config.getInteger("checkerMoveTime");
	}
}
