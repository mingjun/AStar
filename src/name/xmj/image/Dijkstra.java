package name.xmj.image;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import name.xmj.d2.Matrix;
import name.xmj.d2.MoveDirection;
import name.xmj.d2.Point;
import name.xmj.util.PQHeap;
import name.xmj.util.PriorityAccessor;

public class Dijkstra {

	Matrix<Character> map;
	Point start, end;

	Matrix<Character> points;
	public Dijkstra(Matrix<Character> map) {
		this.map = map;

		for(int x=0; x<map.sizeX; x++) {
			for(int y=0;y<map.sizeY; y++) {
				if(map.get(x, y) == 'S'){
					start = new Point(x,y);
					break;
				}
			}
		}
		for(int x=0; x<map.sizeX; x++) {
			for(int y=0;y<map.sizeY; y++) {
				if(map.get(x, y) == 'E'){
					end = new Point(x,y);
					break;
				}
			}
		}
	}

	public Dijkstra(Matrix<Character> map, Point s, Point e) {
		this.map = map;
		start = s;
		end = e;
	}

	public Set<Point> shortestPath() {
		Matrix<Integer> weights = new Matrix<Integer>(map.sizeX, map.sizeY, Integer.MAX_VALUE);
		weights.set(start, 0);

		Set<Point> processed  = new LinkedHashSet<Point>();
		PriorityAccessor<Point> accessor = getAccessor(weights);
		PQHeap<Point> q = new PQHeap<Point>(accessor);
		q.add(start);

		while(!q.isEmpty()) {
			Point p = q.remove();
//			System.out.println(p + " " + weights.get(p));
			if(p.equals(end)) {
				System.out.println("shortest path "+ weights.get(p) + " steps");
				break;
			}
			for(Point next : getNext(p)) {
				if(!processed.contains(next)) {
					if(!q.contains(next)) {
						q.add(next);
					}
					int index = q.indexOf(next);
					int oldP = accessor.getPriority(next);
					int newP = accessor.getPriority(p) + 1;
					if(q.comparePriority(oldP, newP) > 0) {
						q.update(index, newP);
					}
				}
			}
			processed.add(p);
		}

		return processed;
	}



	List<Point> getNext(Point p) {
		List<Point> list = new ArrayList<Point>(4);
		for(MoveDirection dir : MoveDirection.values()) {
			Point n = MoveDirection.move(p, dir);
			if(map.inRange(n) && map.get(n) != 'W') {
				list.add(n);
			}
		}
		return list;
	}



	PriorityAccessor<Point> getAccessor(final Matrix<Integer> weights) {
		return new PriorityAccessor<Point>(){
			@Override
			public int getPriority(Point e) {
				return weights.get(e);
			}

			@Override
			public void setPriority(Point e, int p) {
				weights.set(e, p);
			}};
	}



}
