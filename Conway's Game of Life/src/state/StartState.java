package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MyFrame;

@SuppressWarnings("serial")
public class StartState extends JPanel implements ActionListener{
	
	private JLabel title_label;
	private JButton start_button;
	private JButton rules_button;
	
	private MyFrame master;

	public StartState(MyFrame master) {
		this.master = master;
		
		
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(new Color(234,255,123));
		
		// title label
		title_label = new JLabel("GAME OF LIFE");
		title_label.setFont(new Font("Comic Sans", Font.BOLD, 100));
		title_label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		// start button
		start_button = new JButton("START");
		start_button.setFocusable(false);
		start_button.setFont(new Font("MV Boli", Font.PLAIN, 50));
		start_button.setBackground(new Color(145,63,146));
		start_button.setForeground(new Color(41,189,193));
		start_button.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		// rules button
		rules_button = new JButton("RULES");
		rules_button.setFocusable(false);
		rules_button.setFont(new Font("MV Boli", Font.PLAIN, 50));
		rules_button.setBackground(new Color(145,63,146));
		rules_button.setForeground(new Color(41,189,193));
		rules_button.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		add(title_label);
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
