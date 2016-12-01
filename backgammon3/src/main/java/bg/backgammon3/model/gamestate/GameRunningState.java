/**
 * 
 */
package bg.backgammon3.model.gamestate;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.*;

/**
 * 
 *
 */
public class GameRunningState extends GameState {
	

	public GameRunningState(Game game)
	{
		super(game);
		activateState();
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
		//game.setState(new MenuState(game, true));
		game.getMenuState().activateState();
		game.setState(game.getMenuState());
	}


	@Override
	public void gameIsFinished(Integer playerId) {
		game.finishGame(playerId);
		//GameState newState = new MenuState(game, false);
		if(Config.getInteger("loopGame") == 0) {
			game.getMenuState().activateState();
			game.setState(game.getMenuState());
		}
	}


	@Override
	public int accept(ModelVisitor gameObject) {
		return gameObject.visit(this);
	}


	@Override
	public int nextAccept(ModelVisitor gameObject) {
		if(!gameObject.getBusy()){
			return game.letPlayerHandle(gameObject);
		}
		return 0;
	}
	


}
