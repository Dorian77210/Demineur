import javax.swing.*;
import java.awt.*;

/**
  * The class <code>SelectionModel</code> is the model of the selection
  * @version 0.4
  * @author Dorian Terbah, Thomas Béchet
**/

public class SelectionModel{
	//Constants for the limit of rows and columns
	/**
	  * Constant used to have a maximum limit of the number of columns and rows
	**/
	static final int SIZE_MAX = 30;
	/**
	  * Constant used to have a minimum limit of the number of columns and rows
	**/
	static final int SIZE_MIN = 4;
	
	/**
	  * Constant used if the value of the columns, rows or difficulty is not an integer
	**/
	static final int BAD_VALUE = -1;

	private int rows = (SelectionModel.SIZE_MIN + SelectionModel.SIZE_MAX) / 2;
	private int columns = (SelectionModel.SIZE_MIN + SelectionModel.SIZE_MAX) / 2;
	private int mineCount = (SelectionModel.SIZE_MIN * SelectionModel.SIZE_MIN) / 2;

	/**
	  * The model of the actual game
	  * @see GameModel
	**/
	private GameModel gameModel;

	/**
	  * The view of the actual selection
	  * @see SelectionView
	**/
	private SelectionView selectionView;

	/**
	  * Constructor of the SelectionModel
	  * @param gameModel It's the model of the actual game
	  * @param selectionView It's the view of the actual selection
	**/
	public SelectionModel(GameModel gameModel, SelectionView selectionView){
		this.gameModel = gameModel;
		this.selectionView = selectionView;

		this.selectionView.setRows(this.rows);
		this.selectionView.setColumns(this.columns);
		this.selectionView.setMineCount(this.mineCount);
	}
	
	/**
	  * Permits to know if the number of rows is valid, and if it's not valid, the number of rows get a default value according to its actual value
	**/
	private void checkRows(){
		if(this.rows != SelectionModel.BAD_VALUE){
			this.rows = (this.rows < SelectionModel.SIZE_MIN) ? SelectionModel.SIZE_MIN : this.rows;
			this.rows = (this.rows > SelectionModel.SIZE_MAX) ? SelectionModel.SIZE_MAX : this.rows;
		}
	}

	/**
	  * Permits to know if the number of columns is valid, and if it's not valid, the number of columns get a default value according to its actual value
	**/
	private void checkColumns(){
		if(this.columns != SelectionModel.BAD_VALUE){
			this.columns = (this.columns < SelectionModel.SIZE_MIN) ? SelectionModel.SIZE_MIN : this.columns;
			this.columns = (this.columns > SelectionModel.SIZE_MAX) ? SelectionModel.SIZE_MAX : this.columns; 
		}
	}

	/**
	  * Permits to know if the number of mines is valid, and if it's not valid, the number of mines get a adefault value according to its actual value
	**/
	private void checkMineCount(){
		if(this.mineCount != SelectionModel.BAD_VALUE && this.rows != SelectionModel.BAD_VALUE && this.columns != SelectionModel.BAD_VALUE){
			this.mineCount = (this.mineCount < 1) ? 1 : this.mineCount;
			this.mineCount = (this.mineCount > (this.rows * this.columns) - 1) ? (this.rows * this.columns) - 1 : this.mineCount;
		}
	}

	/**
	  * Increment the number of rows selected
	**/
	public void incrementRows(){
		if(this.rows != SelectionModel.BAD_VALUE){
			this.rows++;
			this.checkRows();
			this.selectionView.setRows(this.rows);
		}
	}

	/**
	  * Decrement the number of rows selected
	**/
	public void decrementRows(){
		if(this.rows != SelectionModel.BAD_VALUE){
			this.rows--;
			this.checkRows();
			this.checkMineCount();
			this.selectionView.setRows(this.rows);
			this.selectionView.setMineCount(this.mineCount);
		} 
	}

	/**
	  * Increment the number of columns selected
	**/
	public void incrementColumns(){
		if(this.columns != SelectionModel.BAD_VALUE){
			this.columns++;
			this.checkColumns();
			this.selectionView.setColumns(this.columns);
		}
	}

	/**
	  * Decrement the number of columns selected
	**/
	public void decrementColumns(){
		if(this.columns != SelectionModel.BAD_VALUE){
			this.columns--;
			this.checkColumns();
			this.checkMineCount();
			this.selectionView.setColumns(this.columns);
			this.selectionView.setMineCount(this.mineCount);
		}	
	}

	/**
	  * Increment the number of mines selected
	**/
	public void incrementMine(){
		if(this.mineCount != SelectionModel.BAD_VALUE && this.rows != SelectionModel.BAD_VALUE && this.columns != SelectionModel.BAD_VALUE){
			this.mineCount++;
			this.checkMineCount();
			this.selectionView.setMineCount(this.mineCount);
		}
	}	

	/**
	  * Decrement the number of mines selected
	**/
	public void decrementMine(){
		if(this.mineCount != SelectionModel.BAD_VALUE && this.rows != SelectionModel.BAD_VALUE && this.columns != SelectionModel.BAD_VALUE){
			this.mineCount--;
			this.checkMineCount();
			this.selectionView.setMineCount(this.mineCount);
		}
	}

	/**
	  * @param text It's the text corresponding of the row's field
	  * Update the number of rows according to the value of the row's field
	**/
	public void setRows(String text){
		int rows;
		try{
			rows = Integer.parseInt(text);
		} catch(NumberFormatException e){
			rows = SelectionModel.BAD_VALUE;
		}
		
		this.rows = rows;
		this.checkRows();
		this.checkMineCount();
		this.selectionView.setRows(this.rows);
		this.selectionView.setMineCount(this.mineCount);
	}

	/**
	  * @param text It's the text corresponding of the column's field
	  * Update the number of columns according to the value of the columns's field
	**/
	public void setColumns(String text){
		int columns;
		try{
			columns = Integer.parseInt(text);
		} catch(NumberFormatException e){
			columns = SelectionModel.BAD_VALUE;
		}
		
		this.columns = columns;
		this.checkColumns();
		this.checkMineCount();
		this.selectionView.setColumns(this.columns);
		this.selectionView.setMineCount(this.mineCount);
	}

	/**
	  * @param text It's the text corresponding of the column's field
	  * Update the number of mines according to the value of the mine's field
	**/
	public void setMineCount(String text){
		int count;
		try{
			count = Integer.parseInt(text);
		} catch(NumberFormatException e){
			count = SelectionModel.BAD_VALUE;
		}
		this.mineCount = count;
		this.checkMineCount();
		this.selectionView.setMineCount(this.mineCount);
	}

	/**
	  * Start the game according to the parameters selected
	**/
	public void startGame(){
		//Make sure the player has enter each field
		this.setRows(this.selectionView.readRows());
		this.setColumns(this.selectionView.readColumns());
		this.setMineCount(this.selectionView.readMineCount());
		
		if(this.rows != SelectionModel.BAD_VALUE && this.columns != SelectionModel.BAD_VALUE && this.mineCount != SelectionModel.BAD_VALUE){
			this.gameModel.newGame(this.mineCount, new Dimension(this.columns, this.rows));
		} else{
			JOptionPane.showMessageDialog(null, "Bad values ! Can't start the game ...", "BAD VALUES", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	  * Return at the home
	**/
	public void cancelGame(){
		this.gameModel.changeState(ViewState.HOME);
	}
}