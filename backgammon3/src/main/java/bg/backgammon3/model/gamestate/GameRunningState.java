/**
 * 
 */
package bg.backgammon3.model.gamestate;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;

/**
 * @author philipp
 *
 */
public class GameRunningState extends GameState {

	public GameRunningState(Game game)
	{
		super(game);
	}
	

	@Override
	public void handle(GameObject gameObject, boolean busy) {
		if (gameObject instanceof MenuButton) {
			showMenu();
		} else {
			if(!busy){
				game.letPlayerHandle(gameObject);
			}
		}
	}
	
	/**
	 * Hier kann false zurückgegeben werden da das Menu sowieso im Moment unsichtbar ist.
	 */
	public boolean getGameCanContinue()
	{
		return false;
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


	/**
	 * Das Menu anzeigen.
	 */
	@Override
	public void showMenu() {
		game.setState(new MenuState(game, true));
	}


	@Override
	public void gameIsFinished(Integer playerId) {
		game.finishGame(playerId);
		GameState newState = new MenuState(game, false);
		if(Config.getInteger("loopGame") == 0) {
			game.setState(newState);
		}
	}


}
