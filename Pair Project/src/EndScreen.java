import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class EndScreen extends JPanel implements ActionListener{
	JFrame screen;
	JButton playAgain,exit;
	String easyFile = "Z:/git/PairProject/Pair Project/src/EasyScore";
	String normalFile = "Z:/git/PairProject/Pair Project/src/NormalScore";
	String hardFile = "Z:/git/PairProject/Pair Project/src/HardScore";
	String difficulty;
	EndScreen(String score, String username, String dif){
		difficulty=dif;
		int highscore = score(username,Integer.parseInt(score));
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
		
		JLabel lblScore = new JLabel(username+": "+highscore + " "+score);
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
	
	public int score(String username, int score){
		boolean newScore = false;
		String nextLine;
		String Highscore = "0";
		int highscore = 0;
		String level="";
		if(difficulty.equals("Easy")){
			level="src/Easy";
		}else if(difficulty.equals("Normal")){
			level="src/Normal";
		}else if(difficulty.equals("Hard")){
			level="src/Hard";
		}
		try{
			Scanner scan = new Scanner(new File(level+"Score"));
			while(scan.hasNext()){
				nextLine = scan.nextLine();
				if(firstInt(nextLine)!=-1&&firstInt(nextLine)>highscore){
					highscore = firstInt(nextLine);
				}
			}
			if(score>highscore){
				highscore = score;
				newScore = true;
			}
			PrintWriter out = new PrintWriter(new File(level+"Score"));
			out.print(highscore+" "+username);
			out.close();
		}catch(Exception e){
		}
		return highscore;
	}
	
	public static void sort(File f,String scoreName){
		//rewrites the file with highest score to lowest,overwrites score of same player if needed
		int i = firstInt(scoreName);
		try {
			Scanner scan = new Scanner(f);
			PrintWriter out = new PrintWriter(f);
			while(scan.hasNext()){
				if()
			}
		} catch (FileNotFoundException e) {
		}
		
	}
	
	public static int firstInt(String str){//returns -1 if not found
		int index = 0;
		int i = -1;
		String finalString="";
		boolean go = true;
		while(go&&index<str.length()){
			if(Character.isDigit(str.charAt(index))){
				finalString+=str.charAt(index);
			}else{
				go=false;
			}
			index++;
		}
		try{
			i = Integer.parseInt(finalString);
		}catch(Exception e){
		}
		return i;
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
