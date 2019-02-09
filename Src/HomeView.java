import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

/**
  * The class <code>HomeView</code> is the representation of the home
  * @version 0.3
  * @author Dorian Terbah, Thomas Béchet
**/

public class HomeView extends JPanel{

	/**
	  * The controller of the home
	  * @see HomeController
	**/
	private HomeController controller;

	/**
	  * The model of the home
	  * @see HomeModel
	**/
	private HomeModel model;

	private JButton exit;
	private JButton continueGame;
	private JButton newGame;

	private Image image;

	/**
	  * Constructor of the HomeView
	  * @param gameModel It's the model of the actual game
	**/
	public HomeView(GameModel gameModel){
		super();

		//Creating JButtons
		this.continueGame = new JButton("Continue");
		this.continueGame.setActionCommand("CONTINUE");
		this.newGame = new JButton("New game");
		this.newGame.setActionCommand("NEW");
		this.exit = new JButton("Exit");
		this.exit.setActionCommand("EXIT");

		this.model = new HomeModel(gameModel);
		this.controller = new HomeController(this.model);

		this.exit.addActionListener(this.controller);
		this.continueGame.addActionListener(this.controller);
		this.newGame.addActionListener(this.controller);


		//UI Configuration///////////////////////////////
		this.setBackground(Color.WHITE);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 15, 0, 0);
	
		this.continueGame.setPreferredSize(new Dimension(200, 50));
		this.continueGame.setFont(new Font("Arial", Font.BOLD, 30));
		this.continueGame.setFocusPainted(false);
		this.continueGame.setBackground(Color.decode("#e0000a"));
		this.continueGame.setForeground(Color.WHITE);
		this.continueGame.setFocusPainted(false);
		this.continueGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.newGame.setPreferredSize(new Dimension(200, 50));
		this.newGame.setMargin(new Insets(0, 0, 0, 0));
		this.newGame.setFont(new Font("Arial", Font.BOLD, 30));
		this.newGame.setBackground(Color.decode("#e0000a"));
		this.newGame.setForeground(Color.WHITE);
		this.newGame.setFocusPainted(false);
		this.newGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		this.exit.setPreferredSize(new Dimension(200, 40));
		this.exit.setFont(new Font("Arial", Font.PLAIN, 20));
		this.exit.setBackground(Color.decode("#e0000a"));
		this.exit.setForeground(Color.WHITE);
		this.exit.setFocusPainted(false);
		this.exit.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.add(this.continueGame, c);
		this.add(this.newGame, c);
		this.add(this.exit, c);

		//Load background image
		this.image = Toolkit.getDefaultToolkit().createImage("Images/dangerBackground.jpg");
		this.repaint();
	}

	/**
	  * @param toggle It's the confirmation that an game already exists or not
	  * show the continueGame button if it already exists a game
	**/
	public void makeContinuable(boolean toggle){
		if(toggle){
			this.continueGame.setVisible(true);
			this.continueGame.setEnabled(true);
			this.continueGame.addActionListener(this.controller);
		} else{
			this.continueGame.setVisible(false);
			this.continueGame.setEnabled(false);
			this.continueGame.removeActionListener(this.controller);
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		g.drawImage(this.image, 0, 0, this.getSize().width, this.getSize().height, this);
	}
}