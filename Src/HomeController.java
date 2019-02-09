import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
  * The class <code>HomeController</code> control the actions present in the home
  * @version 0.2
  * @author Dorian Terbah, Thomas Béchet
**/

public class HomeController implements ActionListener{

	/**
	  * The model of the actual home
	  * @see HomeModel
	**/
	private HomeModel homeModel;

	/**
	  * Constructor of the HomeController
	  * @param homeModel The model of the actual hoem
	**/
	public HomeController(HomeModel homeModel){
		this.homeModel = homeModel;
	}

	@Override

	/**
	  * Permits to know what is the clicked button in the home
	  * @param event It's the event producted by the pression on one of the buttons presented on the home
	**/
	public void actionPerformed(ActionEvent event){
		String action = event.getActionCommand();
		switch(action){
			case "EXIT":
				this.homeModel.requestExit();
			break;
			case "NEW":
				this.homeModel.requestNewGame();
			break;
			case "CONTINUE":
				this.homeModel.requestContinue();
			break;
			default:
			break;
		}
	}
}