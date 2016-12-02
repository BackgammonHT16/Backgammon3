/**
 * 
 */
package bg.backgammon3.model;

import java.util.Random;

import bg.backgammon3.config.Config;

/**
 * Modelliert den Würfel
 *
 */
public class Dice {
	private int value = 0;
	private Random r = new Random(1);
	private static int simulationDiceNumber = 0;
	
	/**
	 * Konstruktor
	 */
	public Dice() {
		r = new Random();
	}
	
	// zum Debuggen
	/**
	 * Konstruktor
	 * @param seed Seed
	 */
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
	
	/**
	 * Würfel Rollen
	 * @return wert der gerollt wurde
	 */
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
	
	/**
	 * Würfel wird auf value gesetzt
	 * @param value neuer Würfel Wert
	 */
	public void set(Integer value) {
		used = false;
		active = true;
		this.value = value;
	}
	
	/**
	 * Würfel wurde benutzt
	 */
	public void setUsed() {
		used = true;
	}
	
	/**
	 * Gibt an ob der Würfel aktiv ist
	 * @return der Würfel ist aktiv
	 */
	public boolean getIsActive() {
		return active;
	}
	
	/**
	 * Gibt an ob der Würfel benutzt wurde
	 * @return Wahr wenn der würfel benutzt wurde
	 */
	public boolean getIsUsed() {
		return used;
	}
	
	/**
	 * Gibt den Würfel wert an
	 * @return Der wert des Würfels
	 */
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
