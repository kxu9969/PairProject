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
	final int INCREMENT_AMOUNT = 5;
	final int counterMax = 30;
	int counterDelay = 0;
	
	Game(String playerName){
		p = new Player(playerName);
		Enemy e= new Enemy();
		enemies.add(e);
		vis = new Visuals();
		this.add(vis);
		this.pack();
		this.setVisible(true);
		addKeyListener(this);
		t.schedule(new TimerTask(){
			public void run() {
				p.move();
				if(counterDelay == 0){
					playerBullets.add(new Bullet(p.hitbox.c1,new int[]{0,-3}));
					counterDelay = counterMax;
				}else{
					counterDelay--;
				}
				for(Enemy e: enemies){
					e.move();
				}
				for(Bullet b: playerBullets){
					b.move();
					//System.out.println(p.hitbox.c1.x+" "+p.hitbox.c1.y+" "+p.hitbox.c2.x+" "+p.hitbox.c2.y);
					for(Enemy e: enemies){
						if(b.hasHit(e)){
							b.hit();
							toBeRemoved.add(b);
						}
					}
					boolean moved = b.move();
					for(Enemy e: enemies){
						if(b.hasHit(e)){
							b.hit();
							toBeRemoved.add(b);
						}
					}
					if(!moved) {
						toBeRemoved.add(b);
					}
					
				}
				for(Bullet b: enemyBullets){
					boolean moved = b.move();
					if(b.hasHit(p)){
						b.hit();
						toBeRemoved.add(b);
					}
					if(!moved) {
						toBeRemoved.add(b);
					}
				}
				for(Bullet b: toBeRemoved){
					playerBullets.remove(b);
					enemyBullets.remove(b);
				}
				toBeRemoved.clear();
				vis.repaint();
			}
		}, 0, 10);
	}

	class Visuals extends JPanel{
		final static int WIDTH = 430;
		final static int HEIGHT = 580;
		Visuals(){
			this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
			ImageIcon background=new ImageIcon("Background.png");
			JLabel back=new JLabel();
			back.setIcon(background);
			back.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			back.setAlignmentY(JLabel.TOP_ALIGNMENT);
			this.add(back);
		}
		
		public void paint(Graphics g){
			super.paint(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 430, 10);
			g.fillRect(0, 0, 5, 580);
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
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			playerBullets.add(new Bullet(p.hitbox.c1,new int[]{0,-10}));
		}
	}

	public void keyTyped(KeyEvent e) {	
	}
	
	
	
	
}
