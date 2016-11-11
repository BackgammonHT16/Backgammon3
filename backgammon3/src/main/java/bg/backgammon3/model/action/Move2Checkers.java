/**
 * 
 */
package bg.backgammon3.model.action;

import bg.backgammon3.view.BoardView;

/**
 * @author philipp
 *
 */
public class Move2Checkers extends Action {
	private Integer startId1;
	private Integer endId1;
	private Integer startId2;
	private Integer endId2;
	
	public Move2Checkers(Integer startId1, Integer endId1, Integer startId2, Integer endId2) {
		this.startId1 = startId1;
		this.endId1 = endId1;
		this.startId2 = startId2;
		this.endId2 = endId2;
	}

	public int visit(BoardView view) {
		return Math.max(view.moveChecker(startId1, endId1),
				view.moveChecker(startId2, endId2));
	}
	
	public Integer getStartId1() {
		return startId1;
	}
	
	public Integer getEndId1() {
		return endId1;
	}
	
	public Integer getStartId2() {
		return startId2;
	}
	
	public Integer getEndId2() {
		return endId2;
	}

	@Override
	public int getTime() {
		return 0;
	}
}
