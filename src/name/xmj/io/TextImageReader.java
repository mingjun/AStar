package name.xmj.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import name.xmj.d2.Matrix;
import name.xmj.d2.Point;

public class TextImageReader {

	static List<String> readFile(String fileName) {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String line = null;
			while(null != (line = br.readLine())) {
				lines.add(line);
			}
			br.close();
		} catch(IOException e) {
			//nothing
		}
		return lines;
	}

	static Point getSize(List<String> lines) {
		int y = lines.size();
		int x = y > 0 ? lines.get(0).length() : 0;
		return new Point(x, y);
	}

	public static Matrix<Character> readImage(String fileName) {
		List<String> lines = readFile(fileName);
		Point size = getSize(lines);
		return new ImageMatrix(size, lines);
	}

	static class ImageMatrix extends Matrix<Character> {
		protected ImageMatrix(Point size, List<String> lines) {
			super(size.x, size.y);
			data = fillData(lines);
		}
		static ArrayList<ArrayList<Character>> fillData(List<String> lines) {
			ArrayList<ArrayList<Character>> m = new ArrayList<ArrayList<Character>>(lines.size());
			for(String line: lines) {
				final int lineLen = line.length();
				ArrayList<Character> list = new ArrayList<Character>(lineLen);
				for(int i=0;i< lineLen;i++) {
					list.add(line.charAt(i));
				}
				m.add(list);
			}
			return m;
		}

	}
}
