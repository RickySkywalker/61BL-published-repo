package byow.Core;

class PointGuo {
	public static final int MAXHEIGHTINDEX = Engine.HEIGHT - 2;
	public static final int MINHEGHTINDEX = 1;
	public static final int MAXWIDTHINDEX = Engine.WIDTH - 2;
	public static final int MINWIDTHINDEX = 1;
	public int x;
	public int y;

	PointGuo(int x, int y){

		if (x<=MAXWIDTHINDEX && x>=MINWIDTHINDEX) {
			this.x = x;
		}else{
			if (x<MINWIDTHINDEX) {
				this.x = MINWIDTHINDEX;
			}else{
				this.x =MAXWIDTHINDEX;
			}
		}
		if (y<= MAXHEIGHTINDEX && y>= MINHEGHTINDEX) {
			this.y = y;
		}
		else{
			if (x<MINHEGHTINDEX) {
				this.x = MINHEGHTINDEX;
			}else{
				this.x =MAXHEIGHTINDEX;
			}
		}


	}
}