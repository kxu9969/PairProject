import java.util.ArrayList;

public class Boss extends Enemy{
	Hitbox hitbox;
	int[] increment = {0,0};//needs functionality
	final int DEFAULT_START_X = 105;
	final int DEFAULT_START_Y = 40;
	final int WIDTH = 220;
	final int HEIGHT = 40;
	final int bulletSpeed = 1;
	final int counterMax = 30;
	int counterDelay = 0;
	boolean flash = false;
	int flashMax = 8;
	int flashCounter = 0;
	boolean dead = false;
	int health = 30;
	boolean stage1=true;
	boolean stage2=false;
	boolean stage3=false;
	Boss(){
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
	}
	
	ArrayList<Bullet> spawnBullets(){
		ArrayList<Bullet> a = new ArrayList<Bullet>();
		if(this.counterDelay == 0 && this.stage1){
			for(int i=0;(this.WIDTH/2)-i>0;i+=this.WIDTH*2){
				if(i==0){
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));
				}else{
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2-i,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2+i,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));

				}
			}
		}
		return a;
	}
	
	void move(){
		
	}
}
