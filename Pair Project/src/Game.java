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
	int INCREMENT_AMOUNT = 2;
	int ASTEROID_SPAWN_RATE = 700;
	boolean bossMode;
	int WAVE_DELAY = 1000;//one zero smaller because updates every 10 ms
	int waveTimer = WAVE_DELAY/2;
	int waveCounter = 1;
	int lazorWarningCounter=10;


	Game(String playerName){
		this.setSize(new Dimension(Game.Visuals.WIDTH+5,Game.Visuals.HEIGHT+30));
		this.setResizable(false);
		p = new Player(playerName);
		vis = new Visuals();
		this.add(vis);
		this.setVisible(true);
		addKeyListener(this);
		t.schedule(new TimerTask(){
			public void run() {
				if(!p.dead){
					moveShips();
					makeBullets();
					moveProjectiles();
					if(waveTimer == 0){
						makeEnemies();
						waveTimer = WAVE_DELAY;
					}else{
						waveTimer--;
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
		if(p.counterDelay == 0){
			playerBullets.add(new Bullet(p.hitbox.c1,new int[]{0,-p.bulletSpeed}));
			p.counterDelay = p.counterMax;
		}else{
			p.counterDelay--;
		}
		for(Enemy e: enemies){
			if(e.getCounterDelay() == 0){
				ArrayList<Bullet> a = e.spawnBullets();
				for(Bullet b:a){
					enemyBullets.add(b);
				}
			}else{
				e.counterDelay--;
			}
			if(bossMode){
				if(b.lazorsDelay==0){
					Hitbox lazorHitbox=new Hitbox(new Coordinate(b.hitbox.c1.x+b.WIDTH/2-25,b.hitbox.c1.y+b.HEIGHT),
							new Coordinate(b.hitbox.c1.x+b.WIDTH/2+25,Game.Visuals.HEIGHT));
				}else{
					b.lazorsDelay--;
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
			for(Enemy e: enemies){
				if(a.hasHit(e)&&!(e instanceof Boss)){
					ded.add(e);
				}
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
		if(bossMode && b.lazorsDelay==0){
			if(b.lazor.lazorHitbox.contact(p.hitbox)){
				//System.out.println("OW");
				p.whenHit(1);
			}
		}
	}
	
	private void makeEnemies(){
		int enemyCount = 4+(int)(waveCounter*1.15);
		for(int i = 0;i<enemyCount;i++){
			Enemy e = new Enemy();
			enemies.add(e);
		}
		if(waveCounter>3){
			enemyCount = (int)(waveCounter*1.15);
			for(int i = 0;i<enemyCount;i++){
				Enemy e = new Sloop();
				enemies.add(e);
			}
		}
		if(waveCounter!=0&&waveCounter%10==0 ){//Boss wave; make waveCounter%10==0//Boss wave; make waveCounter%10==0			
			System.out.println("BOSS ROUND");
			bossMode=true;
			b = new Boss();
			enemies.add(b);
		}
		waveCounter++;
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
		if(DoublePress.cooldown!=0){
			DoublePress.cooldown--;
		}
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
			g.fillRect(0, 0, 450, 10);
			g.fillRect(0, 0, 10, 600);
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
					g.setColor(e.color);
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
			if(bossMode && b.lazorsDelay<=15){
				if(lazorWarningCounter<=0){
					g.setColor(Color.RED);
					g.drawLine(b.DEFAULT_START_X+b.WIDTH/2, b.DEFAULT_START_Y+b.HEIGHT, b.DEFAULT_START_X+b.WIDTH/2, this.HEIGHT);
					lazorWarningCounter--;
					if(lazorWarningCounter==-10){
						lazorWarningCounter=10;
						
					}
					
				}else{
					lazorWarningCounter--;
				}
				if(b.lazorsDelay==0){
					g.setColor(Color.CYAN);
					g.fillRect(b.hitbox.c1.x+b.WIDTH/2-25,b.hitbox.c1.y+b.HEIGHT,50,Game.Visuals.HEIGHT-b.hitbox.c1.y);
					b.lazorsDelay=30;
				}
				
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
		if (DoublePress.cooldown==0 && DoublePress.released && DoublePress.isDoublePress(e) && DoublePress.lastKeyCode == e.getKeyCode()) {
			int scalar = 0;
			p.barrelRoll = true;;
			DoublePress.cooldown = DoublePress.cooldownMax;
		} 
		DoublePress.released = false;
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
		DoublePress.released = true;
	}

	public void keyTyped(KeyEvent e) {	
	}
	

	static class DoublePress {
	 
	    private static int doublePressTime = 200; // double keypressed in ms
	    private static long timeKeyDown = 0;       // last keyperessed time
	    public static int lastKeyCode;
	    public static boolean released = false;
	    public static int cooldownMax = 100;
	    public static int cooldown = cooldownMax;
	 
	    public static  boolean isDoublePress(KeyEvent ke) {
	        if ((ke.getWhen() - timeKeyDown) < doublePressTime&&ke.getKeyCode()==lastKeyCode) {
	            return true;
	        } else {
	            timeKeyDown = ke.getWhen();
	        }
	        lastKeyCode = ke.getKeyCode();
	        return false;
	    }
	}




}

