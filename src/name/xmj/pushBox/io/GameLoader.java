package name.xmj.pushBox.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import name.xmj.d2.Point;
import name.xmj.pushBox.bean.GameMap;


public class GameLoader {
private List<String> data;

	final public GameMap map;
	final public List<Point> boxes, targets;
	public Point man;

	public static GameLoader loadModel(String file) {
		return new GameLoader(file);
	}

	private GameLoader(String fileName) {
		data = load(fileName);
		map = new GameMap(data);

		boxes = new ArrayList<Point>();
		targets = new ArrayList<Point>();
		for(int y=0; y< map.sizeY ; y++) {
			String line = data.get(y);
			for(int x=0; x < map.sizeX ;x++) {
				switch(line.charAt(x)) {
				case 'O':
					man = new Point(x,y);
					break;
				case 'B':
					boxes.add(new Point(x,y));
					break;
				case 'T':
					targets.add(new Point(x,y));
				}
			}
		}
	}


	List<String> load(String fileName) {
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
}
