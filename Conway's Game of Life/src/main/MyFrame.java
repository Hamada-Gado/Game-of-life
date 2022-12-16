package main;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import state.GameState;
import state.StartState;
import state.State;


@SuppressWarnings("serial")
public class MyFrame extends JFrame {
	
	JPanel current_state;
	public CardLayout cl;
	HashMap<State, JComponent> my_states;
	
	
	MyFrame() {
		// Set the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Conway's Game of Life");
		
		// Set the panels
		my_states = new HashMap<State, JComponent>();
		my_states.put(State.START_STATE, new StartState(this));
		my_states.put(State.GAME_STATE, new GameState(this));
		
		// Add panels to card to control panels and change them
		current_state = new JPanel(new CardLayout());
		for(State s : my_states.keySet()) {
			current_state.add(my_states.get(s), s.getValue());
		}
		cl = (CardLayout) current_state.getLayout();
		
		// show first panel
		cl.show(current_state, State.START_STATE.getValue());

		// to exit the application click escape
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
					System.exit(0);
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
		
		});

		// Add components to frame and make it visible
		add(current_state);
		pack();
		
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
	}

	public void stateChange(State state) {
		if(state == State.GAME_STATE)
			((GameState)my_states.get(state)).getReady();;
		
		cl.show(current_state, state.getValue());
		pack();
	}

}
