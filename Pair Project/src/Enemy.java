
public class Enemy {
	Hitbox hitbox;
	int[] increment = {0,0};//needs functionality
	final int DEFAULT_START_X = 0;
	final int DEFAULT_START_Y = 0;
	final int WIDTH = 10;
	final int HEIGHT = 10;
	final int height=(int) (Math.random()*290)+1;
	boolean moveLeft=false;
	boolean moveRight=true;
	final int bulletSpeed = 1;
	final int counterMax = 30;
	int counterDelay = 0;
	boolean flash = false;
	int flashMax = 8;
	int flashCounter = 0;
	Enemy(){
		hitbox = new Hitbox(new Coordinate(DEFAULT_START_X,DEFAULT_START_Y),
				new Coordinate(DEFAULT_START_X+WIDTH,DEFAULT_START_Y+HEIGHT));
	}
	void move(){
		if(moveRight){
			increment[0]=1;
		}else if (moveLeft){
			increment[0]=-1;
		}
		if(hitbox.getCornerX()==420){
			moveRight=false;
			moveLeft=true;
		}else if(hitbox.getCornerX()==0){
			moveRight=true;
			moveLeft=false;
		}
		increment[1]=1;
		if(hitbox.getCornerY()>=height){
			increment[1]=0;
		}
		hitbox.move(increment[0], increment[1]);
	}
	
	void whenHit(){
		System.out.println("Enemy hit!");
		flash = true;
	}
}
