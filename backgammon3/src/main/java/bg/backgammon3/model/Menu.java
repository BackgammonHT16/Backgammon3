/**
 * 
 */
package bg.backgammon3.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;

/**
 * 
 *
 */
public class Menu extends ModelVisitor {
	private Logger logger = LogManager.getLogger(Menu.class);
	private Integer color;
	private Integer difficulty;
	private Boolean sound;
	private Integer time;
	
	/**
	 * Konstruktor
	 * @param color Spieler Farbe
	 * @param difficulty Spiel Schwierigkeit
	 * @param sound Sound
	 * @param time Zeit
	 */
	public Menu(Integer color, Integer difficulty, Boolean sound, Integer time) {
		this.color = color;
		this.difficulty = difficulty;
		this.sound = sound;
		this.time = time;
	}
	
	/**
	 * Konstruktor
	 */
	public Menu() {
		color = Config.getInteger("firstPlayer");
		if(Config.getInteger("player0Type") != 0) {
			difficulty = Config.getInteger("player0Type");
		} else {
			difficulty = Config.getInteger("player1Type");
		}
		sound = Config.getInteger("playSound") == 0 ? false : true;
		time = Config.getInteger("maximumTime");
	}

	/**
	 * Die Farbe
	 * @return the color
	 */
	public Integer getColor() {
		return color;
	}

	/**
	 * Die Farbe die zu setzen ist
	 * @param color die neue Farbe
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * Der Schwierigkeitsgrad
	 * @return Der Schwierigkeitsgrad
	 */
	public Integer getDifficulty() {
		if(difficulty == 0) {
			// Beide Spieler sind Menschen. Solange das Menu nicht darauf angepasst ist
			// wird das einfach ignoriert.
			return 1;
		}
		return difficulty;
	}

	/**
	 * Setzt den Schwierigkeitsgrad
	 * @param difficulty Der neue Schwierigkeitsgrad
	 */
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Der Sound
	 * @return der sound
	 */
	public Boolean getSound() {
		return sound;
	}

	/**
	 * Der Sound
	 * @param sound der neue Sound
	 */
	public void setSound(Boolean sound) {
		this.sound = sound;
	}

	/**
	 * Die Zeit
	 * @return die Zeit
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * Setzt die Zeit
	 * @param time Die neue zeit
	 */
	public void setTime(Integer time) {
		logger.info("Zeit wurde in Menu auf " + time + " gesetzt.");
		this.time = time;
	}

	
}
