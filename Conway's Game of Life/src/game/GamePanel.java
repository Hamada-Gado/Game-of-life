package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{

	private Logic logic;
	private final int cellSize = 10;

	int width, height;
	
	public GamePanel(int width,int height){
		this.width = width/cellSize * cellSize;
		this.height = height/cellSize * cellSize;
		logic = new Logic(this.width/cellSize, this.height/cellSize);
		
		this.setPreferredSize(new Dimension(this.width, this.height));
	}
	
	public void startGameThread() {
		
		logic.set_board();
		Timer timer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
			
		});

		timer.start();
	}
	
	void update() {
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
