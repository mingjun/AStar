package name.xmj.util;

import java.util.List;

public class Lists {
	public static <T> boolean equals(List<T> a, List<T> b) {
		int len = a.size();
		if(b.size() != len) {
			return false;
		}
		for(int i=0;i<len;i++) {
			if(!a.get(i).equals(b.get(i))) {
				return false;
			}
		}
		return true;
	}
}
