
public class Coordinate {
	int x, y;
	Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	void move(int x, int y){
		this.x+=x;
		this.y+=y;
	}
	void set(int x, int y){
		this.x = x;
		this.y = y;
	}
}
