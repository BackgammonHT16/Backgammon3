/**
 * 
 */
package bg.backgammon3.model.pointstate;

import java.util.ArrayList;

import bg.backgammon3.model.*;
import bg.backgammon3.model.boardstate.ChooseEndState;
import bg.backgammon3.model.boardstate.ChooseStartState;
import bg.backgammon3.model.place.Place;

/**
 * @author philipp
 *
 */
public class EndPoint extends PointState {
	public ArrayList<Dice> dices = new ArrayList<Dice>();
	public ArrayList<Place> places = new ArrayList<Place>();
	private Integer playerId;
	private boolean selected;
	
	public EndPoint(Integer playerId, Dice d0, Place p0){
		dices.add(d0);
		places.add(p0);
		this.playerId = playerId;
		this.selected = false;
	}

	public EndPoint(Integer playerId, Dice d0, Place p0, Dice d1, Place p1){
		dices.add(d0);
		places.add(p0);
		dices.add(d1);
		places.add(p1);
		this.playerId = playerId;
		this.selected = false;
	}

	public EndPoint(Integer playerId, Dice d0, Place p0, Dice d1, Place p1, Dice d2, Place p2){
		dices.add(d0);
		places.add(p0);
		dices.add(d1);
		places.add(p1);
		dices.add(d2);
		places.add(p2);
		this.playerId = playerId;
		this.selected = false;
	}

	public EndPoint(Integer playerId, Dice d0, Place p0, Dice d1, Place p1, Dice d2, Place p2, Dice d3, Place p3){
		dices.add(d0);
		places.add(p0);
		dices.add(d1);
		places.add(p1);
		dices.add(d2);
		places.add(p2);
		dices.add(d3);
		places.add(p3);
		this.playerId = playerId;
		this.selected = false;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}
	
	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		selected = true;
	}

	@Override
	public int visit(ChooseEndState g, Place place) {
		g.selectEndPlace(place);
		return 0;
	}
	
}
