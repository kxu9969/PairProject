import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StartScreen extends JPanel implements ActionListener{
	JFrame screen;
	JButton start, howToPlay;
	JTextField name;
	JComboBox difficulty;
	String[] difficulties = {"Easy","Medium","Hard"};
	StartScreen(){
		screen = new JFrame();
		start = new JButton();
		howToPlay = new JButton();
		name = new JTextField();
		difficulty = new JComboBox();
		start.setActionCommand(this.getName());
		start.addActionListener(this);
		howToPlay.setActionCommand(this.getName());
		howToPlay.addActionListener(this);
		screen.add(start);
		screen.add(howToPlay);
		screen.add(name);
		screen.add(difficulty);
		screen.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("start")) {
			
		}else if(e.getActionCommand().equals("howToPlay")) {
			
		}
	}
	
	public static void main(String[] args) {
		StartScreen s = new StartScreen();
	}

}
