package name.xmj.pushBox.bean;

import java.util.ArrayList;
import java.util.List;

import name.xmj.d2.Matrix;
import name.xmj.d2.Point;


public class GameMap extends Matrix<Character> {

	static Point getSize(List<String> lines) {
		int y = lines.size();
		int x = y > 0 ? lines.get(0).length() : 0;
		return new Point(x, y);
	}
	static ArrayList<ArrayList<Character>> fillData(List<String> lines) {
		ArrayList<ArrayList<Character>> m = new ArrayList<ArrayList<Character>>(lines.size());
		for(String line: lines) {
			final int lineLen = line.length();
			ArrayList<Character> list = new ArrayList<Character>(lineLen);
			for(int i=0;i< lineLen;i++) {
				list.add(line.charAt(i) == 'W' ? 'W' : ' ');
			}
			m.add(list);
		}
		return m;
	}

	public GameMap(List<String> lines) {
		super(getSize(lines).x, getSize(lines).y);
		data = fillData(lines);
	}

	public GameMap(GameMap o) {
		super(o);
	}

	public void dump() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<sizeY;i++) {
			for(int j=0;j<sizeX;j++) {
				sb.append(get(j,i));
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}
