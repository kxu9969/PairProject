import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
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
	Boss boss;
	String difficulty;
	static Timer t = new Timer();
	int INCREMENT_AMOUNT = 2;
	int ASTEROID_SPAWN_RATE = 700;
	boolean bossMode;
	int WAVE_DELAY = 200;//one zero smaller because updates every 10 ms
	int waveTimer = 400;
	int waveCounter = 3;
	//static String pre = "/Users/kyang/git/PairProject/Pair Project/";
	static String pre = "img/";
	Image enemyBullet, playerBullet, enemyFlash, sloopFlash;


	Game(String playerName, String dif){
		difficulty=dif;
		this.setSize(new Dimension(Game.Visuals.WIDTH+5,Game.Visuals.HEIGHT+30));
		this.setResizable(false);
		p = new Player(playerName);
		vis = new Visuals();
		this.add(vis);
		this.setVisible(true);
		addKeyListener(this);
		try{
			enemyBullet=ImageIO.read(new File("img/Bulletdown.png"));
			enemyFlash=ImageIO.read(new File("img/Enemygrey.png"));
			playerBullet=ImageIO.read(new File("img/Bulletup.png"));
			sloopFlash=ImageIO.read(new File("img/Sloopgrey.png"));
		}catch(Exception e){
		}
		t.schedule(new TimerTask(){
			public void run() {
				if(!p.dead){
					moveShips();
					makeBullets();
					moveProjectiles();
					if(enemies.size()==0) {
						if(waveTimer==0) {
							makeEnemies();
							waveTimer=WAVE_DELAY;
						}
						else {
							waveTimer--;
						}
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
			playerBullets.add(new PlayerBullet(new Coordinate(p.hitbox.c1.x+p.bulletSpawn,p.hitbox.c1.y),new int[]{0,-p.bulletSpeed}));
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
						p.score+=e.score;
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
			if(b instanceof Boss.Lazor){
				if(b.hasHit(p)){
					//System.out.println("OW");
					p.whenHit(1);
				}
			}
			else if(b.hasHit(p)){
				p.whenHit();
				toBeRemoved.add(b);
			}
			for(Asteroid a: steroids){
				if(b.hasHit(a) && !(b instanceof Boss.Lazor)){
					toBeRemoved.add(b);
				}
			}
			if(!moved) {
				toBeRemoved.add(b);
			}
		}
		
	}
	
	private void makeEnemies(){
		int enemyCount = 4+(int)(waveCounter*1.15);
		for(int i = 0;i<enemyCount;i++){
			Enemy e = new Enemy(difficulty);
			enemies.add(e);
		}
		if(waveCounter>3){
			enemyCount = (int)(waveCounter*1.15);
			for(int i = 0;i<enemyCount;i++){
				Enemy e = new Sloop(difficulty);
				enemies.add(e);
			}
		}
		if(waveCounter!=0&&waveCounter%10==0 ){//Boss wave; make waveCounter%10==0//Boss wave; make waveCounter%10==0			
			System.out.println("BOSS ROUND");
			bossMode=true;
			boss = new Boss(p,difficulty);
			enemies.add(boss);
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
			ImageIcon background=new ImageIcon(pre + "Background (1).jpg");
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
					if(e.flashCounter == 0){
						e.flash=false;
						e.flashCounter = e.flashMax;
					}else{
						e.flashCounter--;
					}
					try {
						if(e instanceof Sloop){
							g.drawImage(sloopFlash, e.hitbox.getCornerX(), e.hitbox.getCornerY(), null);
						}else{
							g.drawImage(enemyFlash, e.hitbox.getCornerX(), e.hitbox.getCornerY(), null);
						}
						
					} catch (Exception e1) {
					}
				}else{
					try {
						g.drawImage(ImageIO.read(new File(pre+e.post)), e.hitbox.getCornerX(), e.hitbox.getCornerY(), null);
					} catch (Exception e1) {
					}
				}
			}
			
			if(p.warp) {//draw Player
				if(p.warpCounter == 0){
					p.warp=false;
					p.warpCounter = p.warpMax;
				}else{
					p.warpCounter--;
				}
				try {
					g.drawImage(ImageIO.read(new File(pre+p.post.substring(0, p.post.length()-4)+"dark.png")), p.warpBox.getCornerX(), p.warpBox.getCornerY(), null);
					g.drawImage(ImageIO.read(new File(pre+p.post.substring(0, p.post.length()-4)+"light.png")), p.hitbox.getCornerX(), p.hitbox.getCornerY(), null);
				} catch (IOException e1) {
				}
			}else if(p.flash){
				if(p.flashCounter == 0){
					p.flash=false;
					p.flashCounter = p.flashMax;
				}else{
					p.flashCounter--;
				}
				try {
					g.drawImage(ImageIO.read(new File(pre+p.post.substring(0, p.post.length()-4)+"grey.png")), p.hitbox.getCornerX(), p.hitbox.getCornerY(), null);
				} catch (IOException e1) {
				}
			}else {
				try {
					g.drawImage(ImageIO.read(new File(pre+p.post)), p.hitbox.getCornerX(), p.hitbox.getCornerY(), null);
				} catch (IOException e1) {
				}
			}
			for(Bullet b: enemyBullets){
				if(b instanceof Boss.Lazor) {
					g.setColor(Color.CYAN);
					g.fillRect(b.hitbox.getCornerX(),b.hitbox.getCornerY(), b.WIDTH, b.HEIGHT);
				}else {
					try {
						g.drawImage(enemyBullet, b.hitbox.getCornerX(), b.hitbox.getCornerY(), null);
					} catch (Exception e1) {
				}
					//g.setColor(Color.YELLOW);
					//g.fillRect(b.hitbox.getCornerX(),b.hitbox.getCornerY(), b.WIDTH, b.HEIGHT);
				}
				
			}
			for(Bullet b: playerBullets){
				g.drawImage(PlayerBullet.image, b.hitbox.getCornerX(), b.hitbox.getCornerY(), null);
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
			if(bossMode && boss.stage1){
				if(!boss.fireLazor){
					if(boss.lazorWarningCounter<30) {
						if(boss.lazorWarningCounter>5){
							g.setColor(Color.RED);
							g.drawLine(boss.DEFAULT_START_X+boss.WIDTH/2, boss.DEFAULT_START_Y+boss.HEIGHT, boss.DEFAULT_START_X+boss.WIDTH/2, this.HEIGHT);
						}
						boss.lazorWarningCounter--;
						if(boss.lazorWarningCounter<-10){
							boss.lazorWarningCounter=boss.lazorWarningMax;
							boss.lazorCounterCounter++;
						}
						if(boss.lazorCounterCounter == 3) {
							boss.lazorCounterCounter = 0;
							boss.lazorWarningCounter = boss.lazorWarningMax;
							boss.fireLazor = true;
							enemyBullets.add(boss.new Lazor(boss));
							boss.lazorWarningCounter=50; //increase for delay between stuff
						}
					}
					else {
						boss.lazorWarningCounter--;
					}
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

