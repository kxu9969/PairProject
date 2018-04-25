import java.awt.Color;
import java.util.ArrayList;

public class Boss extends Enemy{
	int[] increment = {0,0};//needs functionality
	boolean stage1=true;
	boolean stage2=false;
	boolean stage3=false;
	int lazorsMax=300;
	int lazorsDelay=lazorsMax;
	int lazorWarningMax = 30;
	int lazorWarningCounter = lazorWarningMax;
	int lazorCounterCounter= 0;
	boolean fireLazor = false;
	Boss(){
		DEFAULT_START_X = 105;
		DEFAULT_START_Y = 40;
		WIDTH=220;
		HEIGHT=40;
		health=300;
		counterMax=300;
		counterDelay=60;
		color= Color.MAGENTA;
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
		
	}
	
	ArrayList<Bullet> spawnBullets(){
		ArrayList<Bullet> a = new ArrayList<Bullet>();
		if(this.counterDelay == 0 && this.stage1){
			for(int i=0;(this.WIDTH/2)-i>0;i+=Player.WIDTH*3){//playerWidth*2
				if(i==0){
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));
				}else{
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2-i,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2+i,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));
				}
			}
			counterDelay = counterMax;
			if(fireLazor) {
				fireLazor=!fireLazor;
			}
		}
		if(this.counterDelay == 0 && this.stage2){
			counterDelay = counterMax;
		}
		
		
		if(health<200){
			stage2=true;
			stage1=false;
			stage3=false;
		}
		if(health<100){
			stage3=true;
			stage1=false;
			stage2=false;
		}

		return a;
	}
	
	void move(){
		
	}
	int getLazorsDelay(){
		return lazorsDelay;
	}
	
	class Lazor extends Bullet{
		int lazorTimer = 300;
		Boss b;
		Lazor(Boss b){
			super();
			this.b=b;
			WIDTH = 50;
			HEIGHT = Game.Visuals.HEIGHT-b.hitbox.c1.y;
			hitbox =new Hitbox(new Coordinate(b.hitbox.c1.x+b.WIDTH/2-25,b.hitbox.c1.y+b.HEIGHT),new Coordinate(b.hitbox.c1.x+b.WIDTH/2+25,Game.Visuals.HEIGHT));
		}
		
		boolean move() {
			if(lazorTimer!=0) {
				lazorTimer--;
				return true;
			}else {
				b.fireLazor=false;
				return false;
			}
		}
	}
	
}
