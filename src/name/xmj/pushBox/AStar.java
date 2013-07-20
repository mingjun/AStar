package name.xmj.pushBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import name.xmj.d2.MoveDirection;
import name.xmj.d2.Point;
import name.xmj.pushBox.bean.GameMap;
import name.xmj.pushBox.bean.Push;
import name.xmj.pushBox.bean.State;
import name.xmj.pushBox.io.GameLoader;
import name.xmj.util.Lists;
import name.xmj.util.PQHeap;
import name.xmj.util.PriorityAccessor;


public class AStar {

	GameMap map;
	List<Point> targets;
	State start; //a start node of a graph


	public AStar(GameLoader m) {
		map = m.map;
		targets = m.targets;
		Collections.sort(targets);
		start = new State(m.boxes, m.man, map);
	}

	public List<State> findPath() {
		Set<State> closed  = new LinkedHashSet<State>();
		PriorityAccessor<State> accessor = getAccessor();
		PQHeap<State> opened = new PQHeap<State>(accessor);
		opened.add(start);
		lookup.put(start, new Augmentation(start));


		State finalState = null;
		while(!opened.isEmpty()) {
			State p = opened.remove();
			if(isFinalState(p)) {
				finalState = p;
				break;
			}
			closed.add(p);

			Augmentation augP = lookup.get(p);

			for(State n: getNextStates(p)) {
				if(closed.contains(n)) {
					continue;
				}
				if(!lookup.containsKey(n)) {
					lookup.put(n, new Augmentation(n, p));
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
		List<State> path;
		if(null != finalState) {
			int length = lookup.get(finalState).getWeight();

			path = new ArrayList<State>(length);
			path.add(finalState);

			State p = finalState;
			State pp;
			while((pp = lookup.get(p).previous) != null) {
				path.add(p);
				p = pp;
			}
			Collections.reverse(path);
		} else {
			path = Collections.emptyList();
		}

		return path;
	}


	PriorityAccessor<State> getAccessor() {
		return new PriorityAccessor<State>(){
			@Override
			public int getPriority(State e) {
				return lookup.get(e).getWeight();
			}

			@Override
			public void setPriority(State e, int p) {
				//do nothing
			}};
	}

	boolean isFinalState(State s) {
		return Lists.equals(s.boxes, targets);
	}

	List<State> getNextStates(State s) {
		List<State> c = new ArrayList<State>();
		//use Push to get next state
		for(Push push: GeoCalculator.findAvaliablePushes(map, s.boxes,s.man)) {
			MoveDirection dir = push.pushDir;
			Point box = MoveDirection.move(push.pushPoint, dir);
			Point newBox = MoveDirection.move(box, dir);

			List<Point> boxes = new ArrayList<Point>();
			for(Point b: s.boxes) {
				if(b.equals(box)) {
					b = newBox;
				}
				boxes.add(b);
			}
			c.add(new State(boxes, box, map));
		}
		return c;
	}

	//!!!
	int distanceToFinal(State s) {
		List<Point> boxes = s.boxes;
		int len = boxes.size();
		//boxes and targets is sorted;
		int distance = 0;
		for(int i=0;i<len;i++) {
			distance += boxes.get(i).manhatonDistance(targets.get(i));
		}
		return distance;
	}

	class Augmentation {
		public State self;
		public State previous;
		public int g;

		Augmentation(State me) {
			this.self = me;
			g = 0;
		}

		Augmentation(State me, State pre) {
			this(me);
			this.previous = pre;
			g = lookup.get(previous).g + 1;
		}

		public int getWeight() {
			return g + h();
		}

		int h() {
			return distanceToFinal(self);
//			return 0;
		}
	}

	Map<State, Augmentation> lookup = new HashMap<State, Augmentation>();

	public int getTryCount() {
		return lookup.size();
	}

	public void dump(State s) {
		GameMap m = new GameMap(map);
		for(Point b: s.boxes) {
			m.set(b, 'B');
		}
		m.set(s.man, 'O');
		m.dump();

	}

}
