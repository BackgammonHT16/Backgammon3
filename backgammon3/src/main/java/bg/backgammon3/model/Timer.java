/**
 * 
 */
package bg.backgammon3.model;



import bg.backgammon3.config.Config;
import bg.backgammon3.model.player.AI2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

/**
 * 
 *
 */
public class Timer extends ModelVisitor {
	private boolean active;
	private boolean over;
	private IntegerProperty time = new SimpleIntegerProperty(Config.getInteger("maximumTime"));
	private Timeline timeline;
	private TimerInterface actionInterface;
	
	
	public Timer(TimerInterface actionInterface) {
		this.actionInterface = actionInterface;
	}
	
	public void setActionInterface(TimerInterface actionInterface) {
		this.actionInterface = actionInterface;
	}
	
	public void killTimer() {
		if(timeline != null) {
			timeline.stop();
		}
	}
	
	public void resetTimer() {
		over = false;
		if(timeline != null) {
			timeline.stop();
		}
		time.set(Config.getInteger("maximumTime"));
		if(time.get() <= 0) {
			active = false;
		} else {
			active = true;
		}
	}
	
	public IntegerProperty acitvateTimer() {   
		if(!active){
			return new SimpleIntegerProperty(-1);
		}
	    timeline = new Timeline();
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.getKeyFrames().add(
	            new KeyFrame(Duration.seconds(1),
	              e-> {
	                	time.set(time.get() - 1);
	                    if (time.get() <= 0) {
	                    	active = false;
	                    	over = true;
	                        timeline.stop();
	                    	actionInterface.timeOver();
	                    }
	                  }
	            ));
	    timeline.playFromStart();
	    return time;
	}

	public boolean isOver() {
		return over;
	}
	
	public boolean isActive() {
		return active;
	}

	public IntegerProperty getTime() {
		return time;
	}
		
	public int visit(AI2 ai) {
		ai.getBoard().accept(this);
		return 0;
	}
	
	public int visit(Board board) {
		board.timeOver();
		return 0;
	}
}
