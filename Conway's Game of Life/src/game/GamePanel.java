package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
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
	public int delay;
	public final int delayMin = 20, delayMax = 400;
	
	private final int width = 840, height = 500;
	
	public GamePanel(){
		logic = new Logic(this.width/cellSize, this.height/cellSize);
		state = State.PLAYING;
		delay = 165;
	
		setPreferredSize(new Dimension(width, height));
	}
	
	public void startGame() {
		
		logic.newGeneration();
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
	
	public void stopGame() {
		timer.stop();
	}
	
	public void restartGeneration() {
		logic.resetGeneration();
	}
	
	public void newGeneration() {		
		logic.newGeneration();
		
	}
	
	public void newGeneration(String alive, String dead, String delimiter, String data) {
		logic.newGeneration(alive, dead, delimiter, data);

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
