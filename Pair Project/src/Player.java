
public class Player {
	String name;
	Hitbox hitbox;
	int[] increment = {0,0};
	static int WIDTH = 10;//placeholder values, will draw the player instead of rectangle
	static int HEIGHT = 10;
	int DEFAULT_START_X = Game.Visuals.WIDTH/2;
	int DEFAULT_START_Y = Game.Visuals.HEIGHT-HEIGHT;
	int bulletSpeed = 6;
	int counterMax = 10;
	int counterDelay = 0;
	int barrelRollScalar = 40;
	boolean flash = false;
	int flashMax = 8;
	int flashCounter = 0;
	boolean dead = false;
	boolean barrelRoll = false;
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
		if(barrelRoll){
			hitbox.move(increment[0]*barrelRollScalar, increment[1]*barrelRollScalar);
			barrelRoll = false;
		}else{
			hitbox.move(increment[0], increment[1]);
		}
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
