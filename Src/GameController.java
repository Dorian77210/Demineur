import java.awt.event.*;

public class GameController implements WindowListener{

	/**
	  * The model of the game
	  * @see GameModel
	**/
	private GameModel model;

	/**
	  * Constructor of the GameController
	  * @param model it's the model of the actual game
	**/
	public GameController(GameModel model){
		this.model = model;
	}

	@Override
	public void windowActivated(WindowEvent e){}	
	@Override
	public void windowClosed(WindowEvent e){}
	@Override
	/**
	  * @param WindowEvent It's the event products by the window
	  *			permits to close the window and the program when the frame was closed
	**/
	public void windowClosing(WindowEvent e){
		this.model.close();
	}
	@Override
	public void windowDeactivated(WindowEvent e){}
	@Override
	public void windowDeiconified(WindowEvent e){}
	@Override
	public void windowIconified(WindowEvent e){}
	@Override
	public void windowOpened(WindowEvent e){}
}