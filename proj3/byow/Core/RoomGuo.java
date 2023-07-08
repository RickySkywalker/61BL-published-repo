package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

class RoomGuo {
	public PointGuo bottomLeft;
	public PointGuo topLeft;
	public PointGuo bottomRight;

	public RoomGuo(int width, int height, PointGuo p){
		bottomLeft = p;
		bottomRight = new PointGuo(p.x + width - 1,p.y);
		topLeft = new PointGuo(p.x,p.y + height - 1);

	}

	public boolean isValid(TETile[][] world){
		if (bottomLeft.x -1 <0 || bottomLeft.y -1 <0) {
			return false;
		}
		return !isOverlapped(world);
		//if (bottomRight.x - bottomLeft.x >=1 && topLeft.y - bottomLeft.y >=1)
	}

	public boolean isOverlapped(TETile[][] world){
		for (int i = bottomLeft.x - 1 ; i < bottomRight.x+ 2 ; i++) {
			for (int j = bottomLeft.y - 1; j < topLeft.y + 2; j++) {
				if (world[i][j].equals(Tileset.FLOOR)) {
					return true;
				}
			}
		}
		return false;
	}

	public PointGuo getMiddle(){
		return new PointGuo((bottomRight.x+bottomLeft.x)/2,(topLeft.y+bottomRight.y)/2);
	}

	public int getXDisplacement(RoomGuo newRoom){
		return newRoom.getMiddle().x - this.getMiddle().x;
	}
	public int getYDisplacement(RoomGuo newRoom){
		return newRoom.getMiddle().y - this.getMiddle().y;
	}


	@Override
	public boolean equals(Object o){
		return bottomLeft.x == ((RoomGuo)o).bottomLeft.x && bottomRight.x == ((RoomGuo)o).bottomRight.x
				&& topLeft.x == ((RoomGuo)o).topLeft.x;
	}



}