import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerBullet extends Bullet{
	static BufferedImage image;
	PlayerBullet(Coordinate spawn,int[] move){
		super(spawn,move);
		post = "Bulletup.png";
		try {
			image = ImageIO.read(new File(Game.pre+"Bulletup.png"));
		} catch (IOException e) {
		}
	}
	PlayerBullet(Coordinate spawn, int[] move, int width, int height){
		super(spawn,move,width,height);
		post = "Bulletup.png";
		try {
			image = ImageIO.read(new File(Game.pre+"Bulletup.png"));
		} catch (IOException e) {
		}
	}
}
