
public class Asteroid {
	Hitbox hitbox;
	int[] increment = {0,0};
	final int WIDTH = 10;
	final int HEIGHT = 10;
	int health;
	
	Asteroid(Coordinate spawn, int[] move){
		hitbox = new Hitbox(new Coordinate(spawn.x,spawn.y),new Coordinate(spawn.x+WIDTH,spawn.y+HEIGHT));
		increment[0] = move[0];
		increment[1] = move[1];
	}
	
	void move(){
		
	}
}
