/**
 * 
 */
package bg.backgammon3.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.gamestate.MenuState;

/**
 * @author philipp
 *
 */
public class Menu extends GameObject {
	private Logger logger = LogManager.getLogger(Menu.class);
	private Integer color;
	private Integer difficulty;
	private Boolean sound;
	private Integer time;
	
	public Menu(Integer color, Integer difficulty, Boolean sound, Integer time) {
		this.color = color;
		this.difficulty = difficulty;
		this.sound = sound;
		this.time = time;
	}
	
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
	 * @return the color
	 */
	public Integer getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * @return the difficulty
	 */
	public Integer getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the sound
	 */
	public Boolean getSound() {
		return sound;
	}

	/**
	 * @param sound the sound to set
	 */
	public void setSound(Boolean sound) {
		this.sound = sound;
	}

	/**
	 * @return the time
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Integer time) {
		logger.info("Zeit wurde in Menu auf " + time + " gesetzt.");
		this.time = time;
	}

	
}
