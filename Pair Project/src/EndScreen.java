import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class EndScreen extends JPanel implements ActionListener{
	JFrame screen;
	JButton playAgain,exit;
	EndScreen(String score, String username){
		screen=new JFrame("Game Over");
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0,0,0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0,Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gridBagLayout);
		
		JLabel lblGameOver = new JLabel("Game Over!");
		GridBagConstraints gbc_lblGameOver = new GridBagConstraints();
		gbc_lblGameOver.gridx = 1;
		gbc_lblGameOver.gridy = 0;
		gbc_lblGameOver.insets = new Insets(5, 5, 10, 5);
		this.add(lblGameOver, gbc_lblGameOver);
		
		JLabel lblScore = new JLabel(username+": "+score);
		GridBagConstraints gbc_lblScore = new GridBagConstraints();
		gbc_lblScore.gridx = 1;
		gbc_lblScore.gridy = 1;
		gbc_lblScore.insets = new Insets(10, 5, 10, 5);
		this.add(lblScore, gbc_lblScore);
		
		JButton playAgain=new JButton("Play Again");
		playAgain.setActionCommand(this.getName());
		playAgain.addActionListener(this);
		GridBagConstraints gbc_playBtn = new GridBagConstraints();
		gbc_playBtn.gridx = 0;
		gbc_playBtn.gridy = 2;
		gbc_playBtn.insets = new Insets(10, 0, 0, 5);
		this.add(playAgain,gbc_playBtn);
		
		JButton exit=new JButton("Exit");
		exit.setActionCommand(this.getName());
		exit.addActionListener(this);
		GridBagConstraints gbc_exitBtn = new GridBagConstraints();
		gbc_exitBtn.ipadx=50;
		gbc_exitBtn.fill=GridBagConstraints.HORIZONTAL;
		gbc_exitBtn.gridx = 2;
		gbc_exitBtn.gridy = 2;
		gbc_exitBtn.insets = new Insets(10, 5, 0, 0);
		this.add(exit,gbc_exitBtn);
		
		screen.add(this);
		screen.pack();
		screen.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String whichButton=e.getActionCommand();
		if(whichButton.equals("Play Again")){
			screen.setVisible(false);
			StartScreen start=new StartScreen();
		}else if(whichButton.equals("Exit")){
			System.exit(0);
		}
	}
}
