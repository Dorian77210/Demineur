import javax.swing.*;
import java.awt.*;

/**
  * The class <code>Session</code> is the global representation of the game
  * @version 0.4
  * @author Dorian Terbah, Thomas Béchet
**/

public class Session extends JPanel{

	/**
	  * The board of the actual game
	  * @see Board
	**/
	private Board board;

	/**
	  * The clock of the actual game
	  * @see VisualClock
	**/
	private VisualClock clock;
	private JButton saveButton;

	/**
	  * The model of the actual game
	  * @see GameModel
	**/
	private GameModel gameModel;

	/**
	  * The manager of the save of the previous game
	  * @see DataSession
	**/
	private DataSession dataSession;

	/**
	  * The controller of the actual session
	  * @see SessionController
	**/
	private SessionController controller;
	private JLabel starsView;
	
	/**
	  * Constructor of the Session
	  * @param dataSession The manager of the previous game
	  * @param gameModel The model of the actual game 
	**/
	public Session(DataSession dataSession, GameModel gameModel){
		super();

		this.setBackground(Color.BLACK);

		this.gameModel = gameModel;
		this.dataSession = dataSession;
		//Parameters the session
		this.saveButton = new JButton("Save And Quit");
		this.saveButton.setMargin(new Insets(0, 0, 0, 0));
		this.saveButton.setFont(this.saveButton.getFont().deriveFont(10.0f));
		this.saveButton.setFocusPainted(false);
		//Parameters the clock
		this.clock = new VisualClock();

		//Modifying window dimensions
		int sizeX = (int)(dataSession.getDimensions().getWidth() * 30.0);
		int sizeY = (int)(dataSession.getDimensions().getHeight() * 30.0);
		if(sizeX < 300){
			sizeX = (int)(dataSession.getDimensions().getWidth() * 50.0);
			sizeY = (int)(dataSession.getDimensions().getHeight() * 50.0);
		}
		sizeX += 100;
		sizeY += 100;
		this.gameModel.requestWindowDimension(sizeX, sizeY);

		//Add the controller to the session
		this.controller = new SessionController(this);
		this.saveButton.setActionCommand("SAVE");
		this.saveButton.addActionListener(this.controller);
		
		//Creating the board
		if(!dataSession.available()){
			this.board = new Board(this, dataSession.getDimensions(), dataSession.getMineCount());
			this.board.generate();
			this.clock.setElapsedTime(0);
		} else{
			this.board = this.dataSession.load(this);
			this.clock.setElapsedTime(dataSession.getElapsedTime());
		}

		//Modifying the starsView
		this.starsView = new JLabel("Mines: " + this.board.getMineRemaining());
		this.starsView.setHorizontalAlignment(JLabel.CENTER);
		this.starsView.setOpaque(true);
		this.starsView.setBorder(null);
		this.starsView.setFont(this.starsView.getFont().deriveFont(10.0f));
		this.starsView.setForeground(Color.RED);
		this.starsView.setBackground(Color.BLACK);
		

		//Layout configuration
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		this.clock.setPreferredSize(new Dimension(this.board.getPreferredSize().width / 4, 50));
		this.add(this.clock, c);
		
		c.gridx = 1;
		c.gridy = 0;
		this.saveButton.setPreferredSize(new Dimension(this.board.getPreferredSize().width / 2, 50));
		this.add(this.saveButton, c);
		
		c.gridx = 2;
		c.gridy = 0;
		this.starsView.setPreferredSize(new Dimension(this.board.getPreferredSize().width / 4, 50));
		this.add(this.starsView, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		this.add(this.board, c);

		//Starting the clock
		this.clock.start();
	}

	/**
	  * Update the number of stars
	  * @param stars The number of stars
	**/
	public void setStars(int stars){
		this.starsView.setText("Mines: " + stars);
	}

	/**
	  * Update the mode of the cell
	  * @param cell The cell that must to be updated
	**/
	public void changeCellMode(Cell cell){
		//Check the flags
		this.board.changeCellMode(cell);
		this.setStars(this.board.getMineRemaining());	
	}

	/**
	  * Permits to reveal the cell, to know if it's a mined cell and the state of the ending of the game
	  * @param cell The cell that was clicked
	**/
	public void clickCell(Cell cell){
		if(cell.isMined() && cell.isHidden()){
			this.board.showEverything();
			this.dataSession.destroy();
			cell.setIcon(new ImageIcon("Images/explosion.png"));
			this.clock.stop();
			this.showEndingMessage();
		} else{
			this.board.revealRecursive(cell);
		}

		if(this.board.checkVictory()){
			this.clock.stop();
			this.dataSession.destroy();
			//Using english buttons
			javax.swing.JComponent.setDefaultLocale(java.util.Locale.ENGLISH);
			JOptionPane message = new JOptionPane();
			javax.swing.JComponent.setDefaultLocale(java.util.Locale.ENGLISH ) ;
			int choice = message.showConfirmDialog(null,"You win ! Do you want to restart ?",
				null, JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(choice == JOptionPane.YES_OPTION){
				this.gameModel.newGame(this.dataSession.getMineCount(), this.dataSession.getDimensions());
			} else if(choice == JOptionPane.NO_OPTION){
				this.gameModel.changeState(ViewState.HOME);
			} else if(choice == JOptionPane.CLOSED_OPTION){
				this.gameModel.close();
			}
		}
	}

	/**
	  * Sshow an ending message if the player clicked on a mined cell
	**/
	public void showEndingMessage(){
		JOptionPane message = new JOptionPane();
		javax.swing.JComponent.setDefaultLocale(java.util.Locale.ENGLISH);
		int choice = message.showConfirmDialog(null,"You loose ! Do you want replay? ",null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(choice == JOptionPane.YES_OPTION){ 
			this.gameModel.newGame(this.dataSession.getMineCount(), this.dataSession.getDimensions());
		} else if(choice == JOptionPane.NO_OPTION){ //return at the home
			this.gameModel.changeState(ViewState.HOME);
		} else if(choice == JOptionPane.CLOSED_OPTION){
			this.gameModel.close();//close the window
		}
	}

	/**
	  * Save the actual game
	**/
	public void saveAndQuit(){
		this.dataSession.save(this.board, this.clock.getElapsedTime());
		this.gameModel.changeState(ViewState.HOME);
	}
}