/**
 * 
 */
package bg.backgammon3.model.player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;

/**
 * @author philipp
 *
 */
public class AIHelper {
	private Logger logger = LogManager.getLogger(AIHelper.class);
	private int currentNumberOfGames = 0;
	private int currentNumberOfWins = 0;
	private int id;
	private int inGameId;
	private boolean optimize = false;
	private int numberOfRules = 0;
	private int numberOfSteps = 1;
	private int precisionLevel = 0;
	
	public AIHelper() {
		this.id = 0;
		this.inGameId = 0;
	}
	
	public AIHelper(int id, int inGameId) {
		this.id = id;
		this.inGameId = inGameId;
		init();
	}
	
	public void init() {
		if(Config.getInteger("ai" + id + "Optimize") == 0) {
			optimize = false;
		} else {
			optimize = true;
		}
		numberOfRules = countRules();
		if(Config.getInteger("ai" + id + "ResetBest") == 1) {
			resetBest();
			Config.forceSetInteger("ai" + id + "ResetBest", 0);
		}
		if(Config.getInteger("ai" + id + "ResetCurrent") == 1) {
			resetCurrent();
			Config.forceSetInteger("ai" + id + "ResetCurrent", 0);
		}
		numberOfSteps = Config.getInteger("ai" + id + "Step");
		precisionLevel = Config.getInteger("ai" + id + "CurrentPrecisionLevel");
	}
	
	public int getRule(int i) {
		if(optimize) {
			return Config.getInteger("ai" + id + "CurrentRule" + i);
		} else {
			return Config.getInteger("ai" + id + "Rule" + i);
		}
	}
	
	public boolean isOptimizationFinished() {
		return precisionLevel >= Config.getInteger("ai" + id + "PrecisionLevel");
	}
	
	public void gameFinished(int idWinner) {
		if(!optimize) {
			return;
		}
		
		boolean won = inGameId == idWinner ? true : false;
		currentNumberOfGames++;
		if(won) {
			currentNumberOfWins++;
		}
		
		// Falls wir am ende einer Spielserie sind
		if(currentNumberOfGames >= Config.getInteger("ai" + id + "NumberOfGames")) {
			// Prüfen ob es sich um die Beste Serie Handelt
			if(currentNumberOfWins > Config.getInteger("ai" + id + "BestResult")) {
				Config.forceSetInteger("ai" + id + "BestResult", currentNumberOfWins);
				for(int i = 0; i < numberOfRules; i++) {
					Config.forceSetInteger("ai" + id + "BestRule" + i, getRule(i));
				}
			}
			logEndSeries();
			currentNumberOfWins = 0;
			currentNumberOfGames = 0;
			setNextParameterSet();
		}
	}
	
	private void logEndSeries() {
		double ratio = ((double) currentNumberOfWins) / ((double) currentNumberOfGames) * 100;
		String log = "[AIHELPER] Game Series Over. Precision Level: " + precisionLevel + ". "+ String.format("%5.1f", ratio) + "% gewonnen bei den Regeln ";
		for(int i = 0; i < numberOfRules; i++) {
			log += String.format("%4d", getRule(i)) + ", ";
		}
		log = log.substring(0, log.length() - 2) + ".";
		logger.info(log);
	}

	private void setNextParameterSet() {
		currentNumberOfGames = 0;
		for(int i = 0; i < numberOfRules; i++) {
			int upperBound = getCurrentUpperBound(i);
			int lowerBound = getCurrentLowerBound(i);
			if(getRule(i) < upperBound) {
				int newRule = getRule(i) + (upperBound - lowerBound) / (numberOfSteps - 1);
				newRule = newRule > upperBound ? upperBound : newRule;
				
				setRule(i, newRule);
				
				resetLowerParameters(i);
				
				// Wir wollen nur einen wert erhöhen
				return;
			}
		}
		// Wir haben den gesamten Parameterraum abgesucht.
		loadNewCurrentBounds();
	}
	
	private void loadNewCurrentBounds() {
		// Alle optimierungsstufen Durchlaufen
		if(precisionLevel >= Config.getInteger("ai" + id + "PrecisionLevel")) {
			optimize = false;
			Config.forceSetInteger("aiOptimizationFinished", 1);
			return;
		}
		
		precisionLevel++;
		logger.info("[AIHELPER][PRECISION] Precision level " + precisionLevel + "reached.");
		Config.forceSetInteger("ai" + id + "CurrentPrecisionLevel", precisionLevel);
		for(int i = 0; i < numberOfRules; i++) {
			int upperBound = getCurrentUpperBound(i);
			int lowerBound = getCurrentLowerBound(i);
			int boundSize = (upperBound - lowerBound) / numberOfSteps;
			int bestRule = getBestRule(i) == upperBound ? getBestRule(i) - 1 : getBestRule(i);
			int segment = (bestRule - lowerBound) / boundSize;
			int newLowerBound = segment * boundSize + lowerBound;
			int newUpperBound = (segment + 1) * boundSize + lowerBound;

			newLowerBound = newLowerBound < lowerBound ? lowerBound : newLowerBound;
			newUpperBound = newUpperBound > upperBound ? upperBound : newUpperBound;
			Config.forceSetInteger("ai" + id + "Rule" + i + "CurrentLowerBound", newLowerBound);
			Config.forceSetInteger("ai" + id + "Rule" + i + "CurrentUpperBound", newUpperBound);
		}
		resetLowerParameters(numberOfRules);
	}
	
	private int getBestRule(int i) {
		return Config.getInteger("ai" + id + "BestRule" + i);
	}
	
	
	private void resetLowerParameters(int rule) {
		for(int i = 0; i < rule; i++) {
			setRule(i, getCurrentLowerBound(i));
		}
	}
	
	private int getCurrentUpperBound(int i) {
		return Config.getInteger("ai" + id + "Rule" + i + "CurrentUpperBound");
	}
	

	private int getCurrentLowerBound(int i) {
		return Config.getInteger("ai" + id + "Rule" + i + "CurrentLowerBound");
	}
	

	private int getUpperBound(int i) {
		return Config.getInteger("ai" + id + "Rule" + i + "UpperBound");
	}
	

	private int getLowerBound(int i) {
		return Config.getInteger("ai" + id + "Rule" + i + "LowerBound");
	}
	
	public int getBottom() {
		return Config.getInteger("ai" + id + "LowerBound");
	}
	
	private void setRule(int i, int value) {
		Config.forceSetInteger("ai" + id + "CurrentRule" + i, value);
	}
	
	private int countRules() {
		int i = 0;
		while(Config.getInteger("ai" + id + "Rule" + i) != null) i++;
		return i;
	}
	
	private void resetBest() {
		Config.forceSetInteger("ai" + id + "BestResult", 0);
		for(int i = 0; i < numberOfRules; i++) {
			Config.forceSetInteger("ai" + id + "BestRule" + i, getLowerBound(i));
		}
	}
	
	private void resetCurrent() {
		for(int i = 0; i < numberOfRules; i++) {
			Config.forceSetInteger("ai" + id + "Rule" + i + "CurrentLowerBound", getLowerBound(i));
			Config.forceSetInteger("ai" + id + "Rule" + i + "CurrentUpperBound", getUpperBound(i));
			Config.forceSetInteger("ai" + id + "Rule" + i + "CurrentPrecisionLevel", getUpperBound(i));
			setRule(i, getCurrentLowerBound(i));
		}
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInGameId(int inGameId) {
		this.inGameId = inGameId;
	}
	
}
