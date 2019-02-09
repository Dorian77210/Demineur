import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;

public class VisualClock extends JLabel{
	private Timer timer;
	private long elapsedTime;

	private class ClockTask extends TimerTask{
		private VisualClock clock;
		public ClockTask(VisualClock clock){
			this.clock = clock;
		}
		@Override
		/**
		  * Run the clock
		**/
		public void run(){
			clock.setElapsedTime(clock.getElapsedTime() + 1);
		}
	}

	public VisualClock(){
		super();
		this.timer = new Timer();
		//Set text to '0' and init time to 0
		this.setElapsedTime(0);

		//parameters the clock
		this.setOpaque(true);
		this.setForeground(Color.RED);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(Color.BLACK);
	}

	/**
	  * Start the clock
	**/
	public void start(){
		//Wait 1s then start updating each second
		this.timer.scheduleAtFixedRate(new ClockTask(this), 1000, 1000);
	}

	/**
	  *Stop the clock
	**/
	public void stop(){
		this.timer.cancel();
	}

	/**
	  * Restart the clock
	  * @return The elapsed time
	**/
	public long restart(){
		//Save time
		long time = this.elapsedTime;
		//Kill running thread
		this.stop();
		//Set everything to 0
		this.setElapsedTime(0);
		//Start again
		this.start();

		return time;
	}

	/**
	  * Get the elapsed time since the beginning of the game
	  * @return The elapsed time
	**/
	public long getElapsedTime(){
		return this.elapsedTime;
	}

	/**
	  * Update the elapsed time and the representation of this
	  * @param time The actual time
	**/
	public void setElapsedTime(long time){
		this.elapsedTime = time;

		String text = new String();

		if(this.elapsedTime > 3600){
			text += Long.toString(this.elapsedTime / 3600) + ":";
		}

		text += String.format("%02d", (this.elapsedTime % 3600) / 60);
		text += ":" + String.format("%02d", this.elapsedTime % 60);

		this.setText(text);
		this.repaint();
	}
}