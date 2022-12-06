package state;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import main.MyFrame;

@SuppressWarnings("serial")
public class StartState extends BaseState implements MouseListener{
	
	private JButton start_button;
	private JButton rules_button;

	public StartState(MyFrame master) {
		super(master);
		
		
		width = 420;
		height = 420;
		setBackground(Color.red);
		
		// start button
		start_button = new JButton();
		
		// rules button
		rules_button = new JButton();
		
		add(start_button);
		add(rules_button);
		
		start_button.addMouseListener(this);
		rules_button.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == start_button) {
			master.stateChanged(State.GAME_STATE);
		}
		else if(e.getSource() == rules_button) {
			master.stateChanged(State.RULES_STATE);
		}
		
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
