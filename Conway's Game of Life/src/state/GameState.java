package state;

import java.awt.Color;

import game.GamePanel;
import main.MyFrame;

@SuppressWarnings("serial")
public class GameState extends BaseState{

	private GamePanel game_panel;
	
	public GameState(MyFrame master) {
		super(master);
		this.width = 500;
		this.height = 500;
		game_panel = new GamePanel(width, height);

		add(game_panel);
		setBackground(Color.blue);
	}

	@Override
	public void get_ready() {
		super.get_ready();
		
		game_panel.startGameThread();
	}

}
