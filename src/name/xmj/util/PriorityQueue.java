package name.xmj.util;

public abstract class PriorityQueue<T> {
	public final PriorityAccessor<T> accessor;
	public final boolean bigFirst;

	public PriorityQueue(PriorityAccessor<T> accessor, boolean bigFirst) {
		this.accessor = accessor;
		this.bigFirst = bigFirst;
	}

	public int comparePriority(int p1, int p2) {
		int c = NumberUtil.compare(p1, p2);
		return bigFirst ? -c : c;
	}

	public int comparePriority(T e1, T e2) {
		return comparePriority(accessor.getPriority(e1), accessor.getPriority(e2));
	}


	public abstract void add(T e);
	public abstract void update(T e, int priority);
	public abstract T remove();
	public abstract boolean isEmpty();
}
