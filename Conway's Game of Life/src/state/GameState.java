package state;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.Cell;
import game.GamePanel;
import main.MyFrame;

@SuppressWarnings("serial")
public class GameState extends JPanel{

	private GamePanel game_panel;
	private JPanel utilites;
	
	private MyFrame master;
	
	public GameState(MyFrame master) {
		this.master = master;
	
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.DARK_GRAY);
		
		game_panel = new GamePanel();

		utilites = new JPanel(new FlowLayout());

		utilites.setBackground(Color.gray);
		// add pause and play button
		utilites.add(new JButton("pause") {

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

		// add new button to create new generation
		utilites.add(new JButton("new") {
			{
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						game_panel.newGeneration();
						
					}
				});
			}
		});
		
		// add restart button to return to original generation
		utilites.add(new JButton("restart") {
			{
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						game_panel.restartGeneration();
						
					}
				});
			}
		});
		
		// add save button to save current generation
		utilites.add(new JButton("Save") {
			{
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						saveOriginalGeneration();
						
					}
				});
			}
		});
		
		// add load button to load a saved generation
		utilites.add(new JButton("Load") {
			{
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						loadOriginalGeneration();
						
					}
				});
			}
		});

		// add a slider to change game speed
		utilites.add(new JSlider(game_panel.delayMin, game_panel.delayMax, game_panel.delay) {
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
	
		add(game_panel);
		add(utilites);
	}

	public void getReady() {	
		game_panel.startGame();
	}
	
	private String[] getAliveDeadDelimiterValues() {
		// get values for dead and alive cells
		String alive, dead, delimiter;
	
		// keep asking for response until exactly one character is given
		// if canceled, exited, or pressed OK without input. set values to a default 
		while(( alive = JOptionPane.showInputDialog("Enter a character for alive cells: ")) != null  && (alive.length() > 1));
		alive = (alive != null && alive.length() == 0) ? "" + Cell.ALIVE.getValue() : alive;
		
		while(( dead = JOptionPane.showInputDialog("Enter a character for dead cells: ")) != null && (dead.length() > 1 || dead.equals(alive)));
		dead = (dead != null && dead.length() == 0) ? "" + Cell.DEAD.getValue() : dead;
		
		while(( delimiter = JOptionPane.showInputDialog("Enter a character for delimiter: ")) != null && (delimiter.length() > 1 || delimiter.equals(alive) || delimiter.equals(dead)));
		delimiter = (delimiter != null && delimiter.length() == 0) ? " " : delimiter;

		return new String[] {alive, dead, delimiter};
	}
	
	private void loadOriginalGeneration() {
		JFileChooser fileChooser = new JFileChooser(new File("."));
		
		if(fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
		
		String[] responses = getAliveDeadDelimiterValues();
		String alive = responses[0], dead = responses[1], delimiter = responses[2];
		
		if(alive == null || dead == null || delimiter == null) return;
		
		// all read from the file
		String strData = "";
		
		try(FileReader reader = new FileReader(fileChooser.getSelectedFile().getAbsolutePath())){
			int intData = reader.read();

			while(intData != -1) {
				strData += (char)intData;
				intData = reader.read();				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		game_panel.newGeneration(alive, dead, delimiter, strData);
	}
	
	private void saveOriginalGeneration() {
		Cell[][] originalGen = game_panel.getCurrentGeneration();
		
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File("."));
		
		if(fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) return;

		String[] responses = getAliveDeadDelimiterValues();
		String alive = responses[0], dead = responses[1], delimiter = responses[2];	

		if(alive == null || dead == null || delimiter == null) return;
		
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
