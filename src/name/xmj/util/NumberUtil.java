package name.xmj.util;

public class NumberUtil {

	static int compare(int x, int y) {
		if(x > y) {
			return 1;
		} else if (x < y) {
			return -1;
		} else {
			return 0;
		}
	}
}
