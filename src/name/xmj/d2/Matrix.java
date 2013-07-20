package name.xmj.d2;

import java.util.ArrayList;

public class Matrix<E> {
	final public int sizeX, sizeY;
	protected ArrayList<ArrayList<E>> data;

	protected Matrix(int sizex, int sizey) {
		sizeX = sizex;
		sizeY = sizey;
	}

	public Matrix(int sizex, int sizey, E defaultValue) {
		this(sizex, sizey);
		data = new ArrayList<ArrayList<E>>(sizeY);
		for(int y=0; y < sizeY; y++) {
			ArrayList<E> line = new ArrayList<E>();
			for(int x =0; x < sizeX; x++) {
				line.add(defaultValue);
			}
			data.add(line);
		}
	}

	public Matrix(Matrix<E> other) {
		this(other.sizeX, other.sizeY);
		data = new ArrayList<ArrayList<E>>(sizeY);
		for(int y=0; y < sizeY; y++) {
			ArrayList<E> line = new ArrayList<E>();
			for(int x =0; x < sizeX; x++) {
				line.add(other.get(x, y));
			}
			data.add(line);
		}
	}

	public E get(int x, int y) {
		return data.get(y).get(x);
	}

	public E get(Point p) {
		return get(p.x, p.y);
	}

	public void set(int x, int y, E v) {
		data.get(y).set(x, v);
	}

	public void set(Point p, E v) {
		set(p.x, p.y, v);
	}

	public boolean inRange(Point p) {
		return p.x >=0 && p.x < sizeX && p.y >=0 && p.y < sizeY;
	}
}
