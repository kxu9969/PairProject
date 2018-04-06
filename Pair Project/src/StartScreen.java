import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JPanel implements ActionListener{
	JFrame screen;
	JPanel panel,instructionPanel;
	JButton start, howToPlay, back;
	JTextField name;
	JComboBox difficulty;
	Game game;
	String[] difficulties = {"Easy","Medium","Hard"};
	StartScreen(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{35, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		
		screen = new JFrame();
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel=new JPanel();
		panel.setLayout(gridBagLayout);
		
		name = new JTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.gridwidth = 1;
		gbc_nameField.fill=GridBagConstraints.HORIZONTAL;
		gbc_nameField.insets = new Insets(0, 0, 5, 0);
		gbc_nameField.gridx = 1;
		gbc_nameField.gridy = 0;
		panel.add(name,gbc_nameField);
		
		difficulty = new JComboBox(difficulties);
		GridBagConstraints gbc_difBox = new GridBagConstraints();
		gbc_difBox.gridwidth = 1;
		gbc_difBox.insets = new Insets(0, 0, 5, 0);
		gbc_difBox.gridx = 1;
		gbc_difBox.gridy = 1;
		panel.add(difficulty,gbc_difBox);
		
		start = new JButton("Start");
		start.setActionCommand(this.getName());
		start.addActionListener(this);
		GridBagConstraints gbc_startbtn = new GridBagConstraints();
		gbc_startbtn.gridwidth = 1;
		gbc_startbtn.insets = new Insets(0, 0, 0, 0);
		gbc_startbtn.gridx = 0;
		gbc_startbtn.gridy = 2;
		panel.add(start,gbc_startbtn);
		
		howToPlay = new JButton("How to Play");
		howToPlay.setActionCommand(this.getName());
		howToPlay.addActionListener(this);
		GridBagConstraints gbc_howbtn = new GridBagConstraints();
		gbc_howbtn.gridwidth = 1;
		gbc_howbtn.insets = new Insets(0, 0, 0, 0);
		gbc_howbtn.gridx = 2;
		gbc_howbtn.gridy = 2;
		panel.add(howToPlay,gbc_howbtn);
		
		screen.add(panel);
		screen.pack();
		screen.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")) {
			String userName=name.getText();
			panel.setVisible(false);
			Game game=new Game(userName);
			screen.setVisible(false);
			game.setVisible(true);
		}else if(e.getActionCommand().equals("How to Play")) {
			panel.setVisible(false);
			howToPlayScreen();
		}else if(e.getActionCommand().equals("Back")){
			instructionPanel.setVisible(false);
			panel.setVisible(true);
			screen.setSize(new Dimension(250,130));
		}
	}
	
	public void howToPlayScreen(){
		instructionPanel=new JPanel();
		instructionPanel.setLayout(new GridLayout(2,1,0,0));
		
		JLabel instructions=new JLabel("<html>Use the arrow keys to move around. Your goal is to dodge asteroids and"
				+ " avoid enemy <br/>shots while shooting at them. Shooting is automatic, and you will do so continuously.</html>");
		instructions.setHorizontalAlignment(JLabel.CENTER);
		instructionPanel.add(instructions);
		
		back=new JButton("Back");
		back.setActionCommand(this.getName());
		back.addActionListener(this);
		instructionPanel.add(back);
		
		screen.add(instructionPanel);
		screen.pack();
	}
	public static void main(String[] args) {
		StartScreen s = new StartScreen();
	}

}
