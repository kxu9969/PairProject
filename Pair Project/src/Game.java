import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

	class Visuals extends JFrame{
		final int WIDTH = 500;
		final int HEIGHT = 500;
		Visuals(){
			this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		}
		
		public void paint(Graphics g){
			super.paint(g);
			g.setColor(Color.RED);
			for(Enemy e : enemies){
				g.fillRect(e.hitbox.getCornerX(), e.hitbox.getCornerY(), e.WIDTH, e.HEIGHT);
			}
			g.setColor(Color.GREEN);
			g.fillRect(p.hitbox.getCornerX(), p.hitbox.getCornerY(), p.WIDTH, p.HEIGHT);
			g.setColor(Color.YELLOW);
			for(Bullet b: enemyBullets){
				g.fillRect(b.hitbox.getCornerX(),b.hitbox.getCornerY(), b.WIDTH, b.HEIGHT);
			}
			g.setColor(Color.ORANGE);
			for(Bullet b: playerBullets){
				g.fillRect(b.hitbox.getCornerX(),b.hitbox.getCornerY(), b.WIDTH, b.HEIGHT);
			}
			//code here to draw explosions of blown up bullets and ships
		}
		
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
