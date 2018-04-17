
public class Player {
	String name;
	Hitbox hitbox;
	int[] increment = {0,0};
	final int WIDTH = 10;//placeholder vlaues, will draw the player instead of rectangle
	final int HEIGHT = 10;
	final int DEFAULT_START_X = Game.Visuals.WIDTH/2;
	final int DEFAULT_START_Y = Game.Visuals.HEIGHT-HEIGHT;
	final int bulletSpeed = 6;
	final int counterMax = 10;
	int counterDelay = 0;
	boolean flash = false;
	int flashMax = 20;
	int flashCounter = 0;
	boolean dead = false;
	int health = 2;
	int score=0;
	Player(String name){
		this.name = name;
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
		
	}

	void kill(){
		health = 0;
		dead = true;
	}
	
	void move(){
		hitbox.move(increment[0], increment[1]);
	}
	
	void whenHit(){
		//System.out.println("PLAYER HIT");
		flash = true;
		health--;
		if(health==0){
			dead = true;
		}
	}
}
