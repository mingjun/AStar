package name.xmj.d2;

public class Point implements Comparable<Point>{
	public int x, y;
	public Point(int a, int b) {
		x = a;
		y = b;
	}
	public Point(Point other) {
		x = other.x;
		y = other.y;
	}

	public Point[] getSurrounding() {
		Point a = new Point(this);
		a.x ++;
		Point b = new Point(this);
		b.x --;
		Point c = new Point(this);
		c.y ++;
		Point d = new Point(this);
		d.y --;
		return new Point[]{a,b,c,d};
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}

	@Override
	public int hashCode() {
		//assume here x y is very small: [0~65536]
		return (x << 16) | y;
// general hash
//		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Point) {
			Point p = (Point)o;
			return this.x == p.x && this.y == p.y;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Point o) {
		int diff = this.hashCode() - o.hashCode();

		if(diff > 0) {
			return 1;
		} else if(diff < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	public int manhatonDistance(Point p) {
		return Math.abs(x - p.x) + Math.abs(y - p.y);
	}

	public double distance(Point p) {
		int dx = x - p.x;
		int dy = y - p.y;
		return Math.sqrt(dx*dx + dy*dy);
	}

}
