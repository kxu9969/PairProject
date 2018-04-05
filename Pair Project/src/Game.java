import javax.swing.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JFrame{
	Visuals vis;
	Player p;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	ArrayList<Bullet> toBeRemoved = new ArrayList<Bullet>();
	static Timer t = new Timer();
	
	Game(String playerName){
		p = new Player(playerName);
		vis = new Visuals();
		this.add(vis);
		this.pack();
		this.setVisible(true);		
		t.schedule(new TimerTask(){
			public void run() {
				p.move();
				for(Enemy e: enemies){
					e.move();
				}
				for(Bullet b: playerBullets){
					b.move();
					for(Enemy e: enemies){
						if(b.hasHit(e)){
							//if enemy is hit
							toBeRemoved.add(b);
						}
					}
				}
				for(Bullet b: enemyBullets){
					b.move();
					if(b.hasHit(p)){
						//if player is hit
						toBeRemoved.add(b);
					}
				}
				for(Bullet b: toBeRemoved){
					playerBullets.remove(b);
					enemyBullets.remove(b);
				}
				vis.repaint();
				//also calculate crap
			}
		}, 0, 10);
	}
	
	
	
	
}
