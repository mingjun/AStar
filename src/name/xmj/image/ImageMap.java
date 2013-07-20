package name.xmj.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import name.xmj.d2.Matrix;
import name.xmj.d2.Point;

public class ImageMap {

	public BufferedImage img;
	public final int sizeX, sizeY;
	public List<Integer> ranks;

	Point start, end;
	int startRGB, endRGB;

	List<Point> walls;
	int wallRGB;

	public ImageMap(String fileName) {
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sizeX = img.getWidth();
		sizeY = img.getHeight();

		final Map<Integer, Integer> rgbs = new HashMap<Integer, Integer>();
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				int n = img.getRGB(x,y);
				Integer count = rgbs.get(n);
				if(count == null) {
					count = 0;
				}
				rgbs.put(n, count+1);
			}
		}
		ranks = new ArrayList<Integer>(rgbs.keySet());
		Collections.sort(ranks, new Comparator<Integer>(){
			@Override
			public int compare(Integer a, Integer b) {
				return rgbs.get(b) - rgbs.get(a);
			}
		});
	}

	public Point findFirstPoint(int rgb) {
		Point p = null;
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				int n = img.getRGB(x,y);
				if(n == rgb) {
					p = new Point(x,y);
					break;
				}
			}
		}
		return p;
	}

	public List<Point> findPoints(int rgb) {
		List<Point> ps = new ArrayList<Point>();
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				int n = img.getRGB(x,y);
				if(n == rgb) {
					ps.add(new Point(x,y));
				}
			}
		}
		return ps;
	}

	public void setPoints(Point start, Point end, int s, int e, List<Point> walls, int wRGB) {
		this.start = start;
		this.end = end;
		this.startRGB = s;
		this.endRGB = e;
		this.walls = walls;
		this.wallRGB = wRGB;
	}

	public Matrix<Character> getTextMatrix() {
		Matrix<Character> m = new Matrix<Character>(sizeX, sizeY, ' ');
		for(Point w: walls) {
			m.set(w, 'W');
		}
		return m;
	}

	public BufferedImage generateOutput(int bgRGB) {
		BufferedImage out = new BufferedImage(sizeX, sizeY, img.getType());
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				out.setRGB(x, y, bgRGB);
			}
		}
		return out;
	}
	public static void drawPoints(BufferedImage out, Collection<Point> fill, int fillRGB) {
		for(Point p: fill) {
			out.setRGB(p.x, p.y, fillRGB);
		}
	}

	public void drawStartEnd(BufferedImage out) {
		drawCircle(start, 5, startRGB, out);
		drawCircle(end, 8, endRGB, out);
	}

	static int sq(int x) {
		return x*x;
	}

	static void drawCircle(Point o, int r, int rgb, BufferedImage img) {
		int startX = o.x - r;
		int endX = o.x + r;
		int startY = o.y - r;
		int endY = o.y + r;
		int sqR = sq(r);
		for(int x = startX; x < endX;x++) {
			for(int y = startY; y < endY; y++) {
				if(sq(x - o.x) + sq(y - o.y) <= sqR) {
					img.setRGB(x,y,rgb);
				}
			}
		}
	}
}
