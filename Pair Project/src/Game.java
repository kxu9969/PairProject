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
	ArrayList<Enemy> ded = new ArrayList<Enemy>();
	ArrayList<Asteroid> steroids = new ArrayList<Asteroid>();
	ArrayList<Asteroid> noSteroids = new ArrayList<Asteroid>();
	Boss b;
	static Timer t = new Timer();
	final int INCREMENT_AMOUNT = 2;
	final int ENEMY_SPAWN_RATE = 500;
	final int ASTEROID_SPAWN_RATE = 700;
	boolean bossMode;


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
				if(!p.dead){
					moveShips();
					makeBullets();
					moveProjectiles();
					if((int)(Math.random()*ENEMY_SPAWN_RATE)==0){
						makeEnemies();
					}
					if((int)(Math.random()*ASTEROID_SPAWN_RATE)==0){
						makeAsteroid();
					}
					reset();
					vis.repaint();
				}else{
					this.cancel();
					gameOver();
				}
			}
		}, 0, 10);
	}
	
	private void moveShips(){
		p.move();		
		for(Enemy e: enemies){
			e.move();
			//System.out.println(e.hitbox.c1.x+" "+e.hitbox.c1.y+" "+e.hitbox.c2.x+" "+e.hitbox.c2.y);
		}
	}
	
	private void makeBullets(){
		if(!bossMode){
			if(p.counterDelay == 0){
				playerBullets.add(new Bullet(p.hitbox.c1,new int[]{0,-p.bulletSpeed}));
				p.counterDelay = p.counterMax;
			}else{
				p.counterDelay--;
			}
			for(Enemy e: enemies){
				if(e.counterDelay == 0){
					enemyBullets.add(new Bullet(e.hitbox.c1,new int[]{0,e.bulletSpeed}));
					e.counterDelay = e.counterMax;
				}else{
					e.counterDelay--;
				}
			}
		}else{
			b=new Boss();
			if(b.counterDelay == 0 && b.stage1){
				for(int i=0;(b.WIDTH/2)-i>0;i+=p.WIDTH*2){
					enemyBullets.add(new Bullet());
				}
			}
		}

	}

	private void moveProjectiles(){
		for(Asteroid a: steroids){
			boolean moved = a.move();
			if(a.hasHit(p)){
				p.kill();
			}
			if(!moved){
				noSteroids.add(a);
			}
		}
		for(Bullet b: playerBullets){
			boolean moved = b.move();
			for(Enemy e: enemies){
				if(b.hasHit(e)){
					e.whenHit();
					toBeRemoved.add(b);
					if(e.dead){
						ded.add(e);
						p.score++;
					}
				}for(Asteroid a: steroids){
					if(b.hasHit(a)){
						toBeRemoved.add(b);
					}
				}
			}
			if(!moved) {
				toBeRemoved.add(b);
			}

		}
		for(Bullet b: enemyBullets){
			boolean moved = b.move();
			if(b.hasHit(p)){
				p.whenHit();
				toBeRemoved.add(b);
			}
			for(Asteroid a: steroids){
				if(b.hasHit(a)){
					toBeRemoved.add(b);
				}
			}
			if(!moved) {
				toBeRemoved.add(b);
			}
		}
	}
	
	private void makeEnemies(){
		Enemy e = new Enemy();
		enemies.add(e);
	}
	
	private void gameOver() {
		this.setVisible(false);
		EndScreen endScreen=new EndScreen(p.score+"",p.name);
	}
	private void makeAsteroid(){
		int x = 0,y = 0;
		int[] increment;
		boolean horizontal;
		if(Math.random()<0.5){
			x = (int)(Math.random()*Game.Visuals.WIDTH)-Asteroid.WIDTH;
			increment = new int[]{0,2};
			horizontal = false;
		}else{
			y = (int)(Math.random()*Game.Visuals.HEIGHT)-Asteroid.HEIGHT;
			increment = new int[]{2,0};
			horizontal = true;
		}
		Asteroid a = new Asteroid(new Coordinate(x,y),increment,horizontal);
		steroids.add(a);
	}

	private void reset(){
		for(Bullet b: toBeRemoved){
			playerBullets.remove(b);
			enemyBullets.remove(b);
		}
		for(Enemy e: ded){
			enemies.remove(e);
		}
		for(Asteroid a: noSteroids){
			steroids.remove(a);
		}
		toBeRemoved.clear();
		ded.clear();
		noSteroids.clear();
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
			for(Enemy e : enemies){
				if(e.flash){
					g.setColor(Color.WHITE);
					if(e.flashCounter == 0){
						e.flash=false;
						e.flashCounter = e.flashMax;
					}else{
						e.flashCounter--;
					}
				}else{
					g.setColor(Color.RED);
				}
				g.fillRect(e.hitbox.getCornerX(), e.hitbox.getCornerY(), e.WIDTH, e.HEIGHT);
			}
			if(p.dead){
				g.setColor(Color.GRAY);
			}else if(p.flash){
				g.setColor(Color.WHITE);
				if(p.flashCounter == 0){
					p.flash=false;
					p.flashCounter = p.flashMax;
				}else{
					p.flashCounter--;
				}
			}else{
				g.setColor(Color.GREEN);
			}
			g.fillRect(p.hitbox.getCornerX(), p.hitbox.getCornerY(), p.WIDTH, p.HEIGHT);
			g.setColor(Color.YELLOW);
			for(Bullet b: enemyBullets){
				g.fillRect(b.hitbox.getCornerX(),b.hitbox.getCornerY(), b.WIDTH, b.HEIGHT);
			}
			g.setColor(Color.ORANGE);
			for(Bullet b: playerBullets){
				g.fillRect(b.hitbox.getCornerX(),b.hitbox.getCornerY(), b.WIDTH, b.HEIGHT);
			}

			for(Asteroid a: steroids){
				g.setColor(Color.RED);
				if(a.horizontal){
					g.drawLine(a.spawnX, a.spawnY, Game.Visuals.WIDTH, a.spawnY);
				}else{
					g.drawLine(a.spawnX, a.spawnY, a.spawnX, Game.Visuals.HEIGHT);
				}
				g.setColor(new Color(160,82,45));
				g.fillRect(a.hitbox.getCornerX(), a.hitbox.getCornerY(), a.WIDTH, a.HEIGHT);
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
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			makeAsteroid();
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

