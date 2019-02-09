import javax.swing.*;
import java.awt.*;

/**
  * The class <code>HomeModel</code> is the model of the home in the MVC
  * @version 0.2
  * @author Dorian Terbah, Thomas Béchet
**/

public class HomeModel{

	/**
	  * The model of the actual game
	  * @see GameModel
	**/
	private GameModel gameModel;

	/**
	  * Constructor of the HomeModel
	  * @param gameModel The model of the actual game
	**/
	public HomeModel(GameModel gameModel){
		this.gameModel = gameModel;
	}

	/**
	  * Close the program and the gameWindow by the intermediary of the gameModel
	**/ 
	public void requestExit(){
		this.gameModel.close();
	}

	/**
	  * Change the state of the gameModel to start a new game
	**/
	public void requestNewGame(){
		this.gameModel.changeState(ViewState.SELECTION);
	}

	/**
	  * Continue previous game
	**/
	public void requestContinue(){
		this.gameModel.continueGame();
	}
}