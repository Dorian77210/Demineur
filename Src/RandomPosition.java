import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

/**
  * The class <code>RandomPosition</code> permits to generate the position of the mines
  * @version 0.1
  * @author Dorian Terbah, Thomas Béchet
**/

public class RandomPosition{
	private ArrayList<Integer> indexer;
	private Random random;
	private int sizeX;
	private int sizeY; //Maybe useless

	/**
	  * Constructor of the RandomPosition
	  * @param sizeX It represents the width of the board
	  * @param sizeY It represents the height of the board
	**/
	public RandomPosition(int sizeX, int sizeY){
		this.random = new Random();

		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.indexer = new ArrayList<Integer>(this.sizeX * this.sizeY);

		//Filling indexer with all linear positions possible
		for(int i = 0; i < this.sizeX * this.sizeY; i++){
			this.indexer.add(new Integer(i));
		}
	}

	/**
	  * Generate a new random position for the mines
	  * @return The random obtained position
	**/
	
	public Point nextPosition(){
		//Generate index based on the indexer
		int randomIndex = this.random.nextInt(this.indexer.size());
		//Read this linear position from the indexer at random index
		int linearPosition = this.indexer.get(randomIndex);
		//Remove the generated position for the next generation
		this.indexer.remove(randomIndex);

		//Create point from the linear position
		return new Point(linearPosition % this.sizeX, linearPosition / this.sizeX);
	}
}