import javax.swing.*;
import java.awt.*;

/**
  * The class <code>Board</code> is the representation of the grid of cells
  * @version 0.3
  * @author Dorian Terbah, Thomas Béchet
**/

public class Board extends JPanel{

	/**
	  * Represent all of the cells of the board
	  * @see Cell
	**/
	private Cell cells[][];
	private Dimension dimension;

	/**
	  * The controller of the board
	  * @see BoardController
	**/
	private BoardController controller;

	private int mineCount;
	private int cellRevealed;
	private int mineRemaining;

	/**
	  * Contructor of the Board
	  * @param session It's the session of the actual game
	  * @param dimension It's the dimension of the board
	  * @param mineCount It's the number of mines that they have to be present on the board
	**/
	public Board(Session session, Dimension dimension, int mineCount){
		super();

		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout((int)dimension.getHeight(), (int)dimension.getWidth()));
		this.dimension = dimension;

		this.mineCount = mineCount;
		this.cellRevealed = 0;
		this.mineRemaining = this.mineCount;

		//Giving the preferred size related to the height and width of the board
		int sizeX = (int)(dimension.getWidth() * 30.0);
		int sizeY = (int)(dimension.getHeight() * 30.0);
		if(sizeX < 300){
			sizeX = (int)(dimension.getWidth() * 50.0);
			sizeY = (int)(dimension.getHeight() * 50.0);
		}
		Dimension boardDimension = new Dimension(sizeX, sizeY);
		this.setPreferredSize(boardDimension);
		this.setMinimumSize(boardDimension);
		this.setMaximumSize(boardDimension);
		
		this.cells = new Cell[(int)this.dimension.getHeight()][(int)this.dimension.getWidth()];
		this.controller = new BoardController(session);
		this.addMouseListener(this.controller);

