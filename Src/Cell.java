import java.awt.*;
import javax.swing.*;

/**
  * The class <code>Cell</code> is the representation of a case
  * @version 0.4
  * @author Dorian Terbah, Thomas Béchet
**/

public class Cell extends JButton{

	/**
	  * Colors used for the cell with mined neightbours
	**/
	private static final Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.ORANGE, Color.MAGENTA, Color.YELLOW, Color.PINK};
	//game flags

	/**
	  * Constant used to represent the hidden state of the cells
	**/
	public static final int HIDDEN = 0;

	/**
	  * Constant used to represent the revealed state of the cells
	**/
	public static final int REVEALED = 1;

	/**
	  * Constant used to represent the flagged state of the cells
	**/
	public static final int FLAGGED = 2;

	/**
	  * Constant used to represent the suspicous state of the cells
	**/
	public static final int SUSPICIOUS = 3;

	//Needed for raw data

	/**
	  * Constant used to represent the mined state of the cells for the writing and reading data
	**/
	private static final byte MINED_MASK = 0x4;

	/**
	  * Constant used to represent the basics states of the cells for the writing and the reading of data
	**/
	private static final byte STATES_MASK = 0x3;

	private boolean mined = false;
	private int state = Cell.HIDDEN;
	
	private Point position;
	private String[] images = {"Images/flag.png", "Images/suspicious.png", "Images/bomb.png"};

	/**
	  * Constructor of the Cell
	  * @param position Position of the cell in the cell's table of the board
	**/
	public Cell(Point position){
		super();
		this.position = position;
		this.setFocusPainted(false);
		this.setRolloverEnabled(false);
		this.refreshAppearance();
	}

	/**
	  * Change the state of this cell
	  * @param state It's the new state for this cell
	**/
	public void setState(int state){
		this.state = state;
		this.refreshAppearance();
	}

	//Updating visual when needed
	/**
	  * Change the appearance of this cell according to its actual state
	**/
	public void refreshAppearance(){
		switch(this.state){
			case HIDDEN:
				this.setEnabled(true);
				this.setBackground(Color.GRAY);
				this.setIcon(null);
			break;
			case REVEALED:
				this.setEnabled(true);
				this.setFocusPainted(false);
				this.setBackground(Color.WHITE);
				if(this.isMined())
					this.setIcon(new ImageIcon(this.images[2])); //Adding mine icon
				else
					this.setIcon(null);
			break;
			case SUSPICIOUS:
				this.setEnabled(false);
				this.setBackground(Color.GRAY);
				this.setIcon(new ImageIcon(this.images[1]));
			break;
			case FLAGGED:
				this.setEnabled(false);
				this.setBackground(Color.GRAY);
				this.setIcon(new ImageIcon(this.images[0]));
			break;		
		}
	}

	/**
	  * Actualize the number of mines that are neightbours of this cell
	  * @param count It's the number of mineds neightbours of this cell
	**/
	public void setMinedNeighbourCount(int count){
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setText("" + count);
		this.setForeground(Cell.colors[count - 1]);
	}

	/**
	  * Get the position of the cell in the table of cells in the board
	  * @return The position of this cell
	**/
	public Point getPosition(){
		return this.position;
	}

	//Set the cell as a mined cell
	/**
	  * Set the cell as a mined cell
	**/
	public void mine(){
		this.mined = true;
	}

	/**
	  * Permits to know if this cell is mined
	  * @return True if this cell is mined, else false
	**/
	public boolean isMined(){
		return this.mined;
	}

	//Getting basics states
	/**
	  * Permits to know if this cell is hidden
	  * @return True if the state of this cell is hidden, else false
	**/
	public boolean isHidden(){
		return (this.state == Cell.HIDDEN);
	}

	/**
	  * Permits to know if this cell is revealed
	  * @return True if this cell is revealed, else false
	**/
	public boolean isRevealed(){
		return (this.state == Cell.REVEALED);
	}

	/**
	  * Permits to know if this cell is flagged
	  * @return True if the cell is flagged, else false
	**/
	public boolean isFlagged(){
		return (this.state == Cell.FLAGGED);
	}

	/**
	  * Permits to know if this cell is suspicious
	  * @return True if the state of this cell is suspicious, else false
	**/
	public boolean isSuspicious(){
		return (this.state == Cell.SUSPICIOUS);
	}

	//Needed for writing

	/**
	  * Code the cell for writing
	  * @param cell The cell that must to be coded
	  * @return The result of the coding of the cell
	**/
	public static byte codeCell(Cell cell){
		byte raw = (byte)cell.state;
		if(cell.isMined()) raw = (byte)(raw | Cell.MINED_MASK);
	
		return raw;
	}

	/**
	  * @param data The representation of the cell
	  * @param cell The cell that equals at the data
	  * Recreate the state of the cell
	**/
	public static void decodeCell(byte data, Cell cell){
		if((data & Cell.MINED_MASK) != 0) cell.mine();
		cell.setState((int)(data & Cell.STATES_MASK));
	}
}
