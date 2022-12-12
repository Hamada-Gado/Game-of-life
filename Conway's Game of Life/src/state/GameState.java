package state;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.GamePanel;
import main.MyFrame;

@SuppressWarnings("serial")
public class GameState extends BaseState{

	private GamePanel game_panel;
	
	public GameState(MyFrame master) {
		super(master);
		width = 500;
		height = 500;
		game_panel = new GamePanel(width, height-50);
		setLayout(new FlowLayout());		
		
		add(game_panel);
		
		add(new JButton("play") {

			{
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						boolean resualt = game_panel.changeState();
						if(resualt)
							setText("pause");							
						else
							setText("play");
					}
				});
			}
		});
		
		add(new JButton("restart") {
			{
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						game_panel.restart();
						
					}
				});
			}
		});
		
		add(new JSlider(30, 300, 165) {
			{
				addChangeListener(new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						game_panel.changeDelay(getValue());
						
					}
				});
			}
			
		});
		
		setBackground(Color.gray);
	}

	@Override
	public void get_ready() {
		super.get_ready();
		
		game_panel.startGameThread();
	}

}
