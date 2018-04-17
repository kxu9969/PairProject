import java.awt.Color;
import java.util.ArrayList;

public class Sloop extends Enemy{
	final int burstMax = 20;
	final int burstCount = 2;
	int burstCounter = burstCount;
	Sloop(){
		WIDTH = 30;
		health = 3;
		color = Color.BLUE;
		counterMax = 300;
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
	}
	ArrayList<Bullet> spawnBullets(){
		ArrayList<Bullet> a = new ArrayList<Bullet>();
		a.add(new Bullet(hitbox.c1,new int[]{0,bulletSpeed}));
		a.add(new Bullet(new Coordinate(hitbox.c2.x,hitbox.c1.y),new int[]{0,bulletSpeed}));
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
