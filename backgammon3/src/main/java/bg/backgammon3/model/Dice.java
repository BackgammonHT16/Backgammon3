/**
 * 
 */
package bg.backgammon3.model;

import java.util.Random;

import bg.backgammon3.config.Config;

/**
 * 
 *
 */
public class Dice {
	private int value = 0;
	private Random r = new Random(1);
	private static int simulationDiceNumber = 0;
	
	public Dice() {
		r = new Random();
	}
	
	// zum Debuggen
	public Dice(int seed) {
		if(Config.getInteger("useSeed") == 1) {
			r = new Random(seed);
		}
		else {
			r = new Random();
		}
	}
	
	// Würfel wurde benutzt
	private boolean used = false;
	
	// Würfel wurde noch nicht gewürfelt
	private boolean active = false;
	
	public int role() {
		if(Config.getInteger("simulateDice") == 0) {
			value = r.nextInt(6) + 1;
		} else {
			if(Config.getInteger("d" + simulationDiceNumber) == null) {
				simulationDiceNumber = 0;
			}
			value = Config.getInteger("d" + simulationDiceNumber++);
		}
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
