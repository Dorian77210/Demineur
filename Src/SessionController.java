import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
  * The class <code>SessionController</code> controls the actions in the session
  * @version 0.2
  * @author Dorian Terbah, Thomas Béchet
**/

public class SessionController implements ActionListener{

	/**
	  * The actual session
	  * @see Session
	**/
	private Session session;

	/**
	  * Constructor of the SessionController
	  * @param session The actual session
	**/
	public SessionController(Session session){
		this.session = session;
	}

	@Override

	/**
	  * Permits to know if the save button was clicked
	  * @param event It's the event producted by the pression of the save's button
	**/
	public void actionPerformed(ActionEvent event){
		String action = event.getActionCommand();
		if(action == "SAVE") this.session.saveAndQuit();
	}
}