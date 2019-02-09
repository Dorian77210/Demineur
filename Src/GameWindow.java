import javax.swing.*;
import java.awt.*;

/**
  * The class <code>GameWindow</code> is the support on which is passing the program
  * @version 0.2
  * @author Dorian Terbah, Thomas Béchet
**/

public class GameWindow extends JFrame{

	private JPanel currentView;

	/**
	  * The model of the game
	  * @see GameModel
	**/
	private GameModel gameModel;

	/**
	  * The controller of the game
	  * @see GameController
	**/
	private GameController controller;

	/**
	  * Constructor of the GameWindow
	**/
	public GameWindow(){
		super();
		//Parameters for the gameWindow
		this.setTitle("Minesweeper");
		this.setResizable(false);
		this.setMinimumSize(new Dimension(1024, 576));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.currentView = new JPanel();//default
		this.gameModel = new GameModel(this);

		this.controller = new GameController(this.gameModel);
		this.addWindowListener(this.controller);
	}

	//Change the appareance of the game window

	/**
	  * @param newView It's the new view for the window
	  *			Change the current view of the game
	**/
	public void setCurrentView(JPanel newView){
		this.remove(this.currentView);
		this.currentView = newView;
		this.add(this.currentView, BorderLayout.CENTER);
		this.repaint();
		this.validate();
	}
}