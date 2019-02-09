import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
  * The class <code>SelectionController</code> permits to control the differents actions present in the selection
  * @version 0.2
  * @author Dorian Terbah, Thomas Béchet
**/

public class SelectionController implements ActionListener{

	/**
	  * The model of the selection
	**/
	private SelectionModel selectionModel;

	/**
	  * Constructor of the SelectionController
	  * @param selectionModel It's the model of the selection
	**/
	public SelectionController(SelectionModel selectionModel){
		this.selectionModel = selectionModel;
	}

	@Override

	/**
	  * @param event It's the event producted by the click on the buttons of the selection
	  * Update the number of columns, rows and mines
	**/
	public void actionPerformed(ActionEvent event){
		String actionCommand = event.getActionCommand();
		switch(actionCommand){
			case "SET_ROWS":
				JTextField rowsField = (JTextField)event.getSource();
				this.selectionModel.setRows(rowsField.getText());
			break;
			case "INCREMENT_ROWS":
				this.selectionModel.incrementRows();
			break;
			case "DECREMENT_ROWS":
				this.selectionModel.decrementRows();
			break;
			case "SET_COLUMNS":
				JTextField columnsField = (JTextField)event.getSource();
				this.selectionModel.setColumns(columnsField.getText());
			break;
			case "INCREMENT_COLUMNS":
				this.selectionModel.incrementColumns();
			break;
			case "DECREMENT_COLUMNS":
				this.selectionModel.decrementColumns();
			break;
			case "SET_MINE":
				JTextField minesField = (JTextField)event.getSource();
				this.selectionModel.setMineCount(minesField.getText());
			break;
			case "INCREMENT_MINE":
				this.selectionModel.incrementMine();
			break;
			case "DECREMENT_MINE":
				this.selectionModel.decrementMine();
			break;
			case "START_GAME":
				this.selectionModel.startGame();
			break;
			case "CANCEL_GAME":
				this.selectionModel.cancelGame();
			break;
			default:
			break;
		}
	}
}