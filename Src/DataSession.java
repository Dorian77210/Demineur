import java.awt.Dimension;
import java.io.*;

/**
  * The class <code>DataSession</code> is the loading of save
  * @version 0.2
  * @author Dorian Terbah, Thomas Béchet
**/

public class DataSession{

    /* [HEADER STRUCTURE]
     * width -> 1 (byte)
     * height -> 1 (byte)
     * difficulty -> 2 (short)
     * timeElapsed -> 8 (long)
     */

    /**
      * Constant use to give the length of the header's structure
    **/
    private static long HEADER_LENGTH = 1 + 1 + 2 + 8;

    /**
      * Constant use to have the path of the file's save
    **/
    private static String SAVE_PATH = "Save" + File.separator;

    private String name;
    private Dimension dimensions;
    private int mineCount;
    private long timeElapsed;

    private boolean exists;

    /**
      * Constructor of the DataSession
      * @param name the name of the save's file
    **/
    public DataSession(String name){
        this.name = name;

        File file = new File(DataSession.SAVE_PATH + this.name + ".dat");
        this.exists = file.exists();

        if(this.exists){
            try{
                //Opening the file according the session's name
                DataInputStream input = new DataInputStream(new FileInputStream(DataSession.SAVE_PATH + this.name + ".dat"));

                try{
                    //Reading the header
                    byte width = input.readByte();
                    byte height = input.readByte();
                    short mineCount = input.readShort();
                    long time = input.readLong();

                    //Filling information with header
                    this.dimensions = new Dimension((int)width, (int)height);
                    this.mineCount = (int)mineCount;
                    this.timeElapsed = time;
                } catch(IOException e){
                    //If the program failed to read, we set the existing flag to false
                    this.exists = false;
                }

                try{
                    input.close();
                } catch(IOException e){
                    //If the program frailed to close the file, set the exist flag to false 
                    this.exists = false;
                }

            } catch(FileNotFoundException e){
                //No file -> no session
                this.exists = false;
            }
        }
    }

    /**
      * Recuperate the dimensions of the board stored on the save's file
      * @return Dimensions of the board
    **/
    public Dimension getDimensions(){
        return this.dimensions;
    }

    /**
      * Give the elapsed time of the game
      * @return The elapsed time of the previous game 
    **/
    public long getElapsedTime(){
        return this.timeElapsed;
    }

    /**
      * Give the number of mined cells in the board
      * @return The number of mines present on the board
    **/
    public int getMineCount(){
        return this.mineCount;
    }

    /**
      * Permits to know if the save's file exist
      * @return True if the header is loaded in memory
      */
    public boolean exists(){
        return this.exists;
    }

    /**
      * Create the file to store the actual game
      * @param dimension The dimension of the board
      * @param mineCount The number of mines of the board
      * @return True if the writing was a success, false if it was a fail
    **/
    public boolean create(Dimension dimension, int mineCount){
        //Destroy the session if it exists
        if(this.exists) this.destroy();

        this.dimensions = dimension;
        this.mineCount = mineCount;
        this.timeElapsed = 0;

        //Even if the program fails to create the file, the session exists in memory
        //But if it fails we notify the program by returning false (success = false)
        this.exists = true;

        boolean success = true;
        try{
            DataOutputStream output = new DataOutputStream(new FileOutputStream(DataSession.SAVE_PATH + this.name + ".dat", false));

            try{
                output.writeByte(dimension.width);
                output.writeByte(dimension.height);
                output.writeByte(mineCount);
                output.writeLong(0);
            } catch(IOException e){
                success = false;
            }

            try{
                output.close();
            } catch(IOException e){
                success = false;
            }
        } catch(FileNotFoundException e){
            success = false;
        }

        return success;
    }

    /**
      * Delete the save's file           
    **/
    public void destroy(){
        //If the file failed to be deleted, we don't need to notify the program.
        //It will be overwritten at the next save
        if(this.exists){
            File file = new File(DataSession.SAVE_PATH + this.name + ".dat");
            file.delete();
        }
        this.exists = false;
    }

    /**
      * Permits to know if board's data are available for reading
      * @return True if the file is ready for reading, false if it doesn't exist or if the reading creates problems
    **/
    public boolean available(){
        if(!this.exists) return false;

        boolean available = true;
        try{
            FileInputStream input = new FileInputStream(DataSession.SAVE_PATH + this.name + ".dat");
            try{
                //Skip the header part
                input.skip(DataSession.HEADER_LENGTH);
                //Make sure the remaining information correspond to board's data required
                if(input.available() != this.dimensions.width * this.dimensions.height) available = false;
            } catch(IOException e){
                available = false;
            }

            try{
                input.close();
            } catch(IOException e){
                available = false;
            }
        } catch(FileNotFoundException e){
            available = false;
        }

        return available;
    }

    /**
      * Reconstitute the board according to the save's file
      * @param session It's the actal session of the game
      * @return the board if the reading was a success and that the board was reconstituted, else null
      * 
    **/
    public Board load(Session session){
        if(!this.exists) return null;

        //Creating the board
        Board board = new Board(session, this.dimensions, this.mineCount);

        try{
            FileInputStream input = new FileInputStream(DataSession.SAVE_PATH + this.name + ".dat");

            try{
                input.skip(DataSession.HEADER_LENGTH); //Go to board data

                if(input.available() != (this.dimensions.width * this.dimensions.height)){
                    if(input.available() == 0){
                        //No board data
                        return null;
                    } else{
                        //Data probably corrupted
                        return null;
                    }
                }

                //Filling states by loading each cells
                for(int y = 0; y < this.dimensions.height; y++){
                    for(int x = 0; x < this.dimensions.width; x++){
                        byte raw = (byte)input.read();
                        board.loadCell(raw, x, y);
                    }
                }
            } catch(IOException e){
                return null;
            }

            try{
                input.close();
            } catch(IOException e){
                return null;
            }
        } catch(FileNotFoundException e){
            return null;
        }

        //Adding mined neighbour for each cell and counting revealed cells
        for(int y = 0; y < this.dimensions.height; y++){
            for(int x = 0; x < this.dimensions.width; x++){
                Cell cell = board.cellAtPosition(x, y);
                if(cell.isRevealed() && !cell.isMined()){
                    board.revealOnce(cell);
                }                
            }
        }

        return board;
    }

    /**
      * Save the actual game in the save's file
      * @param board It's the board of the actual game 
      * @param timeElapsed It's the elapsed time of the actual game
      * @return true if the save was a success, else false
    **/
    public boolean save(Board board, long timeElapsed){
        if(!this.exists) return false;
        
        boolean success = false;
        try{
            DataOutputStream output = new DataOutputStream(new FileOutputStream(DataSession.SAVE_PATH + this.name + ".dat"));

            try{
                //Write header first
                output.writeByte((byte)this.dimensions.width);
                output.writeByte((byte)this.dimensions.height);
                output.writeShort((short)this.mineCount);
                this.timeElapsed = timeElapsed;
                output.writeLong(this.timeElapsed);

                //Write board
                for(int y = 0; y < this.dimensions.height; y++){
                    for(int x = 0; x < this.dimensions.width; x++){
                        byte raw = Cell.codeCell(board.cellAtPosition(x, y));
                        output.writeByte(raw);
                    }
                }
            } catch(IOException e){
                success = false;
            }

            //Close file
            try{
                output.close();
            } catch(IOException e){
                success = false;
            }
        } catch(FileNotFoundException e){
            success = false;
        }

        return success;
    }
}