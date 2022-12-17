package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import exception.LoadPatternException;

enum State {
	PLAYING,
	PAUSED;
}

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements MouseListener{

	private final int cellSize = 10;
	
	private Logic logic;
	
	private State state;
	private boolean isOriginal; // when in this state the original is changed
	private Timer timer;
	private Graphics2D g2;
	
	public int delay;
	public final int DELAY_MIN = 20, DELAY_MAX = 400;
	
	private final int WIDTH = 840, HEIGHT = 500;
	
	public GamePanel(){
		state = State.PLAYING;
		delay = 165;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
	}
	
	public void startGame() {
		
		logic = new Logic(WIDTH/cellSize, HEIGHT/cellSize);
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
		
		isOriginal = false;

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
		isOriginal = true;
		logic.resetGeneration();
	}
	
	public boolean newGeneration() {
		if(logic.isEmpty) {
			logic.newGeneration();
			logic.isEmpty = false;
			isOriginal = false;
		}
		else {
			logic.newEmptyGeneration();
			isOriginal = true;
			logic.isEmpty = true;
		}
		
		return logic.isEmpty;
	}
	
	public void newGeneration(String alive, String dead, String delimiter, String data) throws LoadPatternException {
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
			
		g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2.setColor(Color.black);					
		for(int x = 0; x < logic.cols; x++) {
			for(int y = 0; y < logic.rows; y++) {	
				if(logic.current_generation[x][y] == Cell.ALIVE) {
					g2.setColor(Color.black);
					g2.fillRect(x*cellSize, y*cellSize, cellSize - 1, cellSize - 1);					
				}
					
				g2.setColor(Color.gray);
				g2.drawLine(x*cellSize, y*cellSize, x*cellSize - 1, y*cellSize + cellSize);
				g2.drawLine(x*cellSize, y*cellSize, x*cellSize + cellSize, y*cellSize - 1);
			}
		}
		
		g2.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		int x = e.getX()/cellSize;
		int y = e.getY()/cellSize;
		
		logic.current_generation[x][y] = (logic.current_generation[x][y] == Cell.ALIVE) ? Cell.DEAD : Cell.ALIVE;
		
		if(isOriginal)
			logic.original_generation[x][y] = (logic.original_generation[x][y] == Cell.ALIVE) ? Cell.DEAD : Cell.ALIVE;
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
