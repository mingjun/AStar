package name.xmj;

import java.util.List;

import name.xmj.pushBox.AStar;
import name.xmj.pushBox.io.GameLoader;
import name.xmj.pushBox.bean.*;

import org.junit.Test;

public class TestPushBox {

	@Test
	public void test() {
		GameLoader model = GameLoader.loadModel("images/pushBox/real15.txt");
		AStar a = new AStar(model);
		List<State> path = a.findPath();
		System.out.println(String.format("find a path %d steps, with %d trys", path.size(), a.getTryCount()));
//		for(State step : path) a.dump(step);
	}
}
