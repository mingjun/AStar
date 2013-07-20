package name.xmj.d2;


public enum MoveDirection {
	UP(0, -1), RIGHT(1,0), DOWN(0,1), LEFT(-1,0);

	int x, y;
	MoveDirection(int xx, int yy) {
		x = xx;
		y = yy;
	}
	public static Point move(Point p, MoveDirection dir) {
		return new Point(p.x + dir.x, p.y + dir.y);
	}
}
