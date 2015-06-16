package mst;

import java.util.HashMap;
import java.util.Stack;

public class QuickHeap<Key> {
	
	// use sedgewicks idea to implement generic priority data types

	private int capacity;
	private int idx;
	private Key[] heap;
	private Stack<Integer> S;
	private HashMap<Key, Integer> map;
	private int n;
	
	public QuickHeap() {
		this(1);
	}

	public QuickHeap(Key[] A, int N) {
		S = new Stack<Integer>();
		capacity = max(N, A.length) + 1;
		heap = (Key[]) new Object[capacity];
		n = A.length;
		S.push(A.length);
		idx = 0;
		copy(heap, A);
		map = new HashMap<Key, Integer>();
		for (int i = 0; i < heap.length; i++) {
			map.put(heap[i], i);
		}
	}

	public QuickHeap(int N) {
		S = new Stack<Integer>();
		capacity = N + 1;
		heap = (Key[]) new Object[capacity];
		S.push(0);
		idx = 0;
		n = 0;
		map = new HashMap<Key, Integer>();
	}
	
	
	public void decreaseKey(Key oldKey, Key newKey){
		int pos = map.get(oldKey);
		
		int pidx = findChunk(pos);
		
		//if key is a pivot
		if(S.get(pidx) == pos){
			S.remove(pidx);
			pidx--;
		}
		//we are in the first chunk or the right chunk to insert
		if((S.size() == pidx + 1) || smaller((heap[S.get(pidx+1) % capacity]), newKey)){
			heap[pos % capacity] = newKey;
			map.put(newKey, pos % capacity);
			return;
		}
		else{
			heap[pos % capacity] = heap[(S.get(pidx+1)+1) % capacity];
			map.put(heap[(S.get(pidx+1)+1) % capacity], pos % capacity);
			add(newKey, pidx + 1);
		}
	}
	
	private void add(Key x, int pidx){
		while(true){
			heap[(S.get(pidx)+1) % capacity] = heap[S.get(pidx) % capacity];
			map.put(heap[S.get(pidx) % capacity], (S.get(pidx)+1) % capacity);
			S.set(pidx, S.get(pidx)+1);
			if((S.size() == pidx + 1) || smaller(heap[(S.get(pidx + 1)) % capacity], x)){
				heap[(S.get(pidx)-1) % capacity] = x;
				map.put(x, (S.get(pidx)-1) % capacity);
				return;
			}
			else{
				heap[(S.get(pidx)-1) % capacity] = heap[(S.get(pidx+1)+1) % capacity];
				map.put(heap[(S.get(pidx+1)+1) % capacity], (S.get(pidx)-1) % capacity);
				pidx++;
			}
		}
	}
	
	public void insert(Key x){
		add(x,0);
		n++;
	}
	
	public void delete(int pos){
		int pidx = findChunk(pos);
		if(S.get(pidx) == pos){
			S.remove(pidx);
			pidx--;
		}
		for(int i = pidx; i >= 0; i--){
			heap[pos % capacity] = heap[(S.get(i)-1) % capacity];
			map.put(heap[(S.get(i)-1) % capacity], pos % capacity);
			heap[(S.get(i)-1) % capacity] = heap[S.get(i) % capacity];
			map.put(heap[S.get(i) % capacity], (S.get(i)-1) % capacity);
			S.set(i, S.get(i)-1);
			pos = S.get(i)+1;
		}
		n--;
	}

	private Key incrementalQuickSort(int idx, Stack<Integer> S) {
		int pidx;
		if (idx == S.peek()) {
			//S.pop();
			return heap[idx];
		}
		// pidx = rand.nextInt(S.peek() - idx) + idx;
		pidx = S.peek() - 1;
		int pidxNew = partition(pidx, idx, S.peek() - 1);
		S.push(pidxNew);
		return incrementalQuickSort(idx, S);
	}

	private int partition(int pivot, int leftIndex, int rightIndex) {
		int pivotIndex = pivot;
		Key pivotValue = heap[pivotIndex];
		int storeIndex = leftIndex;
		swap(pivotIndex, rightIndex);
		for (int i = leftIndex; i < rightIndex; i++) {
			if (smaller(heap[i], pivotValue)) {
				swap(storeIndex, i);
				storeIndex++;
			}
		}
		swap(rightIndex, storeIndex);
		return storeIndex;
	}

	public Key findMin() {
		incrementalQuickSort(idx, S);
		return heap[idx % capacity];
	}

	public Key extractMin() {
		incrementalQuickSort(idx, S);
		idx++;
		S.pop();
		n--;
		return heap[(idx - 1) % capacity];
	}

	private boolean smaller(Key i, Key j) {
		return ((Comparable<Key>) i).compareTo(j) <= 0;
	}

	private void swap(int index1, int index2) {
		Key temp = heap[index1];
		heap[index1] = heap[index2];
		map.put(heap[index2], index1);
		heap[index2] = temp;
		map.put(temp, index2);
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
		return (n==0);
	}

	public boolean contains(Key x) {
		return map.containsKey(x);
	}
	
	// help functions for debugging:
	
	public void printStack(){
		System.out.println(S.toString());
	}

}
