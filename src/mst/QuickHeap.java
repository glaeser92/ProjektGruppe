package mst;

import java.util.HashMap;
import java.util.Stack;

public class QuickHeap<Key> {

	private int capacity;
	private int idx;
	private Key[] heap;
	private Stack<Integer> S;
	private HashMap<Integer, Key> map;
	
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
		map = new HashMap<Integer, Key>();
	}

	public QuickHeap(int N) {
		S = new Stack<Integer>();
		capacity = N + 1;
		heap = (Key[]) new Object[capacity];
		S.push(0);
		idx = 0;
		map = new HashMap<Integer, Key>();
	}
	
	
	public void decreaseKey(int pos, Key newKey){
		//testversion without hashmap to find element
		
		int pidx = findChunk(pos);
		
		//if key is a pivot
		if(S.get(pidx) == pos){
			S.remove(pidx);
			pidx--;
		}
		//we are in the first chunk or the right chunk to insert
		if((S.size() == pidx + 1) || smaller((heap[S.get(pidx+1) % capacity]), newKey)){
			heap[pos % capacity] = newKey;
			return;
		}
		else{
			heap[pos % capacity] = heap[(S.get(pidx+1)+1) % capacity];
			add(newKey, pidx + 1);
		}
	}
	
	private void add(Key x, int pidx){
		while(true){
			heap[(S.get(pidx)+1) % capacity] = heap[S.get(pidx) % capacity];
			S.set(pidx, S.get(pidx)+1);
			if((S.size() == pidx + 1) || smaller(heap[(S.get(pidx)-1) % capacity], x)){
				heap[(S.get(pidx)-1) % capacity] = x;
				return;
			}
			else{
				heap[(S.get(pidx)-1) % capacity] = heap[(S.get(pidx+1)+1) % capacity];
				pidx++;
			}
		}
	}
	
	public void insert(Key x){
		add(x,0);		
	}
	
	public void delete(int pos){
		int pidx = findChunk(pos);
		if(S.get(pidx) == pos){
			S.remove(pidx);
			pidx--;
		}
		for(int i = pidx; i >= 0; i--){
			heap[pos % capacity] = heap[(S.get(i)-1) % capacity];
			heap[(S.get(i)-1) % capacity] = heap[S.get(i) % capacity];
			S.set(i, S.get(i)-1);
			pos = S.get(i)+1;
		}
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
	
	private int findChunk(int pos){
		int pidx = 0;
		while(pidx < S.size() && S.get(pidx) >= pos)
			pidx++;
		return pidx - 1;
	}
	
	//TODO: check if method is correct
	public boolean isEmpty() {
		return (S.size() == 0);
	}

	public boolean contains(Key x) {
		//testversion without hashmap
		
		for(int i = 0; i < heap.length; i++){
			if(heap[i].equals(x))
				return true;
		}
		return false;
	}

}
