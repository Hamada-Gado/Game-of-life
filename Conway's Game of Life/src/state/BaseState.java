package state;

import java.awt.Dimension;

import javax.swing.JPanel;

import main.MyFrame;

@SuppressWarnings("serial")
public abstract class BaseState extends JPanel{
	
	protected MyFrame master;
	public int width;
	public int height;
	
	public BaseState(MyFrame master) {
		this.master = master;
	}
	
	public void get_ready() {
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
	}

}
