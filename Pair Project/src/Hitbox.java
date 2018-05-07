
public class Hitbox {
	Coordinate c1, c2;//c1 is always left-most
	int centerx,centery,width,height;
	
	Hitbox(Coordinate c1, Coordinate c2){//should always pass c1 upperleft, c2 lowerright
		this.c1 = c1;
		this.c2 = c2;
		centerx = Math.abs((c1.x+c2.x)/2); //may have an error here with int cutoffs and decimals
		centery = Math.abs((c1.y+c2.y)/2);
		width = Math.abs(c1.x-c2.x);
		height = Math.abs(c1.y-c2.y);
	}
	int getCornerX(){
		return c1.x;
	}
	int getCornerY(){
		return c1.y;
	}

	boolean contact(Hitbox b){
		return (Math.abs(this.centerx - b.centerx) * 2 < (this.width + b.width)) &&
				(Math.abs(this.centery - b.centery) * 2 < (this.height + b.height));

	}
	
	boolean move(int x, int y){//returns true if the hitbox moved
		if((c1.x+x)>=0&&(c1.y+y)>=0&&(c2.x+x)<Game.Visuals.WIDTH&&(c2.y+y)<=Game.Visuals.HEIGHT){
			c1.move(x,y);
			c2.move(x,y);
			centerx = Math.abs((c1.x+c2.x)/2);
			centery = Math.abs((c1.y+c2.y)/2);
			return true;
		}
		else {
			return false;
		}
	}
	
	boolean move(int x, int y,boolean identifier){//returns true if the hitbox moved
		if((c1.x+x)>=0&&(c1.y+y)>=0&&(c2.x+x)<Game.Visuals.WIDTH&&(c2.y+y)<=Game.Visuals.HEIGHT){
			c1.move(x,y);
			c2.move(x,y);
		}
		if(c1.x+x<0){
			c1.set(0,c1.y);
			c2.set(width, c2.y);
		}else if(c2.x+x>=Game.Visuals.WIDTH){
			c1.set(Game.Visuals.WIDTH-width-1, c1.y);
			c2.set(Game.Visuals.WIDTH-1, c2.y);
		}
		if(c1.y+y<0){
			c1.set(c1.x, 0);
			c2.set(c2.x, height);
		}else if(c2.y+y>Game.Visuals.HEIGHT){
			c1.set(c1.x, Game.Visuals.HEIGHT-height);
			c2.set(c2.x, Game.Visuals.HEIGHT);
		}
		centerx = Math.abs((c1.x+c2.x)/2);
		centery = Math.abs((c1.y+c2.y)/2);
		return true;
	}
}
