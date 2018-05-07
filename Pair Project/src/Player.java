import javax.imageio.ImageIO;

public class Player {
	String name;
	Hitbox hitbox;
	int[] increment = {0,0};
	static int WIDTH = 30;//placeholder values, will draw the player instead of rectangle
	static int HEIGHT = 22;
	int DEFAULT_START_X = Game.Visuals.WIDTH/2;
	int DEFAULT_START_Y = Game.Visuals.HEIGHT-HEIGHT;
	int bulletSpeed = 8;
	int counterMax = 10;
	int counterDelay = 0;
	int barrelRollScalar = 40;
	boolean flash = false;
	int flashMax = 8;
	int flashCounter = flashMax;
	boolean dead = false;
	boolean barrelRoll = false;
	int maxHealth = 100;
	int health = maxHealth;
	int score=0;
	int bulletSpawn=13;
	boolean damage1 = false;
	boolean damage2 = false;
	boolean warp = false;
	int warpMax = 10;
	int warpCounter = warpMax;
	Hitbox warpBox;
	String post = "Player1.png";
	
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
			warpBox = new Hitbox(new Coordinate(hitbox.c1.x,hitbox.c1.y),new Coordinate(hitbox.c2.x,hitbox.c2.y));
			warp=true;
			hitbox.move(increment[0]*barrelRollScalar, increment[1]*barrelRollScalar,true);
			barrelRoll = false;
		}else{
			hitbox.move(increment[0], increment[1]);
		}
	}
	
	void whenHit(){
		flash = true;
		health--;
		if(health==0){
			dead = true;
		}else if(health<33) {
			damage1 = false;
			damage2 = true;
			post = "Player3.png";
			WIDTH = 21;
		}else if(health<66) {
			damage1 = true;
			damage2 = false;
			post = "Player2.png";
			WIDTH = 25;
			bulletSpawn = 8;
		}
	}
	void whenHit(int i){
		flash = true;
		health--;
		if(health==0){
			dead = true;
		}else if(health<33) {
			damage1 = false;
			damage2 = true;
			post = "Player3.png";
			WIDTH = 21;
		}else if(health<66) {
			damage1 = true;
			damage2 = false;
			post = "Player2.png";
			WIDTH = 25;
			bulletSpawn = 8;
		}
	}
}
