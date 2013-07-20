package name.xmj.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import name.xmj.d2.Matrix;
import name.xmj.d2.MoveDirection;
import name.xmj.d2.Point;
import name.xmj.util.PQHeap;
import name.xmj.util.PriorityAccessor;

public class AStar {

	Matrix<Character> map;
	Point start, end;

	Map<Point, Augmentation> lookup = new HashMap<Point, Augmentation>();

	public AStar(Matrix<Character> map, Point s, Point e) {
		this.map = map;
		start = s;
		end = e;
	}

	public List<Point> shortestPath() {
		Set<Point> closed  = new LinkedHashSet<Point>();
		PriorityAccessor<Point> accessor = getAccessor();
		PQHeap<Point> opened = new PQHeap<Point>(accessor);
		opened.add(start);
		lookup.put(start, new Augmentation(start));

		boolean isExist = false;
//		int count = 10;
		while(!opened.isEmpty()) {
			Point p = opened.remove();

//			if(count -- < 0) break;
			if(p.equals(end)) {
				isExist = true;
				break;
			}
			closed.add(p);
			Augmentation augP = lookup.get(p);

			for(Point n: getNeighbours(p)) {

				if(closed.contains(n)) {
					continue;
				}

				if(!lookup.containsKey(n)) {
					register(n, p);
				} else {
					Augmentation aug = lookup.get(n);
					aug.g = augP.g + 1;
				}

				if(!opened.contains(n)) {
					opened.add(n);
				} else {
					opened.update(n, accessor.getPriority(n));
				}
			}
		}
		List<Point> path;
		if(isExist) {
			int length = lookup.get(end).getWeight();
			System.out.println(String.format("shortest path %d steps", length));
			path = new ArrayList<Point>(length);
			path.add(end);

			Point p = end;
			Point pp;
			while((pp = lookup.get(p).previous) != null) {
				path.add(p);
				p = pp;
			}
		} else {
			path = Collections.emptyList();
		}
		return path;
	}

	void register(Point p, Point pre) {
		lookup.put(p, new Augmentation(p, pre));
	}

	List<Point> getNeighbours(Point p) {
		List<Point> list = new ArrayList<Point>(4);
		for(MoveDirection dir : MoveDirection.values()) {
			Point n = MoveDirection.move(p, dir);
			if(map.inRange(n) && map.get(n) != 'W') {
				list.add(n);
			}
		}
		return list;
	}

	PriorityAccessor<Point> getAccessor() {
		return new PriorityAccessor<Point>(){
			@Override
			public int getPriority(Point e) {
				return lookup.get(e).getWeight();
			}

			@Override
			public void setPriority(Point e, int p) {
				//nothing
			}};
	}

	class Augmentation {
		public Point self;
		public Point previous;
		public int g;

		Augmentation(Point me) {
			this.self = me;
			g = 0;
		}

		Augmentation(Point me, Point pre) {
			this(me);
			this.previous = pre;
			g = lookup.get(previous).g + 1;
		}

		public int getWeight() {
			return g + h();
		}

		int h() {
			return self.manhatonDistance(end);
//			return (int)Math.round(self.distance(end));
//			return 0;
		}
	}

	public Set<Point> getProcessed() {
		return lookup.keySet();
	}

}
