package name.xmj.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PQHeap<T> extends PriorityQueue<T>{

	List<T> heap;
	Map<T, Integer> indexLookup;
	public PQHeap(PriorityAccessor<T> accessor) {
		super(accessor, false);
		heap =  new ArrayList<T>();
		indexLookup = new HashMap<T, Integer>();
	}
	public PQHeap(PriorityAccessor<T> accessor, Collection<? extends T> input) {
		super(accessor, false);
		List<T> list = new ArrayList<T>(input);
		heap = list;
		buildHeap();
	}

	@Override
	public void add(T e) {
		heap.add(e);
		int index = heap.size() - 1;
		indexLookup.put(e, index);
		heapifyUp(index);
	}

	//maybe it should be defined in super class
	public void update(int index, int priority) {
		T e = heap.get(index);
		int currentP = accessor.getPriority(e);
		accessor.setPriority(e, priority);
		if(comparePriority(currentP, priority) > 0) {//decreased; go up
			heapifyUp(index);
		} else { //increased; go down
			heapifyDown(index);
		}
	}

	@Override
	public void update(T e, int priority) {
		int index = indexOf(e);
		int currentP = accessor.getPriority(e);
		accessor.setPriority(e, priority);
		if(comparePriority(currentP, priority) > 0) {//decreased; go up
			heapifyUp(index);
		} else { //increased; go down
			heapifyDown(index);
		}
	}

	@Override
	public T remove() {
		T root = heap.get(0);
		indexLookup.remove(root);
		T last = heap.remove(heap.size() - 1);
		if(!heap.isEmpty()) {
			heap.set(0, last);
			indexLookup.put(last, 0);
			heapifyDown(0);
		}
		return root;
	}

	@Override
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	public int size() {
		return heap.size();
	}

	public int indexOf(T e) {
		return indexLookup.get(e);
	}

	public boolean contains(T e) {
		return indexLookup.containsKey(e);
	}

	// complexity O(n)
	void buildHeap() {
		for(int i = heap.size()/2 - 1; i >= 0; i--) {
			heapifyDown(i);
		}
	}

	// assume all other heap is OK, but rootIndex
	void heapifyDown(int rootIndex) {
		final int maxIndex = heap.size() - 1;
		int leftIndex = leftChildIndex(rootIndex);
		int rightIndex = rightChildIndex(rootIndex);
		int indexOfLarger;
		if(leftIndex <= maxIndex && comparePriority(heap.get(rootIndex), heap.get(leftIndex)) > 0) {
			indexOfLarger = leftIndex;
		} else {
			indexOfLarger = rootIndex;
		}
		if(rightIndex <= maxIndex && comparePriority(heap.get(indexOfLarger), heap.get(rightIndex)) > 0) {
			indexOfLarger = rightIndex;
		}
		if(indexOfLarger != rootIndex) {
			swap(indexOfLarger, rootIndex);
			heapifyDown(indexOfLarger);
		}
	}

	// assume all other heap is OK, but rootIndex
	void heapifyUp(int index) {
		int i = index, p;
		while((p = parentIndex(i)) >=0 && comparePriority(heap.get(p), heap.get(i)) > 0) {
			swap(i, p);
			i = p;
		}
	}

	void swap(int i, int j) {
		Collections.swap(heap, i, j);
		indexLookup.put(heap.get(i), i);
		indexLookup.put(heap.get(j), j);
	}

	int parentIndex(int index) {
		return (index-1)>>1;
	}
	int leftChildIndex(int index){
		return (index<<1) + 1;
	}
	int rightChildIndex(int index){
		return (index<<1) + 2;
	}

//	void dump() {
//		System.out.println(heap);
//	}

}
