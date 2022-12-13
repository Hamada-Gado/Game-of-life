package state;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.Cell;
import game.GamePanel;
import main.MyFrame;

@SuppressWarnings("serial")
public class GameState extends BaseState{

	private GamePanel game_panel;
	
	private final int delayMin = 20, delayMax = 400;
	
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
							setText("play");							
						else
							setText("pause");
					}
				});
			}
		});
		
		add(new JButton("new") {
			{
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						game_panel.newGeneration();
						
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
		
		add(new JButton("Save") {
			{
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						saveOriginalGeneration();
						
					}
				});
			}
		});

		add(new JSlider(delayMin, delayMax) {
			{
				setInverted(true);
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
	
	void saveOriginalGeneration() {
		Cell[][] originalGen = game_panel.originalGeneration();
		
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File("."));
		
		int response = fileChooser.showSaveDialog(null);
		
		if(response == JFileChooser.APPROVE_OPTION) {

			// get values for dead and alive cells
			String alive, dead, delimiter;
		
			while(( alive = JOptionPane.showInputDialog("Enter a character for alive cells: ")) == null  || alive.length() != 1);
			while(( dead = JOptionPane.showInputDialog("Enter a character for dead cells: ")) == null || dead.length() != 1);
			while(( delimiter = JOptionPane.showInputDialog("Enter a character for delimiter: ")) == null || delimiter.length() != 1);
			
			// write the file and save it
			try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile().getAbsolutePath())) {
				
				for(int x = 0; x < originalGen.length; x++) {
					for(int y = 0; y < originalGen[x].length; y++) {
						if(originalGen[x][y] == Cell.ALIVE)
							writer.append(alive);
						else
							writer.append(dead);
						writer.append(delimiter);
					}
					writer.append('\n');
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
