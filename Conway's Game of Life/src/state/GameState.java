package state;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.Cell;
import game.GamePanel;
import main.MyFrame;

@SuppressWarnings("serial")
public class GameState extends JPanel{

	// two main panels
	private GamePanel game_panel;
	private JPanel utilites;
	private final Color BG_UTILITES = Color.DARK_GRAY;

	// Initialize all icons
	private ImageIcon PLAY_ICON;
	private ImageIcon PAUSE_ICON;
	private ImageIcon STOP_ICON;
	private ImageIcon NEW_ICON;
	private ImageIcon SHUFFLE_ICON;
	private ImageIcon REPEAT_ICON;
	private ImageIcon EJECT_ICON;
	private ImageIcon INSERT_ICON;
	
	public GameState(MyFrame master) {
		try{
			loadIcons();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		game_panel = new GamePanel();

		utilites = new JPanel(new FlowLayout());
		utilites.setBackground(BG_UTILITES);
		
		// add pause and play button
		utilites.add(new JButton(PAUSE_ICON) {
			{
				setBackground(BG_UTILITES);
				setBorderPainted(false);
				setFocusable(false);
				setContentAreaFilled(false);
				
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(game_panel.changeState())
							setIcon(PLAY_ICON);							
						else
							setIcon(PAUSE_ICON);
					}
				});
			}
		});
		
		// add new button to stop the game and return to main menu
		utilites.add(new JButton(STOP_ICON) {
			
			{
				setBackground(BG_UTILITES);
				setBorderPainted(false);
				setFocusable(false);
				setContentAreaFilled(false);
				
				addActionListener(new ActionListener() {
		
					@Override
					public void actionPerformed(ActionEvent e) {
						game_panel.stopGame();
						master.stateChange(State.START_STATE);
					}
				});
			}
		});
		
		// add new button to create new generation
		utilites.add(new JButton(NEW_ICON) {
			{
				setBackground(BG_UTILITES);
				setBorderPainted(false);
				setFocusable(false);
				setContentAreaFilled(false);
				
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(game_panel.newGeneration())
							setIcon(SHUFFLE_ICON);
						else
							setIcon(NEW_ICON);
					}
				});
			}
		});
		
		// add restart button to return to original generation
		utilites.add(new JButton(REPEAT_ICON) {
			{
				setBackground(BG_UTILITES);
				setBorderPainted(false);
				setFocusable(false);
				setContentAreaFilled(false);
				
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						game_panel.restartGeneration();
						
					}
				});
			}
		});
		
		// add save button to save current generation
		utilites.add(new JButton(EJECT_ICON) {
			{
				setBackground(BG_UTILITES);
				setBorderPainted(false);
				setFocusable(false);
				setContentAreaFilled(false);
				
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						saveOriginalGeneration();
						
					}
				});
			}
		});
		
		// add load button to load a saved generation
		utilites.add(new JButton(INSERT_ICON) {
			{
				setBackground(BG_UTILITES);
				setBorderPainted(false);
				setFocusable(false);
				setContentAreaFilled(false);
				
				addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						loadOriginalGeneration();
						
					}
				});
			}
		});

		// add a slider to change game speed
		utilites.add(new JSlider(game_panel.DELAY_MIN, game_panel.DELAY_MAX, game_panel.delay) {
			{
				setBackground(BG_UTILITES);
				setInverted(true);
				setFocusable(false);
				
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
	
	private void loadIcons() throws IOException {
		PLAY_ICON		= new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/icons8-play-64.png")));
		PAUSE_ICON 		= new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/icons8-pause-64.png")));
		STOP_ICON 		= new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/icons8-stop-64.png")));
		NEW_ICON		= new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/icons8-plus-64.png")));
		SHUFFLE_ICON	= new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/icons8-shuffle-64.png")));
		REPEAT_ICON		= new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/icons8-repeat-64.png")));
		EJECT_ICON 		= new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/icons8-eject-64.png")));
		INSERT_ICON 	= new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/res/icons8-insert-64.png")));
		
	}

	public void getReady() {	
		game_panel.startGame();
	}
	
	private String[] getAliveDeadDelimiterValues() {
		// get values for dead and alive cells
		String alive, dead, delimiter;
	
		// keep asking for response until exactly one character is given
		// if pressed OK without input. set values to a default 
		while(( alive = JOptionPane.showInputDialog("Enter a character for alive cells: ")) != null  && (alive.length() > 1));
		alive = (alive != null && alive.length() == 0) ? "" + Cell.ALIVE.getValue() : alive;
		
		while(( dead = JOptionPane.showInputDialog("Enter a character for dead cells: ")) != null && (dead.length() > 1 || dead.equals(alive)));
		dead = (dead != null && dead.length() == 0) ? "" + Cell.DEAD.getValue() : dead;
		
		while(( delimiter = JOptionPane.showInputDialog("Enter a character for separator: ")) != null && (delimiter.length() > 1 || delimiter.equals(alive) || delimiter.equals(dead)));
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
			e.printStackTrace();
		} catch (IOException e) {
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
