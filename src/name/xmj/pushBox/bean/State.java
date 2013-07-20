package name.xmj.pushBox.bean;

import java.util.Collections;
import java.util.List;

import name.xmj.d2.Point;
import name.xmj.pushBox.ConnectedPointSet;
import name.xmj.pushBox.GeoCalculator;
import name.xmj.util.Lists;


public class State {
	//data
	public List<Point> boxes;
	public Point man;

	//not data
//	GameMap map;
	Point normalizeMan;

	public State(List<Point> b, Point m, GameMap map) {
		Collections.sort(b);
		boxes = b;
		man = m;
		normalizeMan = calcNormalizeMan(map, GeoCalculator.buildConnectedPointSet(map, m, b));
	}

	@Override
	public int hashCode() {
		return (boxes.toString() + normalizeMan.toString()).hashCode();
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof State) {
			State s = (State)o;
			return Lists.equals(this.boxes, s.boxes) && this.normalizeMan.equals(s.normalizeMan);
		} else {
			return false;
		}
	}

	static Point calcNormalizeMan(GameMap map, ConnectedPointSet set) {
		int sizeX = map.sizeX;
		int sizeY = map.sizeY;
		for(int x=0; x < sizeX; x++) {
			for(int y=0; y < sizeY; y++) {
				Point each = new Point(x,y);
				if(set.contains(each)) {
					return each;
				}
			}
		}
		// never comes here!!
		return null;
	}
}
