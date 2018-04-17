import java.awt.Color;
import java.util.ArrayList;

public class Sloop extends Enemy{
	final int WIDTH = 30;
	int health = 3;
	Color color = Color.BLUE;
	final int counterMax = 300;
	int counterDelay = 0;
	final int burstMax = 20;
	final int burstCount = 2;
	int burstCounter = burstCount;
	
	ArrayList<Bullet> spawnBullets(){
		ArrayList<Bullet> a = new ArrayList<Bullet>();
		if(burstCount!=0){
			burstCounter=burstCount;
		}else{
			burstCounter--;
		}
	}
}
