
public class Bullet {
	Hitbox hitbox;
	int[] increment = {0,0};
	final int WIDTH = 5;//placeholder values, will instead draw the bullet image not a rectangle
	final int HEIGHT = 10;
	Bullet(Coordinate spawn,int[] move){
		hitbox= new Hitbox(new Coordinate(spawn.x,spawn.y),new Coordinate(spawn.x+WIDTH,spawn.y+HEIGHT));
		increment[0]=move[0];
		increment[1]=move[1];
	}
	
	boolean move(){//returns true if the bullet moved
		return hitbox.move(increment[0], increment[1]);
	}
	
	boolean hasHit (Player p){
		return hitbox.contact(p.hitbox);
	}
	boolean hasHit (Enemy e){
		return hitbox.contact(e.hitbox);
	}
}
