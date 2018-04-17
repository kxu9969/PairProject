
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
	void move(){
		
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
