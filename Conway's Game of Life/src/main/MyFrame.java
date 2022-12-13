package main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import state.BaseState;
import state.GameState;
import state.StartState;
import state.State;


@SuppressWarnings("serial")
public class MyFrame extends JFrame {
	
	public JPanel current_state;
	CardLayout cl;
	HashMap<State, BaseState> my_states;
	
	
	MyFrame() {
		// Set the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Conway's Game of Life");
		
		
		// Set the panels
		my_states = new HashMap<State, BaseState>();
		my_states.put(State.START_STATE, new StartState(this));
		my_states.put(State.GAME_STATE, new GameState(this));
		
		// Add panels to card to control panels and change them
		current_state = new JPanel(new CardLayout());
		for(State s : my_states.keySet()) {
			current_state.add(my_states.get(s), s.getValue());
		}
		cl = (CardLayout) current_state.getLayout();
		
		// show first panel
		my_states.get(State.START_STATE).get_ready();
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

	public void stateChanged(State state) {
		BaseState next_state = my_states.get(state);
		next_state.get_ready();
		
		current_state.setPreferredSize(new Dimension(next_state.width, next_state.height));
		cl.show(current_state, state.getValue());
		
		pack();
	}

}
