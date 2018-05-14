import java.awt.Color;
import java.util.ArrayList;

public class Sloop extends Enemy{
	final int burstMax = 50;
	final int burstCount = 2;
	int burstCounter = burstCount;
	Sloop(String dif){
		super(dif);
		WIDTH = 37;
		HEIGHT = 24;
		if(dif.equals("Easy")){
			health=2;
		}else if(dif.equals("Normal")){
			health=5;
		}else if(dif.equals("Hard")){
			health=7;
		}
		DEFAULT_START_X = Math.abs((int)(Math.random()*Game.Visuals.WIDTH-WIDTH));
		DEFAULT_START_Y = (int)(Math.random()*40+1);
		color = Color.BLUE;
		counterMax = 400;
		bulletSpeed = 2;
		score = 2;
		post = "Sloop.png";
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
	}
	
	ArrayList<Bullet> spawnBullets(){
		ArrayList<Bullet> a = new ArrayList<Bullet>();
		a.add(new Bullet(hitbox.c1,new int[]{0,bulletSpeed},7,7));
		a.add(new Bullet(new Coordinate(hitbox.c2.x,hitbox.c1.y),new int[]{0,bulletSpeed},7,7));
		if(burstCounter==0){
			burstCounter=burstCount;
			counterDelay = counterMax;
		}else{
			burstCounter--;
			counterDelay = burstMax;
		}
		return a;
	}
}
