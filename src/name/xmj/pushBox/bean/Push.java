package name.xmj.pushBox.bean;

import name.xmj.d2.MoveDirection;
import name.xmj.d2.Point;


public class Push {
	final public Point pushPoint;
	final public MoveDirection pushDir;
	public Push(Point pushPoint, MoveDirection pushDir) {
		this.pushPoint = pushPoint;
		this.pushDir = pushDir;
	}
}
