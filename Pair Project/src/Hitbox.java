
public class Hitbox {
	Coordinate c1, c2;//c1 is always left-most
	int centerx,centery,width,height;
	Hitbox(Coordinate c1, Coordinate c2){//should always pass c1 upperleft, c2 lowerright
		if(c1.x<=c2.x){
			this.c1 = c1;
			this.c2 = c2;
		}else{
			this.c1 = c2;
			this.c2 = c1;
		}
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
	
	void move(int x, int y){
		c1.move(x,y);
		c2.move(x,y);
		centerx = Math.abs((c1.x+c2.x)/2);
		centery = Math.abs((c1.y+c2.y)/2);
	}
}
