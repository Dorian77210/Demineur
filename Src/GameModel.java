import javax.swing.*;
import java.awt.*;

/**
  * The class <code>GameModel</code> controls the window of the game
  * @version 0.3
  * @author Dorian Terbah, Thomas Béchet
**/

public class GameModel{

	/**
	  * It's the representation of the game
	  * @see GameWindow
	**/
	private GameWindow gameWindow;
	
	/**
	  * It's the representation of the home
	  * @see HomeView
	**/
	private HomeView homeView;

	/**
	  * It's the representation of the selection
	  * @see SelectionView
	**/
	private SelectionView selectionView;
	
	/**
	  * It represents the actual view of the program
	  * @see ViewState
	**/
	private ViewState viewState;

	/**
	  * It's the representation of the real game
	  * @see Session
	**/
	private Session session;

	/**
	  * It represents the previous save game
	  * @see DataSession
	**/
	private DataSession dataSession; //Unique session

	/**
	  * Constructor of the GameModel
	  * @param gameWindow it's the representation of the game 
	  * @see GameWindow
	**/
	public GameModel(GameWindow gameWindow){
		this.gameWindow = gameWindow;
		
		this.homeView = new HomeView(this);
		this.selectionView = new SelectionView(this);
		this.session = null;

		//Try to find an existing session
		this.dataSession = new DataSession("save");

		this.changeState(ViewState.HOME);
	}

	/**
	  * @param viewState
	  *			Change the actual view of the gameWindow
	**/
	public void changeState(ViewState viewState){
		this.viewState = viewState;
		
		switch(this.viewState){
			case HOME:
				this.requestWindowDimension(800, 500);
				this.homeView.makeContinuable(this.dataSession.exists());
				this.gameWindow.setCurrentView(this.homeView);
				break;
			case SELECTION:
				this.requestWindowDimension(800, 500);
				this.gameWindow.setCurrentView(this.selectionView);
				break;
			case SESSION:
				this.gameWindow.setCurrentView(this.session);
				break;
		}
	}

	/**
	  * @param mineCount the number of mines
	  * @param dimension the dimension of the board
	  * 			Create a new session according to the mineCount and the dimension
	**/
	public void newGame(int mineCount, Dimension dimension){
		this.dataSession.create(dimension, mineCount);
		this.session = new Session(this.dataSession, this); //Old session is destroyed

		//Changing view
		this.changeState(ViewState.SESSION);
	}

	/**
	  * Continue the previous game if it exists
	**/
	public void continueGame(){
		if(this.dataSession.exists()){
			this.session = new Session(this.dataSession, this);
		} else{
			System.out.println("No session sorry :(");
			System.exit(0);
		}

		//Changing view
		this.changeState(ViewState.SESSION);
	}

	/**
	  * Close the gameWindow and the program
	**/
	public void close(){
		if(this.viewState == ViewState.SESSION){
			this.session.saveAndQuit();
		}

		this.gameWindow.dispose(); //close the window
		System.exit(0); //close the program
	}

	/**
	  * @param width the width for the dimension of the window
	  * @param height the height for the dimension of the window
	  * Set the sizes of the window
	**/
	public void requestWindowDimension(int width, int height){
		//Modifying size and default dimensions
		this.gameWindow.setMinimumSize(new Dimension(width, height));
		this.gameWindow.setPreferredSize(new Dimension(width, height));
		this.gameWindow.setSize(width, height);
	}
}