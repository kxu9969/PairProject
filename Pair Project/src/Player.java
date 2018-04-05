
public class Player {
	String name;
	Hitbox hitbox;
	int[] increment = {0,0};
	final int DEFAULT_START_X = 0;
	final int DEFAULT_START_Y = 0;
	final int DEFAULT_WIDTH = 10;
	final int DEFAULT_HEIGHT = 10;
	Player(String name){
		this.name = name;
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+DEFAULT_WIDTH,DEFAULT_START_Y+DEFAULT_HEIGHT));
		
	}

	void move(){
		hitbox.move(increment[0], increment[1]);
	}
}
