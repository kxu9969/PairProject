import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JPanel implements ActionListener{
	JFrame screen;
	JPanel panel,instructionPanel;
	JButton start, howToPlay, back;
	JTextField name;
	JComboBox difficulty;
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
		gbc_startbtn.insets = new Insets(0, 0, 5, 0);
		gbc_startbtn.gridx = 0;
		gbc_startbtn.gridy = 2;
		panel.add(start,gbc_startbtn);
		
		howToPlay = new JButton("How to Play");
		howToPlay.setActionCommand(this.getName());
		howToPlay.addActionListener(this);
		GridBagConstraints gbc_howbtn = new GridBagConstraints();
		gbc_howbtn.gridwidth = 1;
		gbc_howbtn.insets = new Insets(0, 0, 5, 0);
		gbc_howbtn.gridx = 2;
		gbc_howbtn.gridy = 2;
		panel.add(howToPlay,gbc_howbtn);
		
		screen.add(panel);
		screen.pack();
		screen.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")) {
			
		}else if(e.getActionCommand().equals("How to Play")) {
			panel.setVisible(false);
			howToPlayScreen();
		}else if(e.getActionCommand().equals("Back")){
			instructionPanel.setVisible(false);
			panel.setVisible(true);
			screen.setSize(new Dimension(275,150));
		}
	}
	
	public void howToPlayScreen(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		
		instructionPanel=new JPanel();
		instructionPanel.setLayout(gridBagLayout);
		
		JLabel instructions=new JLabel("<html>"+"Use the arrow keys to move around. Your goal is to dodge asteroids and"
				+ " avoid enemy shots while shooting at them. Shooting is automatic, and you will do so continuously."+"</html");
		GridBagConstraints gbc_instructions = new GridBagConstraints();
		gbc_instructions.gridwidth = GridBagConstraints.REMAINDER;
		gbc_instructions.insets = new Insets(0, 0, 10, 0);
		gbc_instructions.gridx = 0;
		gbc_instructions.gridwidth=2;
		gbc_instructions.gridy = 0;
		gbc_instructions.gridheight=2;
		instructionPanel.add(instructions, gbc_instructions);
		
		back=new JButton("Back");
		back.setActionCommand(this.getName());
		back.addActionListener(this);
		GridBagConstraints gbc_backBtn = new GridBagConstraints();
		gbc_backBtn.insets = new Insets(10, 0, 0, 0);
		gbc_backBtn.gridx = 1;
		gbc_backBtn.gridy = 2;
		instructionPanel.add(back, gbc_backBtn);
		
		screen.add(instructionPanel);
		screen.pack();
	}
	public static void main(String[] args) {
		StartScreen s = new StartScreen();
	}

}
