import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class EndScreen extends JPanel implements ActionListener{
	JFrame screen;
	JButton playAgain,exit;
	String congratulations;
	String easyFile = "Z:/git/PairProject/Pair Project/src/EasyScore";
	String normalFile = "Z:/git/PairProject/Pair Project/src/NormalScore";
	String hardFile = "Z:/git/PairProject/Pair Project/src/HardScore";
	String difficulty;
	String userName;
	static int beforeScore=0;
	EndScreen(String score, String username, String dif){
		difficulty=dif;
		userName = username;
		String begin="Easy";
		if(difficulty.equals("Easy")){
				begin="src/Easy";
			}else if(difficulty.equals("Normal")){
				begin="src/Normal";
			}else if(difficulty.equals("Hard")){
				begin="src/Hard";
			}
		int typeOfScore = score(username,Integer.parseInt(score));
		if(typeOfScore==3){
			sort(new File(begin+"Score"),score+" "+username,true);
		}else{
			sort(new File(begin+"Score"),score+" "+username,false);
		}
		boolean localBest=false;
		try {
			//System.out.println("Yes");
			Scanner scan= new Scanner(new File(begin+"Score"));
			String nextLine = scan.nextLine();
		} catch (Exception e) {
			//System.out.println("Exception thrown");
			localBest=true;
		}
		if(Integer.parseInt(score)>beforeScore){
			localBest=true;
		}
		//
		//THIS PART STILL DONT WORK RIGHT
		//
		System.out.println(typeOfScore+" "+ localBest);
		if(typeOfScore==2||localBest){
			congratulations="New local highscore!";
		}else if((typeOfScore==1||typeOfScore==3)&&!localBest){
			congratulations="Personal best!";
		}else{
			congratulations="";
		}
		screen=new JFrame("Game Over");
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setLayout(new GridLayout(2,1,0,0));
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
		
		JLabel lblScore = new JLabel("<html>"+congratulations+"<br/>"+username+": "+score +"</html>");
		
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
		try {
			JPanel leaderboard = new JPanel();
			leaderboard.setLayout(new GridLayout(11,1,0,0));
			leaderboard.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
			Scanner in = new Scanner(new File("src/"+dif+"Score"));
			String[] top = new String[10];
			for(int i = 0;i<10;i++) {
				top[i]="---";
			}
			for(int i = 0;i<10&&in.hasNext();i++){
				top[i]=in.nextLine();
			}
			JLabel title = new JLabel("Leaderboard");
			title.setHorizontalAlignment(JLabel.CENTER);
			JLabel l0 = new JLabel(top[0]);
			JLabel l1 = new JLabel(top[1]);
			JLabel l2 = new JLabel(top[2]);
			JLabel l3 = new JLabel(top[3]);
			JLabel l4 = new JLabel(top[4]);
			JLabel l5 = new JLabel(top[5]);
			JLabel l6 = new JLabel(top[6]);
			JLabel l7 = new JLabel(top[7]);
			JLabel l8 = new JLabel(top[8]);
			JLabel l9 = new JLabel(top[9]);
			leaderboard.add(title);
			leaderboard.add(l0);
			leaderboard.add(l1);
			leaderboard.add(l2);
			leaderboard.add(l3);
			leaderboard.add(l4);
			leaderboard.add(l5);
			leaderboard.add(l6);
			leaderboard.add(l7);
			leaderboard.add(l8);
			leaderboard.add(l9);
			screen.add(leaderboard);
		} catch (FileNotFoundException e) {
		}

		screen.pack();
		screen.setVisible(true);
	}
	
	public int score(String username, int score){
		int type=-1; //1 is personal highscore, 2 is local highscore, 3 is new player, 0 is none to the left
		boolean newScore = false;
		boolean multipleScores=false;
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
				String something=nextLine;
				String integerInString=firstInt(nextLine)+"";
				String subOfString=(nextLine.substring(integerInString.length()+1));
				
				if(subOfString.equals(username)&&type==-1){
					if(firstInt(integerInString)>=score){
						type=0;
					}else{
						type=1;
					}
				}
				if(subOfString.equals(username)){
					multipleScores=true;
				}
			}
			if(score>highscore && type!=0){
				highscore = score;
				newScore = true;
				type=2;
			}
			
		}catch(Exception e){
		}
		if(type==-1||!multipleScores){
			type=3;
		}
		//System.out.println("Type:"+type);
		return type;
	}
	
	public static void sort(File f,String scoreName, boolean newPlayer){
		//rewrites the file with highest score to lowest,overwrites score of same player if needed
		int score = firstInt(scoreName);
		String name = scoreName.substring((""+score).length()+1);
		ArrayList<Integer> numList = new ArrayList<Integer>();
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> list = new ArrayList<String>();
		try {
			Scanner scan = new Scanner(f);
			while(scan.hasNext()){
				String str = scan.nextLine();
				list.add(str);
				int num = firstInt(str);
				if(num>beforeScore){
					beforeScore=num;;
				}
				String i = ""+num;
				str = str.substring(i.length()+1);
				numList.add(num);
				nameList.add(str);
			}
			boolean erase = false;
			if(!newPlayer){
				int eraseIndex = 0;
				for(int i = 0;i<nameList.size();i++){
					if(nameList.get(i).equals(name)){
						if(numList.get(i)<score){
							erase = true;
							eraseIndex = i;
						}
					}
				}
				if(erase){
					numList.remove(eraseIndex);
					nameList.remove(eraseIndex);
					list.remove(eraseIndex);
				}
			}else{
				erase = true;
			}
			boolean lock = false;
			if(erase){
				for(int i = 0;i<=numList.size()&&!lock;i++){
					if(i==numList.size()){
						list.add(scoreName);
						lock = true;
					}else if(numList.get(i)<score){
						list.add(i, scoreName);
						lock = true;
					} 
				}
			}
			PrintWriter out = new PrintWriter(f);
			for(int i = 0;i<list.size();i++){
				out.println(list.get(i));
//				System.out.println(list.get(i));
			}
			out.close();
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
			StartScreen start=new StartScreen(userName);
		}else if(whichButton.equals("Exit")){
			System.exit(0);
		}
	}
	
	public static void main(String[] args){
		EndScreen endscreen=new EndScreen("155", "Player 4", "Hard");
	}
	
}
