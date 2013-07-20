package name.xmj;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import name.xmj.d2.Matrix;
import name.xmj.d2.Point;
import name.xmj.image.AStar;
import name.xmj.image.Dijkstra;
import name.xmj.image.ImageMap;
import name.xmj.io.TextImageReader;

import org.junit.Test;

public class TestImageShortestPath {


	@Test
	public void testMatrix() {
		Matrix<Character> m = TextImageReader.readImage("images/map1.txt");
		dump(m);
		Dijkstra djk = new Dijkstra(m);
		djk.shortestPath();
	}

	public static void dump(Matrix<Character> t) {
		StringBuilder sb = new StringBuilder();
		for(int y=0;y<t.sizeY;y++) {
			for(int x=0;x<t.sizeX;x++) {
				sb.append(t.get(x, y));
			}
			sb.append('\n');
		}
		System.out.print(sb.toString());
	}

	@Test
	public void testDjk() throws IOException {

		ImageMap im = new ImageMap("images/test.png");
		List<Integer> ranks = im.ranks;

		int bg = ranks.get(0);
		int wall = ranks.get(1);
		int e = ranks.get(2);
		int s = ranks.get(5);
		Point start = im.findFirstPoint(s), end = im.findFirstPoint(e);
		List<Point> walls = im.findPoints(wall);
		im.setPoints(start, end, s, e, walls, wall);

		Matrix<Character> map = im.getTextMatrix();
		Dijkstra djk = new Dijkstra(map, start, end);
		Set<Point> processed = djk.shortestPath();
		System.out.println("processed "+ processed.size() + " points");


		BufferedImage out = im.generateOutput(bg);
		ImageMap.drawPoints(out, walls, wall);
		ImageMap.drawPoints(out, processed, Color.GRAY.getRGB());
		im.drawStartEnd(out);
		ImageIO.write(out, "PNG", new File("images/djk.png"));
	}


	@Test
	public void testAStar() throws IOException {

		ImageMap im = new ImageMap("images/test.png");
		List<Integer> ranks = im.ranks;

		int bg = ranks.get(0);
		int wall = ranks.get(1);
		int e = ranks.get(2);
		int s = ranks.get(5);
		Point start = im.findFirstPoint(s), end = im.findFirstPoint(e);
		List<Point> walls = im.findPoints(wall);
		im.setPoints(start, end, s, e, walls, wall);

		Matrix<Character> map = im.getTextMatrix();
		AStar a = new AStar(map, start, end);
		List<Point> path = a.shortestPath();



		BufferedImage out = im.generateOutput(bg);

		System.out.println("processed "+ a.getProcessed().size() + " points");
		ImageMap.drawPoints(out, a.getProcessed(), Color.GRAY.getRGB());
		ImageMap.drawPoints(out, walls, wall);
		ImageMap.drawPoints(out, path, Color.RED.getRGB());
		im.drawStartEnd(out);
		ImageIO.write(out, "PNG", new File("images/a-star.png"));
	}




}
