/**
 * 
 */
package bg.backgammon3.model.gamestate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;
import bg.backgammon3.model.action.*;

/**
 * @author philipp
 *
 */
public class MenuState extends GameState {
	private Logger logger = LogManager.getLogger(MenuState.class);
	private boolean gameCanContinue = false;

	public MenuState(Game game)
	{
		super(game);
		game.addActionAtBeginn(new ShowMenu());
	}
	
	public MenuState(Game game, boolean gameCanContinue)
	{
		super(game);
		this.gameCanContinue = gameCanContinue;
		if(Config.getInteger("loopGame") == 1) {
			startGame(new Menu());
		} else {
			game.addActionAtBeginn(new ShowMenu());
		}
	}
	
	@Override
	public void handle(GameObject gameObject, boolean busy) {
		if (gameObject instanceof StartButton) {
			startGame(((StartButton) gameObject).getMenu());
		} else if (gameObject instanceof ContinueButton) {
			continueGame(((ContinueButton) gameObject).getMenu());
		} else if (gameObject instanceof QuitButton) {
			quitGame();
		} else {
			if(!busy){
				game.letPlayerHandle(gameObject);
			}
		}
	}
	
	public boolean getGameCanContinue()
	{
		return gameCanContinue;
	}


	private void startGame(Menu menu) {
		// Spielfeld anzeigen und Menu Verstecken
		game.addActionAtBeginn(new StartGame());
		
		logger.info("MenuState: Spiel starten wurde gedrückt");
		if(menu.getColor() == 0) {
			Config.setInteger("firstPlayer", 0);
			Config.setInteger("player0Type", 0);
			Config.setInteger("player1Type", menu.getDifficulty());
		} else if (menu.getColor() == 1) {
			Config.setInteger("firstPlayer", 1);
			Config.setInteger("player1Type", 0);
			Config.setInteger("player0Type", menu.getDifficulty());
		} 
		Config.setInteger("playSound", menu.getSound()?1:0);
		Config.setInteger("maximumTime", menu.getTime());
		
		// Spiel Initialisieren
		game.initBoard();
		
		// Zustand Ändern
		game.setState(new GameRunningState(game));
		
	}


	private void continueGame(Menu menu) {
		Config.setInteger("playSound", menu.getSound()?1:0);

		// Zustand Ändern
		game.setState(new GameRunningState(game));

		game.addActionAtBeginn(new UpdateSound());
		game.addActionAtBeginn(new ContinueGame());
		
	}


	private void quitGame() {
		game.addActionAtBeginn(new Quit());
	}


	/**
	 * Das Menu wird bereits angezeigt, also soll nichts unternommen werden.
	 */
	@Override
	public void showMenu() {
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void continueGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitThisGame() {
		// TODO Auto-generated method stub
		
	}
}
