/**
 * 
 */
package bg.backgammon3.model;



import bg.backgammon3.config.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author philipp
 *
 */
public class Timer extends GameObject {
	private boolean active;
	private boolean over;
	private IntegerProperty time;
	private Timeline timeline;
	
	public Timer() {
		resetTimer();
	}
	
	public void resetTimer() {
		over = false;
		if(timeline != null) {
			timeline.stop();
		}
		time = new SimpleIntegerProperty(Config.getInteger("maximumTime"));
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
	                	time.add(-1);
	                    if (time.get() <= 0) {
	                    	active = false;
	                    	over = true;
	                        timeline.stop();
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
}
