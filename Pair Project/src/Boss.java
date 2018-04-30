import java.awt.Color;
import java.util.ArrayList;

public class Boss extends Enemy{
	int[] increment = {0,0};//needs functionality
	boolean stage1=true;
	boolean stage2=false;
	boolean stage3=false;
	int lazorWarningMax = 30;
	int lazorWarningCounter = lazorWarningMax;
	int lazorCounterCounter= 0;
	boolean fireLazor = false;
	int novaBulletSpeed;
	final int burstMax = 25;
	final int burstCount = 2;
	int burstCounter = burstCount;
	Player human;
	Boss(Player p){
		DEFAULT_START_X = 105;
		DEFAULT_START_Y = 40;
		WIDTH=220;
		HEIGHT=40;
		health=300;
		counterMax=300;
		counterDelay=60;
		score=10;
		novaBulletSpeed=7;
		human=p;
		color= Color.MAGENTA;
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
		
	}
	
	ArrayList<Bullet> spawnBullets(){
		ArrayList<Bullet> a = new ArrayList<Bullet>();
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
		if(this.stage1){
			for(int i=0;(this.WIDTH/2)-i>0;i+=Player.WIDTH*3){//playerWidth*2
				if(i==0){
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));
				}else{
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2-i,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));
					a.add(new Bullet(new Coordinate(hitbox.c1.x+this.WIDTH/2+i,hitbox.c1.y+this.HEIGHT),new int[]{0,bulletSpeed}));
				}
			}
			counterDelay = counterMax;
		}
		if(this.stage2){
			if(burstCounter==0){
				burstCounter=burstCount;
				counterDelay = counterMax;
			}else{
				burstCounter--;
				counterDelay = burstMax;
			}
			int ran=(int) (Math.random()*90);
			boolean neg = Math.random()<0.5;
			if(neg){
				ran = -ran;
			}
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{0,novaBulletSpeed},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{1,novaBulletSpeed-1},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{2,novaBulletSpeed-2},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{3,novaBulletSpeed-3},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{4,novaBulletSpeed-4},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{5,novaBulletSpeed-5},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{6,novaBulletSpeed-6},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{-1,novaBulletSpeed-1},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{-2,novaBulletSpeed-2},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{-3,novaBulletSpeed-3},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{-4,novaBulletSpeed-4},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{-5,novaBulletSpeed-5},5,5));
			a.add(new Bullet(new Coordinate(hitbox.centerx+ran, hitbox.c2.y),new int[]{-6,novaBulletSpeed-6},5,5));
		}
		if(this.stage3){
			counterDelay=10;
			
		}
		
		

		return a;
	}
	
	void move(){
		
	}
	
	class Lazor extends Bullet{
		int lazorTimer = 100;
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