		//Creating cells
		for(int i = 0; i < (int)this.dimension.getHeight(); i++){
			for(int j = 0; j < (int)this.dimension.getWidth(); j++){
				this.cells[i][j] = new Cell(new Point(j, i));
				this.cells[i][j].addActionListener(this.controller);
				this.cells[i][j].addMouseListener(this.controller);
				this.add(this.cells[i][j]);
			}
		}
	}

	/**
	  * permits to create the mined cells
	**/
	public void generate(){
		RandomPosition random = new RandomPosition((int)this.dimension.getWidth(), (int)this.dimension.getHeight());
		//Adding mines
		for(int i = 0; i < this.mineCount; i++){
			Point point = random.nextPosition();
			//Mine cell at the generated position
			this.cells[(int)point.getY()][(int)point.getX()].mine();
		}
	}

	/**
	  * Get the cell at the position (x,y)
	  * @param x It's the abscissa in the cell's table
	  * @param y It's the ordinate in the cell's table
	  * @return the cell at the position (x,y)
	**/
	public Cell cellAtPosition(int x, int y){
		return this.cells[y][x];
	}

	/**
	  * Get the cell in the top according to the position
	  * @param position It's the position of the actual cell 
     * @return the top cell according to the position if it not exceed the cell's table, else null
	**/
	public Cell getTopCell(Point position){
		if((int)position.getY() > 0)
			return this.cells[(int)position.getY() - 1][(int)position.getX()];
		else 
			return null;
	}

	/**
	  * Get the cell in the bottom according to the position
	  * @param position It's the position of the actual cell
	  * @return bottom cell according to the position if it not exceed the cell's table, else null
	**/
	public Cell getBottomCell(Point position){
		if((int)position.getY() < (int)this.dimension.getHeight() - 1)
			return this.cells[(int)position.getY() + 1][(int)position.getX()];
		else
			return null;
	}

	/**
	  * Get the cell at the right according to the postion
	  * @param position It's the position of the atual cell
	  * @return the right cell according to the position if it not exceed the cell's table, else null
	**/
	public Cell getRightCell(Point position){
		if((int)position.getX() < (int)(this.dimension.getWidth() - 1))
			return this.cells[(int)position.getY()][(int)position.getX() + 1];
		else 
			return null;
	}

	/**
	  * Get the cell at the left according to the position
	  * @param position It's the position of the actual cell
	  * @return left cell according to the position if it not exceed the cell's table, else null
	**/
	public Cell getLeftCell(Point position){
		if((int)position.getX() > 0)
			return this.cells[(int)position.getY()][(int)position.getX() - 1];
		else
			return null;
	}

	/**
	  * Get the cell at the top left according to the position
	  * @param position It's the posiiton of the actual cell
	  * @return top left cell according to the position if it not exceed the cell's table, else null
	**/
	public Cell getTopLeftCell(Point position){
		if((int)position.getX() > 0 && (int)position.getY() > 0)
			return this.cells[(int)position.getY() - 1][(int)position.getX() - 1];
		else
			return null;
	}

	/**
	  * Get the cell at the top right according to the position
	  * @param position It's the position of the actual cell
	  * @return the top right cell according to the position if it not exceed the cell's table, else null
	**/
	public Cell getTopRightCell(Point position){
		if((int)position.getX() < (int)(this.dimension.getWidth() - 1) && (int)position.getY() > 0)
			return this.cells[(int)position.getY() - 1][(int)position.getX() + 1];
		else
			return null;
	}

	/**
	  * Get the cell at the bottom left according to the position
	  * @param position It's the position of the actual cell
	  * @return bottom left cell according to the position if it not exceed the cell's table, else null
	**/
	public Cell getBottomLeftCell(Point position){
		if((int)position.getX() > 0 && (int)position.getY() < (int)this.dimension.getHeight() - 1)
			return this.cells[(int)position.getY() + 1][(int)position.getX() - 1];
		else
			return null;
	}

	/**
	  * Get the cell at the right bottom according to the position
	  * @param position It's the position of the actual cell
	  * @return the cell at the right bottom of the actual position if it not exceed the cell's table, else null
	**/
	public Cell getBottomRightCell(Point position){
		if((int)position.getX() < (int)this.dimension.getWidth() - 1 && (int)position.getY() < (int)this.dimension.getHeight() - 1)
			return this.cells[(int)position.getY() + 1][(int)position.getX() + 1];
		else
			return null;
	}

	/**
	  * Permimts to know how many mines are present around the cell (the 8 cells around it)
	  * @param cell The cell that was clicked
	  * @return the number of the neightbours of the cell
	**/
	public int getMinedNeighbourCount(Cell cell){
		int count = 0;
		Point position = cell.getPosition();

		Cell neighbour = null;

		neighbour = this.getTopLeftCell(position);
		if(neighbour != null && neighbour.isMined()) count++;

		neighbour = this.getTopCell(position);
		if(neighbour != null && neighbour.isMined()) count++;
		
		neighbour = this.getTopRightCell(position);
		if(neighbour != null && neighbour.isMined()) count++;

		neighbour = this.getRightCell(position);
		if(neighbour != null && neighbour.isMined()) count++;

		neighbour = this.getBottomRightCell(position);
		if(neighbour != null && neighbour.isMined()) count++;

		neighbour = this.getBottomCell(position);
		if(neighbour != null && neighbour.isMined()) count++;

		neighbour = this.getBottomLeftCell(position);
		if(neighbour != null && neighbour.isMined()) count++;

		neighbour = this.getLeftCell(position);
		if(neighbour != null && neighbour.isMined()) count++;

		return count;
	}

	/**
	  * Reveal all cells that they have to be revealed according to one cell
	  * @param cell The cell that was clicked
	**/
	public void revealRecursive(Cell cell){
		Point position = cell.getPosition();

		if(cell.isHidden()){ //Affect only hidden cells (not flagged and suspicious)
			int minedNeighbourCount = this.getMinedNeighbourCount(cell);
			this.revealOnce(cell);
			if(minedNeighbourCount == 0){
				
				//For each existing neighbour, we reveal it with recursion
				Cell neighbour = null;

				neighbour = this.getTopLeftCell(position);
				if(neighbour != null) this.revealRecursive(neighbour);

				neighbour = this.getTopCell(position);
				if(neighbour != null) this.revealRecursive(neighbour);
				
				neighbour = this.getTopRightCell(position);
				if(neighbour != null) this.revealRecursive(neighbour);

				neighbour = this.getRightCell(position);
				if(neighbour != null) this.revealRecursive(neighbour);

				neighbour = this.getBottomRightCell(position);
				if(neighbour != null) this.revealRecursive(neighbour);

				neighbour = this.getBottomCell(position);
				if(neighbour != null) this.revealRecursive(neighbour);

				neighbour = this.getBottomLeftCell(position);
				if(neighbour != null) this.revealRecursive(neighbour);

				neighbour = this.getLeftCell(position);
				if(neighbour != null) this.revealRecursive(neighbour);
			}
		}
	}

	/**
	  * Reveal just one cell
	  * @param cell The cell that was clicked
	**/
	public void revealOnce(Cell cell){
		cell.setState(Cell.REVEALED);
		int count = this.getMinedNeighbourCount(cell);
		if(count != 0) cell.setMinedNeighbourCount(count);
		this.cellRevealed++;
	}

	/**
	  * Change the mode of the cell
	  * @param cell The cell that was clicked with the right button of the mouse
	**/
	public void changeCellMode(Cell cell){
		if(cell.isHidden()){
			cell.setState(Cell.FLAGGED);
			this.mineRemaining--;
		} else if(cell.isFlagged()){
			cell.setState(Cell.SUSPICIOUS);
			this.mineRemaining++;
		} else if(cell.isSuspicious()){ 
			cell.setState(Cell.HIDDEN);
		}
	}

	//Retrieve remaining mine count

	/**
	  * Get the nmber of mines that are not revealded
	  * @return the number of the mines remaining
	**/
	public int getMineRemaining(){
		return this.mineRemaining;
	}

	/**
	  * Get the number of the mines present in the board
	  * @return the number of mines
	**/
	public int getMineCount(){
		return this.mineCount;
	}

	/**
	  * Load the cell according to its raw data and its position
	  * @param raw The data that represent one cell
	  * @param x The abscissa in the cell's tabke
	  * @param y The ordinate in the cell's table
	**/
	public void loadCell(byte raw, int x, int y){
		Cell cell = this.cellAtPosition(x, y);
		Cell.decodeCell(raw, cell);
		if(cell.isFlagged()){
			this.mineRemaining--;
		}
	}

	/**
	  * Permits to know if the player have finish the game without trigger a mined 
	  * @return true if the player won, else false
	**/
	public boolean checkVictory(){
		int total = (int)(this.dimension.getWidth() * this.dimension.getHeight()) - this.cellRevealed;
		if(total == this.mineCount) return true;
		return false;
	}

	/**	
	  * Reveal the mined and hidden cells and the not hidden and not mines cells
	**/
	public void showEverything(){
		for(int i = 0; i < (int)this.dimension.getHeight(); i++){
			for(int j = 0; j < (int)this.dimension.getWidth(); j++){
				if((this.cells[i][j].isMined() && this.cells[i][j].isHidden()) || (!this.cells[i][j].isMined() && !this.cells[i][j].isHidden())) this.cells[i][j].setState(Cell.REVEALED);
			}
		}
	}
}