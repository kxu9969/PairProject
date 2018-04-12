
public class Asteroid {
	Hitbox hitbox;
	int[] increment = {0,0};
	final static int WIDTH = 30;
	final static int HEIGHT = 30;
	int health;
	int spawnX;
	int spawnY;
	boolean horizontal;
	
	Asteroid(Coordinate spawn, int[] move,boolean horizontal){
		this.horizontal = horizontal;
		if(horizontal){
			spawnX = spawn.x;
			spawnY = (spawn.y+spawn.y+HEIGHT)/2;
		}else{
			spawnX = (spawn.x+spawn.x+WIDTH)/2;
			spawnY = spawn.y;
		}
		
		hitbox = new Hitbox(new Coordinate(spawn.x,spawn.y),new Coordinate(spawn.x+WIDTH,spawn.y+HEIGHT));
		increment[0] = move[0];
		increment[1] = move[1];
	}
	
	boolean move(){
		spawnX+=increment[0];
		spawnY+=increment[1];
		return hitbox.move(increment[0], increment[1]);
	}
	
	boolean hasHit (Player p){
		return hitbox.contact(p.hitbox);
	}
	boolean hasHit (Enemy e){
		return hitbox.contact(e.hitbox);
	}
}
