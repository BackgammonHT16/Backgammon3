/**
 * 
 */
package bg.backgammon3.model;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;

/**
 * @author philipp
 *
 */
public class Dices extends GameObject{
	private Logger logger = LogManager.getLogger(Dices.class);
	ArrayList<Dice> dices = new ArrayList<Dice>();
	
	private static int seed;
	
	public Dices() {
		if(seed == 0) {
			seed = Config.getInteger("seed");
		}
		for(int i = 0; i < 4; i++) {
			dices.add(new Dice(i + seed++));
		}
	}
	
	public ArrayList<Dice> roleDice(){
		resetDice();
		dices.get(0).role();
		dices.get(1).role();
		if(dices.get(0).getValue() == dices.get(1).getValue()) {
			// Pasch
			dices.get(2).set(dices.get(0).getValue());
			dices.get(3).set(dices.get(0).getValue());
		}
		logger.info("Role: " + toString());
		return dices;
	}
	
	private void resetDice() {
		for(int i = 0; i < 4; i++) {
			dices.set(i, new Dice(i + seed++));
		}
	}
	
	public ArrayList<Dice> singleRole() {
		if(dices.get(1).getIsActive()) {
			resetDice();
			logger.warn("Unerwarteter Aufruf von singleRole.");
		}
		if(dices.get(0).getIsActive()) {
			// Erster würfel wurde bereits geworfen
			dices.get(1).role();
			if(dices.get(0).getValue() == dices.get(1).getValue()) {
				// Pasch
				dices.get(2).set(dices.get(0).getValue());
				dices.get(3).set(dices.get(0).getValue());
			}
		} else {
			dices.get(0).role();
		}
		logger.info("Single Role: " + toString());
		return dices;
	}
	
	public Dice getDice(int i) {
		return dices.get(i);
	}
	
	/**
	 * Gibt die ersten index Würfel ab die brauchbar sind und gibt den an der Stelle index zurück.
	 * @param index Es wird bei Zählung nur der unbenutzten Würfel derjenige an der Stelle index zurückgegeben.
	 * @return Der angeforderte Dice oder null
	 */
	public Dice getValue(int index) {
		int unUsed = 0;
		for(int i = 0; i < 4; i++) {
			if(dices.get(i).getIsUsed() == false) {
				if(unUsed == index) {
					return dices.get(i);
				} else {
					unUsed++;
				}
			}
		}
		return null;
	}
	
	/**
	 * Gibt wahr zurück falls alle Würfel aufgebraucht wurden.
	 * @return Wahr falls alle Würfel aufgebraucht wurden.
	 */
	public boolean allDicesUsed() {
		for(Dice d : dices) {
			if(d.getIsActive() && !d.getIsUsed()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for(Dice d : dices) {
			ret += d + ", ";
		}
		return ret.substring(0, ret.length() - 2);
	}
	
	public int getSeed() {
		return seed;
	}

	public void setAllUsed() {
		for(Dice d : dices) {
			d.setUsed();
		}
	}
}
