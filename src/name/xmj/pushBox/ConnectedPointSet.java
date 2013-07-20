package name.xmj.pushBox;

import java.util.ArrayList;
import java.util.Collection;

import name.xmj.d2.Matrix;
import name.xmj.d2.Point;

public class ConnectedPointSet {

	Matrix<Integer> w;
	ConnectedPointSet(Point start, Matrix<Character> map) {
		w = buildConnectedWeights(start, map);
	}

	/**
     * build a matrix for weights.
     * the number(weight) of each point, represents the distance between the point and man
     * when distance == Integer.MAX_VALUE, it means, point and man is not connected
     * @param man
     * @param newMap: only 'W' and ' ' in it
     * @return
     */
    Matrix<Integer> buildConnectedWeights(Point man, Matrix<Character> newMap) {
        Matrix<Integer> t = new Matrix<Integer>(newMap.sizeX, newMap.sizeY, Integer.MAX_VALUE);

        // layer 0
        int connectWeight = 0;
        t.set(man.x, man.y, connectWeight);
        Collection<Point> layer = new ArrayList<Point>();
        layer.add(man);

        //complexity is 4*x*y (linear)
        while(!layer.isEmpty()) {
            int surroundWeight = connectWeight + 1;
            Collection<Point> newLayer = new ArrayList<Point>();
            // start has weight == connectWeight
            for(Point start: layer) {
                // s surround start
                for(Point s: start.getSurrounding()) {
                    if(t.inRange(s) && newMap.get(s.x, s.y) != 'W' ) {
                        if(t.get(s.x, s.y) > surroundWeight) {
                            t.set(s.x, s.y, surroundWeight);
                            newLayer.add(s);
                        }
                    }
                }
            }
            connectWeight = surroundWeight;
            layer = newLayer;
        }
        return t;
    }

    public boolean contains(Point p) {
        return w.get(p.x, p.y) != Integer.MAX_VALUE;
    }
}
