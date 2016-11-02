/**
 * 
 */
package bg.backgammon3.model.gamestate;

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
	

	/**
	 * Hier kann false zurückgegeben werden da das Menu sowieso im Moment unsichtbar ist.
	 */
	public boolean gameCanContinue()
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
	public void quitGame() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Das Menu anzeigen.
	 */
	@Override
	public void showMenu() {
		game.setState(new MenuState(game));
	}
}
