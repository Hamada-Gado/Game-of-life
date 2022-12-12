package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

enum State {
	PLAYING,
	PAUSED;
}

@SuppressWarnings("serial")
public class GamePanel extends JPanel{

	private final int cellSize = 10;
	
	private Logic logic;
	
	private State state;
	
	private Timer timer;
	private int delay; // number between 30 and 300
	
	private int width, height;
	
	public GamePanel(int width,int height){
		this.width = width/cellSize * cellSize;
		this.height = height/cellSize * cellSize;
		logic = new Logic(this.width/cellSize, this.height/cellSize);
		
		this.setPreferredSize(new Dimension(this.width, this.height));
	}
	
	public void startGameThread() {
		
		logic.set_board();
		state = State.PLAYING;
		delay = 165;
		timer = new Timer(delay, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
			
		});

		timer.start();
	}
	
	public boolean changeState() {
		// return boolean to change button icon
		// true for playing
		// false for paused
		
		if(state == State.PLAYING) {
			state = State.PAUSED;
			return true;
		}
		else {
			state = State.PLAYING;
			return false;
		}
	}
	
	public void changeDelay(int number) {
		delay = number;
		timer.setDelay(delay);
	}
	
	public void restart() {
		logic.set_board();
	}
	
	private void update() {
		if(state == State.PAUSED)
			return;
		logic.update();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
			
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		
		g2.setColor(Color.black);
		
		for(int x = 0; x < logic.cols; x++) {
			for(int y = 0; y < logic.rows; y++) {	
				if(logic.current_board[x][y] == Cell.ALIVE)
					g2.fillRect(x*cellSize, y*cellSize, cellSize - 1, cellSize - 1);
			}
		}
		
		g2.dispose();
	}

}
