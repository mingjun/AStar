package name.xmj.util;

public interface PriorityAccessor<T> {
	int getPriority(T e);
	void setPriority(T e, int p);
}
