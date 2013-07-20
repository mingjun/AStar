package name.xmj.pushBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.xmj.d2.Matrix;
import name.xmj.d2.MoveDirection;
import name.xmj.d2.Point;
import name.xmj.pushBox.bean.Push;


public class GeoCalculator {

    /**
     *
     * find point next to on box(surrounding), and the man can get to(connected).
     * @param boxes
     * @param man
     * @return
     */
    public static List<Point> findPushPoints(Matrix<Character> map, List<Point> boxes, Point man) {
        Matrix<Character> m = new Matrix<Character>(map);
        //treat box as wall
        for(Point p: boxes) {
            m.set(p.x, p.y, 'W');
        };

        Set<Point> surrounding = new HashSet<Point>();
        // step 1: find point surrounding box
        for(Point box: boxes) {
            for(Point p: box.getSurrounding()) {
                if(m.inRange(p) && m.get(p.x, p.y) != 'W') {
                    surrounding.add(p);
                }
            }
        }
        // step 2: filter point that doesn't connect to man
        List<Point> pushes = filterNonConnected(surrounding, man, m);

        return pushes;
    }


    /**
     * filter on the input list, remove those point, which are not connected to man
     * @param input
     * @param man
     * @param newMap
     * @return
     */
    static List<Point> filterNonConnected(Collection<Point> input, Point man, Matrix<Character> newMap) {

    	ConnectedPointSet connections = new ConnectedPointSet(man, newMap);
        List<Point> out = new ArrayList<Point>();
        for(Point p : input) {
            if(connections.contains(p)) {
                out.add(p);
            }
        }
        return out;
    }

//    public boolean isConnected(Point a, Point b, List<Point> boxes) {
//        Matrix<Character> m = new Matrix<Character>(map);
//        //treat box as wall
//        for(Point p: boxes) {
//            m.set(p.x, p.y, 'W');
//        };
//        ConnectedPointSet connections = new ConnectedPointSet(a, m);
//        return connections.contains(b);
//    }

    public static List<Push> findAvaliablePushes(Matrix<Character> map, List<Point> boxes, Point man) {
        Matrix<Character> m = new Matrix<Character>(map);
        for(Point box: boxes) {
            m.set(box.x, box.y, 'B');
        }

        List<Push> pushes = new ArrayList<Push>();
        for(Point pp : findPushPoints(map, boxes, man)) {
            for(MoveDirection dir: MoveDirection.values()) {
                Point moved = MoveDirection.move(pp, dir);
                if(m.get(moved.x, moved.y) == 'B') {
                    Point boxMoved = MoveDirection.move(moved, dir);
                    if(m.get(boxMoved.x, boxMoved.y) == ' ') {
                        pushes.add(new Push(pp, dir));
                    }
                }
            }
        }
        return pushes;
    }

    public static ConnectedPointSet buildConnectedPointSet(Matrix<Character> map, Point man, List<Point> boxes) {
    	Matrix<Character> m = new Matrix<Character>(map);
        //treat box as wall
        for(Point p: boxes) {
            m.set(p.x, p.y, 'W');
        };
        ConnectedPointSet set = new ConnectedPointSet(man, m);
        return set;
    }

}