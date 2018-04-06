
public class Bullet {
	Hitbox hitbox;
	int[] increment = {0,0};
	Bullet(Hitbox hitbox){
		this.hitbox=hitbox;
	}
	
	void move(){
		hitbox.move(increment[0], increment[1]);
	}
	void hit(){
		//makes an explosion and does stuff
	}
	
	boolean hasHit (Player p){
		return hitbox.contact(p.hitbox);
	}
	boolean hasHit (Enemy e){
		return hitbox.contact(e.hitbox);
	}
}
