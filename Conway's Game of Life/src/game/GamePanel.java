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
	private int delay;
	
	private int width, height;
	
	public GamePanel(int width,int height){
		// divide then multiply by cellSize so that if width or height is not divisible by cellSize
		// screen is does not have extra space around it
		
		this.width = width/cellSize * cellSize;
		this.height = height/cellSize * cellSize;
		logic = new Logic(this.width/cellSize, this.height/cellSize);
		
	}
	
	public void startGame() {
		
		logic.newGeneration();
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
		
		setPreferredSize(new Dimension(width, height));
		setSize(getPreferredSize());
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
	
	public void restartGeneration() {
		logic.resetGeneration();
	}
	
	public void newGeneration() {		
		logic.newGeneration();
		
	}
	
	public void newGeneration(String alive, String dead, String delimiter, String data) {

		logic.newGeneration(alive, dead, delimiter, data);
		
		width = logic.cols * cellSize;
		height = logic.rows * cellSize;
		
		setPreferredSize(new Dimension(this.width, this.height));
		setSize(getPreferredSize());
	}
	
	public Cell[][] getCurrentGeneration() {
		return logic.current_generation;
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
				if(logic.current_generation[x][y] == Cell.ALIVE)
					g2.fillRect(x*cellSize, y*cellSize, cellSize - 1, cellSize - 1);
			}
		}
		
		g2.dispose();
	}

}
