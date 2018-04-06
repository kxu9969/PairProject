import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JFrame implements KeyListener{
	Visuals vis;
	Player p;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	ArrayList<Bullet> toBeRemoved = new ArrayList<Bullet>();
	static Timer t = new Timer();
	final int INCREMENT_AMOUNT = 10;
	
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
							b.hit();
							toBeRemoved.add(b);
						}
					}
				}
				for(Bullet b: enemyBullets){
					b.move();
					if(b.hasHit(p)){
						b.hit();
						toBeRemoved.add(b);
					}
				}
				for(Bullet b: toBeRemoved){
					playerBullets.remove(b);
					enemyBullets.remove(b);
				}
				vis.repaint();
			}
		}, 0, 10);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP ){
			p.increment[1]=-INCREMENT_AMOUNT;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			p.increment[1] = INCREMENT_AMOUNT;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			p.increment[0] = -INCREMENT_AMOUNT;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			p.increment[0] = INCREMENT_AMOUNT;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			p.increment[1]=0;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			p.increment[1] = 0;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			p.increment[0] = 0;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			p.increment[0] = 0;
		}
	}

	public void keyTyped(KeyEvent e) {	
	}
	
	
	
	
}
