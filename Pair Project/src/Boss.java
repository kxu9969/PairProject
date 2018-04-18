import java.awt.Color;
import java.util.ArrayList;

public class Boss extends Enemy{
	Hitbox hitbox;
	int[] increment = {0,0};//needs functionality
	boolean stage1=true;
	boolean stage2=false;
	boolean stage3=false;
	Boss(){
		DEFAULT_START_X = 105;
		DEFAULT_START_Y = 40;
		WIDTH=220;
		HEIGHT=40;
		health=30;
		counterMax=300;
		color= Color.MAGENTA;
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
