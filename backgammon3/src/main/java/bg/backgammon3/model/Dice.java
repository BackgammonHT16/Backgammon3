/**
 * 
 */
package bg.backgammon3.model;

import java.util.Random;

/**
 * @author philipp
 *
 */
public class Dice {
	private int value = 0;
	
	// Würfel wurde benutzt
	private boolean used = false;
	
	// Würfel wurde noch nicht gewürfelt
	private boolean active = false;
	
	public int role() {
		Random r = new Random();
		value = r.nextInt(6) + 1;
		used = false;
		active = true;
		return value;
	}
	
	public void set(Integer value) {
		used = false;
		active = true;
		this.value = value;
	}
	
	public void setUsed() {
		used = true;
	}
	
	public boolean getIsActive() {
		return active;
	}
	
	public boolean getIsUsed() {
		return used;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		if(active) {
			if(used) {
				return value + "u";
			} else {
				return value + "";
			}
		} else {
			return "i";
		}
	}
}
