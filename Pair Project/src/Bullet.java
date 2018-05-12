import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {
	Hitbox hitbox;
	int[] increment = {0,0};
	int WIDTH = 5;//placeholder values, will instead draw the bullet image not a rectangle
	int HEIGHT = 12;
	String post = "Bulletdown.png";
	Image bullet;
	Bullet(){
		
	}
	
	Bullet(Coordinate spawn,int[] move){
		hitbox= new Hitbox(new Coordinate(spawn.x,spawn.y),new Coordinate(spawn.x+WIDTH,spawn.y+HEIGHT));
		increment[0]=move[0];
		increment[1]=move[1];
		try {
			bullet = ImageIO.read(new File("img/Bulletdown.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Bullet(Coordinate spawn, int[] move, int width, int height){
		this.WIDTH = width;
		this.HEIGHT = height;
		hitbox= new Hitbox(new Coordinate(spawn.x,spawn.y),new Coordinate(spawn.x+WIDTH,spawn.y+HEIGHT));
		increment[0]=move[0];
		increment[1]=move[1];
		try {
			bullet = ImageIO.read(new File("img/BallBullet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	boolean hasHit(Asteroid a){
		return hitbox.contact(a.hitbox);
	}
}
