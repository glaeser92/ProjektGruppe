package mst;

import java.util.Stack;

public class QuickHeap<Key> {

	private int capacity;
	private int idx;
	private Key[] heap;
	private Stack<Integer> S;
	
	public QuickHeap() {
		this(1);
	}

	public QuickHeap(Key[] A, int N) {
		S = new Stack<Integer>();
		capacity = max(N, A.length) + 1;
		heap = (Key[]) new Object[capacity];
		S.push(A.length);
		idx = 0;
		copy(heap, A);
	}

	public QuickHeap(int N) {
		S = new Stack<Integer>();
		capacity = N + 1;
		heap = (Key[]) new Object[capacity];
		S.push(0);
		idx = 0;
	}
	
	public void insert(Key x){
		int pidx = 0;
		while(true){
			//shift fictivious pivot one position to the left
			heap[(S.get(pidx) + 1) % capacity] = heap[S.get(pidx) % capacity];
			S.set(pidx, (S.get(pidx)+1));
			//if we are in the first chunk
			if((S.size() == pidx + 1) || smaller((heap[S.get(pidx + 1) % capacity]), x)){
				heap[(S.get(pidx) - 1) % capacity] = x;
				return;
			}
			else{
				heap[(S.get(pidx) - 1) % capacity] = heap[(S.get(pidx + 1) + 1) & capacity];
				pidx++;
			}
		}
		
	}
	
	public void decreaseKey(Key oldKey, Key newKey){
		
	}

	private Key incrementalQuickSort(Key[] list, int idx, Stack<Integer> S) {
		int pidx;
		if (idx == S.peek()) {
			S.pop();
			return list[idx];
		}
		// pidx = rand.nextInt(S.peek() - idx) + idx;
		pidx = S.peek() - 1;
		int pidxNew = partition(list, pidx, idx, S.peek() - 1);
		S.push(pidxNew);
		return incrementalQuickSort(list, idx, S);
	}

	private int partition(Key[] list, int pivot, int leftIndex, int rightIndex) {
		int pivotIndex = pivot;
		Key pivotValue = list[pivotIndex];
		int storeIndex = leftIndex;
		swap(list, pivotIndex, rightIndex);
		for (int i = leftIndex; i < rightIndex; i++) {
			if (smaller(list[i], pivotValue)) {
				swap(list, storeIndex, i);
				storeIndex++;
			}
		}
		swap(list, rightIndex, storeIndex);
		return storeIndex;
	}

	public Key findMin() {
		incrementalQuickSort(heap, idx, S);
		return heap[idx % capacity];
	}

	public Key extractMin() {
		incrementalQuickSort(heap, idx, S);
		idx++;
		S.pop();
		return heap[(idx - 1) % capacity];
	}

	private boolean smaller(Key i, Key j) {
		return ((Comparable<Key>) i).compareTo(j) < 0;
	}

	private void swap(Key[] list, int index1, int index2) {
		Key temp = list[index1];
		list[index1] = list[index2];
		list[index2] = temp;
	}

	private void copy(Key[] dest, Key[] src) {
		for (int i = 0; i < src.length; i++) {
			dest[i] = src[i];
		}
	}

	private int max(int a, int b) {
		if (a >= b) {
			return a;
		} else {
			return b;
		}
	}
	
	//TODO: check if method is correct
	public boolean isEmpty() {
		return (S.size() == 0);
	}

	public boolean contains(Key x) {
		// TODO Auto-generated method stub
		return false;
	}

}
