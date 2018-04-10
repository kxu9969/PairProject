
public class Enemy {
	Hitbox hitbox;
	int[] increment = {0,0};//needs functionality
	final int DEFAULT_START_X = 0;
	final int DEFAULT_START_Y = 0;
	final int WIDTH = 10;
	final int HEIGHT = 10;
	Enemy(){
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
	}
	void move(){
		int height=(int) (Math.random()*290);//add stuff about having it move randomly left and right
		hitbox.move(increment[0], increment[1]);
	}
}
