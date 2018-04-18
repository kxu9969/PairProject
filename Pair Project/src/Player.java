
public class Player {
	String name;
	Hitbox hitbox;
	int[] increment = {0,0};
	int WIDTH = 10;//placeholder values, will draw the player instead of rectangle
	int HEIGHT = 10;
	int DEFAULT_START_X = Game.Visuals.WIDTH/2;
	int DEFAULT_START_Y = Game.Visuals.HEIGHT-HEIGHT;
	int bulletSpeed = 6;
	int counterMax = 10;
	int counterDelay = 0;
	boolean flash = false;
	int flashMax = 8;
	int flashCounter = 0;
	boolean dead = false;
	int health = 100;
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
