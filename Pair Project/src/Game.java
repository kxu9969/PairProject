import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JFrame{
	Visuals vis;
	Player p;
	static Timer t = new Timer();
	
	Game(String playerName){
		p = new Player(playerName);
		vis = new Visuals();
		this.add(vis);
		this.pack();
		this.setVisible(true);		
		t.schedule(new TimerTask(){
			public void run() {
				vis.repaint();
				//also calculate crap
			}
		}, 0, 10);
	}
	
	
	
	
}
