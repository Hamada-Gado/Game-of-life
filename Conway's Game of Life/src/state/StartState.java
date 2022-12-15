package state;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.MyFrame;

@SuppressWarnings("serial")
public class StartState extends JPanel implements ActionListener{
	
	private JButton start_button;
	private JButton rules_button;
	
	private MyFrame master;

	public StartState(MyFrame master) {
		this.master = master;
		
		setBackground(Color.red);
		
		// start button
		start_button = new JButton();
		
		// rules button
		rules_button = new JButton();
		
		add(start_button);
		add(rules_button);
		
		start_button.addActionListener(this);
		rules_button.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == start_button) {
			master.stateChange(State.GAME_STATE);
		}
		else if(e.getSource() == rules_button) {
			master.stateChange(State.RULES_STATE);
		}
		
	}
	
	


}
