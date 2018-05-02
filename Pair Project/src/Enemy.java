import java.awt.Color;
import java.util.ArrayList;

public class Enemy {
	Hitbox hitbox;
	int[] increment = {0,0};//needs functionality
	int WIDTH = 26;
	int HEIGHT = 19;
	int DEFAULT_START_X = Math.abs((int)(Math.random()*Game.Visuals.WIDTH-WIDTH));
	int DEFAULT_START_Y = (int)(Math.random()*40+1);
	int lineSwitchProbability= 1000;
	int yHover=(int) (Math.random()*290)+1;
	boolean moveLeft=false;
	boolean moveRight=true;
	int bulletSpeed = 3;
	int counterMax = 100;
	int counterDelay = (int)(Math.random()*(counterMax*2)+1);
	boolean flash = false;
	int flashMax = 8;
	int flashCounter = 0;
	boolean dead = false;
	int health = 4;
	int score = 1;
	Color color = Color.RED;
	String post = "Enemy.png";
	Enemy(String dif){
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
		if(Math.random()<0.5){
			moveLeft=!moveLeft;
			moveRight=!moveRight;
		}
	}
	
	ArrayList<Bullet> spawnBullets(){
		ArrayList<Bullet> a = new ArrayList<Bullet>();
		a.add(new Bullet(hitbox.c1,new int[]{0,bulletSpeed}));
		counterDelay = counterMax;
		return a;
	}
	int getCounterDelay() {
		return counterDelay;
	}
	
	void move(){
		int random=(int) (Math.random()*lineSwitchProbability);
		//System.out.println(random);
		if(random==0){
			//System.out.println("RANDOM");
			yHover=(int) (Math.random()*290)+1;
		}
		if(moveRight){
			increment[0]=1;
		}else if (moveLeft){
			increment[0]=-1;
		}
		if(hitbox.c2.x>=Game.Visuals.WIDTH-1){
			moveRight=false;
			moveLeft=true;
		}else if(hitbox.getCornerX()<=1){
			moveRight=true;
			moveLeft=false;
		}
		
		if(hitbox.getCornerY()>yHover){
			increment[1]=-1;
		}else if(hitbox.getCornerY()<yHover){
			increment[1]=1;
		}else if(hitbox.getCornerY()==yHover){
			increment[1]=0;
		}
		hitbox.move(increment[0], increment[1]);
	}
	
	void whenHit(){
		//System.out.println("Enemy hit!");
		flash = true;
		health--;
		if(health==0){
			dead = true;
		}
	}
}
