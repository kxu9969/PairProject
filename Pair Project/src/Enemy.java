
public class Enemy {
	Hitbox hitbox;
	int[] increment = {0,0};//needs functionality
	Enemy(){
		
	}
	void move(){
		hitbox.move(increment[0], increment[1]);
	}
}
