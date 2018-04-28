
public class PlayerBullet extends Bullet{
	PlayerBullet(Coordinate spawn,int[] move){
		super(spawn,move);
		post = "Bulletup.png";
	}
	PlayerBullet(Coordinate spawn, int[] move, int width, int height){
		super(spawn,move,width,height);
		post = "Bulletup.png";
	}
}
