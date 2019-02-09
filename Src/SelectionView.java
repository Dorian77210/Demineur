import java.awt.*;
import javax.swing.*;

/**
  * The class <code>SelectionView</code> is the representation of the selection program
  * @version 0.4
  * @author Dorian Terbah, Thomas Béchet
**/

public class SelectionView extends JPanel{

	/**
	  * The controller of the actual selection
	  * @see SelectionController
	**/
	private SelectionController controller;

	/**
	  * The model of the actul selection
	  * @see SelectionModel
	**/
	private SelectionModel model;
	
	/**
	  * Constant used if the value of the columns, rows or difficulty is not an integer
	**/	
	private static final int BAD_VALUE = -1;

	//Components for rows
	private JButton incrementRows;
	private JButton decrementRows;
	private JTextField rows;
	private JLabel rowsTitle;

	//Components for columns
	private JButton incrementColumns;
	private JButton decrementColumns;
	private JTextField columns;
	private JLabel columnsTitle;

	//Components for difficulty
	private JButton incrementMine;
	private JButton decrementMine;
	private JTextField mineCount;
	private JLabel mineCountTitle;

	//Start Button
	private JButton startGame;
	private JButton cancelGame;

	/**
	  * Constructor of the SelectionView
	  * @param gameModel The model of the actual game
	**/
	public SelectionView(GameModel gameModel){
		super();
		
		int lineHeight = 40; //Constant for easy configuration

		//UI CONFIGURATION///////////////////////////////////
		this.rowsTitle = new JLabel("Rows:");
		this.rowsTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		this.rowsTitle.setHorizontalAlignment(JLabel.RIGHT);
		this.rowsTitle.setPreferredSize(new Dimension(100, lineHeight));
		
		this.rows = new JTextField();
		this.rows.setEditable(true);
		this.rows.setBackground(Color.WHITE);
		this.rows.setHorizontalAlignment(JTextField.CENTER);
		this.rows.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		this.rows.setActionCommand("SET_ROWS");
		this.rows.setPreferredSize(new Dimension(100, lineHeight));
		
		this.incrementRows = new JButton("+");
		this.incrementRows.setBackground(Color.BLACK);
		this.incrementRows.setForeground(Color.WHITE);
		this.incrementRows.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		this.incrementRows.setMargin(new Insets(0, 0, 0, 0));
		this.incrementRows.setActionCommand("INCREMENT_ROWS");
		this.incrementRows.setPreferredSize(new Dimension(lineHeight, lineHeight));
		
		this.decrementRows = new JButton("-");
		this.decrementRows.setBackground(Color.BLACK);
		this.decrementRows.setForeground(Color.WHITE);
		this.decrementRows.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		this.decrementRows.setMargin(new Insets(0, 0, 0, 0));
		this.decrementRows.setActionCommand("DECREMENT_ROWS");
		this.decrementRows.setPreferredSize(new Dimension(lineHeight, lineHeight));

		this.columnsTitle = new JLabel("Columns:");
		this.columnsTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		this.columnsTitle.setHorizontalAlignment(JLabel.RIGHT);
		this.columnsTitle.setPreferredSize(new Dimension(150, lineHeight));
		
		this.columns = new JTextField();
		this.columns.setEditable(true);
		this.columns.setBackground(Color.WHITE);
		this.columns.setHorizontalAlignment(JTextField.CENTER);
		this.columns.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		this.columns.setActionCommand("SET_COLUMNS");
		this.columns.setPreferredSize(new Dimension(100, lineHeight));
		
		this.incrementColumns = new JButton("+");
		this.incrementColumns.setBackground(Color.BLACK);
		this.incrementColumns.setForeground(Color.WHITE);
		this.incrementColumns.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 20));
		this.incrementColumns.setActionCommand("INCREMENT_COLUMNS");
		this.incrementColumns.setPreferredSize(new Dimension(lineHeight, lineHeight));
		this.incrementColumns.setMargin(new Insets(0, 0, 0, 0));

		this.decrementColumns = new JButton("-");
		this.decrementColumns.setBackground(Color.BLACK);
		this.decrementColumns.setForeground(Color.WHITE);
		this.decrementColumns.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		this.decrementColumns.setActionCommand("DECREMENT_COLUMNS");
		this.decrementColumns.setPreferredSize(new Dimension(lineHeight, lineHeight));
		this.decrementColumns.setMargin(new Insets(0, 0, 0, 0));

		this.mineCountTitle = new JLabel("Mines:");
		this.mineCountTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		this.mineCountTitle.setHorizontalAlignment(JLabel.RIGHT);
		this.mineCountTitle.setPreferredSize(new Dimension(100, lineHeight));
		
		this.mineCount = new JTextField();
		this.mineCount.setEditable(true);
		this.mineCount.setBackground(Color.WHITE);
		this.mineCount.setHorizontalAlignment(JTextField.CENTER);
		this.mineCount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		this.mineCount.setActionCommand("SET_MINE");
		this.mineCount.setPreferredSize(new Dimension(100, lineHeight));
		
		this.incrementMine = new JButton("+");
		this.incrementMine.setBackground(Color.BLACK);
		this.incrementMine.setForeground(Color.WHITE);
		this.incrementMine.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 20));
		this.incrementMine.setActionCommand("INCREMENT_MINE");
		this.incrementMine.setPreferredSize(new Dimension(lineHeight, lineHeight));
		this.incrementMine.setMargin(new Insets(0, 0, 0, 0));

		this.decrementMine = new JButton("-");
		this.decrementMine.setBackground(Color.BLACK);
		this.decrementMine.setForeground(Color.WHITE);
		this.decrementMine.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		this.decrementMine.setActionCommand("DECREMENT_MINE");
		this.decrementMine.setPreferredSize(new Dimension(lineHeight, lineHeight));
		this.decrementMine.setMargin(new Insets(0, 0, 0, 0));

		this.cancelGame = new JButton("Cancel");
		this.cancelGame.setBackground(Color.BLUE);
		this.cancelGame.setForeground(Color.WHITE);
		this.cancelGame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		this.cancelGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.cancelGame.setActionCommand("CANCEL_GAME");
		this.cancelGame.setPreferredSize(new Dimension(100, lineHeight));
		
		this.startGame = new JButton("Start");
		this.startGame.setBackground(Color.RED);
		this.startGame.setForeground(Color.WHITE);
		this.startGame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		this.startGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.startGame.setActionCommand("START_GAME");
		this.startGame.setPreferredSize(new Dimension(100, lineHeight));
		
		//TOP PART//
		JPanel top = new JPanel();
		top.setBackground(Color.decode("#d8d8d8"));
		top.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(10, 10,
        10, 10, Color.BLACK), "Configurations"));
		top.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10, 10, 0, 0);
		//Line 1//
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		top.add(this.rowsTitle, c);
		c.gridx = 2;
		top.add(this.rows, c);
		c.gridwidth = 1;
		c.gridx = 4;
		top.add(this.decrementRows, c);
		c.gridx = 5;
		top.add(this.incrementRows, c);
		
		//Line 2//
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		top.add(this.columnsTitle, c);
		c.gridx = 2;
		top.add(this.columns, c);
		c.gridwidth = 1;
		c.gridx = 4;
		top.add(this.decrementColumns, c);
		c.gridx = 5;
		top.add(this.incrementColumns, c);
		
		//Line 3//
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 2;
		top.add(this.mineCountTitle, c);
		c.gridx = 2;
		top.add(this.mineCount, c);
		c.gridwidth = 1;
		c.gridx = 4;
		top.add(this.decrementMine, c);
		c.gridx = 5;
		top.add(this.incrementMine, c);
		
		//BOTTOM PART//
		//Line 4//
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.decode("#d8d8d8"));
		bottom.setLayout(new FlowLayout());
		bottom.add(this.cancelGame);
		bottom.add(this.startGame);
		c.gridwidth = 6;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 3;
		top.add(bottom, c);
		
		//ADDING EVERYTHING//
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.CENTER);

		//MODEL AND CONTROLLER//////////////////////////////////
		this.model = new SelectionModel(gameModel, this);
		this.controller = new SelectionController(this.model);

		this.rows.addActionListener(this.controller);
		this.incrementRows.addActionListener(this.controller);
		this.decrementRows.addActionListener(this.controller);
		this.columns.addActionListener(this.controller);
		this.incrementColumns.addActionListener(this.controller);
		this.decrementColumns.addActionListener(this.controller);
		this.mineCount.addActionListener(this.controller);
		this.incrementMine.addActionListener(this.controller);
		this.decrementMine.addActionListener(this.controller);
		this.startGame.addActionListener(this.controller);
		this.cancelGame.addActionListener(this.controller);
	}
	
	/**
	  * Permits to know the value of row's field
	  * @return the text according to the row's field
	**/
	public String readRows(){
		return this.rows.getText();
	}

	/**
	  * Permits to know the value of column's field
	  * @return the text according to the column's field
	**/
	public String readColumns(){
		return this.columns.getText();
	}

	/**
	  * Permits to know the value of mine's field
	  * @return the text according to the mine's field
	**/
	public String readMineCount(){
		return this.mineCount.getText();
	}

	/**
	  * Update the number of selected rows
	  * @param rows The number of selected rows
	**/

	public void setRows(int rows){
		if(rows != SelectionView.BAD_VALUE){
			this.rows.setText(Integer.toString(rows));
		} else{
			this.rows.setText("???");
		}
	}

	/**
	  * Update the number of selected columns
	  * @param columns The number of selected columns
	**/
	public void setColumns(int columns){
		if(columns != SelectionView.BAD_VALUE){
			this.columns.setText(Integer.toString(columns));
		} else{
			this.columns.setText("???");
		}
	}

	/**
	  * Update the number of mines selected
	  * @param mineCount The number of selected mines
	**/
	public void setMineCount(int mineCount){
		if(mineCount != SelectionView.BAD_VALUE){
			this.mineCount.setText(Integer.toString(mineCount));
		}  else{
			this.mineCount.setText("???");
		}
	}
}