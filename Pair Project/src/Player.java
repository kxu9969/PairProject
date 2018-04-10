
public class Player {
	String name;
	Hitbox hitbox;
	int[] increment = {0,0};
	final int DEFAULT_START_X = 0;
	final int DEFAULT_START_Y = 0;
	final int WIDTH = 10;//placeholder vlaues, will draw the player instead of rectangle
	final int HEIGHT = 10;
	Player(String name){
		this.name = name;
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
		
	}

	void move(){
		hitbox.move(increment[0], increment[1]);
	}
	
	void whenHit(){
		
	}
}
