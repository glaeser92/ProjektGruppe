package mst;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Stack;

public class QuickHeap<Key> {

	private int capacity;
	private int idx;
	private Key[] heap;
	private Stack<Integer> S;
	private HashMap<Key, Integer> map;
	private int n;
	
	public QuickHeap() {
		this(1);
	}

	/**
	 * Initialises an QuickHeap with the max of N or A.length elements and
	 * copies the elements of A into the QuickHeap
	 * @param A
	 * @param N
	 */
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

	/**
	 * Initialises an empty QuickHeap with space for N elements
	 * @param N
	 */
	public QuickHeap(int N) {
		S = new Stack<Integer>();
		capacity = N + 1;
		heap = (Key[]) new Object[capacity];
		S.push(0);
		idx = 0;
		n = 0;
		map = new HashMap<Key, Integer>();
	}
	
	/**
	 * Replaces oldKey with newKey and restores the ordering of
	 * the elements
	 * @param oldKey
	 * @param newKey
	 */
	public void decreaseKey(Key oldKey, Key newKey){
		int pos = map.get(oldKey);
		
		int pidx = findChunk(pos);
		
		//if key is a pivot
		if(S.get(pidx) == pos){
			S.remove(pidx);
			pidx--;
		}
		//we are in the first chunk or the right chunk to insert
		//we update heap[pos] to newValue
		if((S.size() == pidx + 1) || smaller((heap[S.get(pidx+1) % capacity]), newKey)){
			heap[pos % capacity] = newKey;
			map.put(newKey, pos % capacity);
			return;
		}
		//we place the element at the right of the next pivot in the current
		//position and start the pivot movement procedure from that chunk
		else{
			heap[pos % capacity] = heap[(S.get(pidx+1)+1) % capacity];
			map.put(heap[(S.get(pidx+1)+1) % capacity], pos % capacity);
			add(newKey, pidx + 1);
		}
	}
	
	/**
	 * Adds x starting from pivot S[pidx]
	 * @param x
	 * @param pidx
	 */
	private void add(Key x, int pidx){
		while(true){
			heap[(S.get(pidx)+1) % capacity] = heap[S.get(pidx) % capacity];
			map.put(heap[S.get(pidx) % capacity], (S.get(pidx)+1) % capacity);
			S.set(pidx, S.get(pidx)+1);
			//we are in the first chunk or the right chunk to insert
			//we insert x to the left of pidx
			if((S.size() == pidx + 1) || smaller(heap[(S.get(pidx + 1)) % capacity], x)){
				heap[(S.get(pidx)-1) % capacity] = x;
				map.put(x, (S.get(pidx)-1) % capacity);
				return;
			}
			//we place the element at the right of the next position one
			//position to the left of the current pivot and continue this
			//procedure with the next chunk
			else{
				heap[(S.get(pidx)-1) % capacity] = heap[(S.get(pidx+1)+1) % capacity];
				map.put(heap[(S.get(pidx+1)+1) % capacity], (S.get(pidx)-1) % capacity);
				pidx++;
			}
		}
	}
	
	/**
	 * Inserts x
	 * @param x
	 */
	public void insert(Key x){
		add(x,0);
		n++;
	}
	
	/**
	 * Deletes the key at position pos
	 * @param pos
	 */
	public void delete(int pos){
		int pidx = findChunk(pos);
		//if pos is a pivot we extract it from S
		//and consider the next pivot
		if(S.get(pidx) == pos){
			S.remove(pidx);
			pidx--;
		}
		//move element to left of the pivot to pos,
		//move pivot one position to the left, update
		//pos and continue with the next pivot
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
	
	/**
	 * Deletes Key x
	 * @param x
	 */
	public void delete(Key x){
		if(!map.containsKey(x)){
			throw new NoSuchElementException("Key is not in the QuickHeap");
		}
		else{
			int pos = map.get(x);
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
	}

	/**
	 * Incrementally gives the next smallest element of the QuickHeap
	 * @param idx
	 * @param S
	 * @return
	 */
	private Key incrementalQuickSort(int idx, Stack<Integer> S) {
		int pidx;
		if (idx == S.peek()) {
			//S.pop();
			return heap[idx % capacity];
		}
		// pidx = rand.nextInt(S.peek() - idx) + idx;
		pidx = S.peek() - 1;
		int pidxNew = partition(pidx, idx, S.peek() - 1);
		S.push(pidxNew);
		return incrementalQuickSort(idx, S);
	}

	/**
	 * Helper routine for incrementalQuickSort. Partitions the QuickHeap from
	 * leftIndex to rightIndex with the pivot element pivot
	 * @param pivot
	 * @param leftIndex
	 * @param rightIndex
	 * @return
	 */
	private int partition(int pivot, int leftIndex, int rightIndex) {
		int pivotIndex = pivot;
		Key pivotValue = heap[pivotIndex % capacity];
		int storeIndex = leftIndex;
		swap(pivotIndex, rightIndex);
		for (int i = leftIndex; i < rightIndex; i++) {
			if (smaller(heap[i % capacity], pivotValue)) {
				swap(storeIndex, i);
				storeIndex++;
			}
		}
		swap(rightIndex, storeIndex);
		return storeIndex;
	}

	/**
	 * Finds the smalles Element
	 * @return
	 */
	public Key findMin() {
		incrementalQuickSort(idx, S);
		return heap[idx % capacity];
	}

	/**
	 * Extracts the smallest Element
	 * @return
	 */
	public Key extractMin() {
		incrementalQuickSort(idx, S);
		idx++;
		S.pop();
		n--;
		return heap[(idx - 1) % capacity];
	}

	/**
	 * Returns 1 if i <= j
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean smaller(Key i, Key j) {
		return ((Comparable<Key>) i).compareTo(j) <= 0;
	}

	/**
	 * Swaps elements at index1 and index2
	 * @param index1
	 * @param index2
	 */
	private void swap(int index1, int index2) {
		Key temp = heap[index1 % capacity];
		heap[index1 % capacity] = heap[index2 % capacity];
		map.put(heap[index2 % capacity], index1);
		heap[index2 % capacity] = temp;
		map.put(temp, index2);
	}

	/**
	 * Copies elements from src to dest
	 * @param dest
	 * @param src
	 */
	private void copy(Key[] dest, Key[] src) {
		for (int i = 0; i < src.length; i++) {
			dest[i] = src[i];
		}
	}

	/**
	 * Returns the bigger integer
	 * @param a
	 * @param b
	 * @return
	 */
	private int max(int a, int b) {
		if (a >= b) {
			return a;
		} else {
			return b;
		}
	}
	
	/**
	 * Finds the chunk of position pos
	 * @param pos
	 * @return
	 */
	private int findChunk(int pos){
		int pidx = 0;
		while(pidx < S.size() && S.get(pidx) >= pos)
			pidx++;
		return pidx - 1;
	}
	
	/**
	 * Returns true if QuickHeap is empty
	 * @return
	 */
	public boolean isEmpty() {
		return (n==0);
	}

	/**
	 * Returns true if QuickHeap contains x
	 * @param x
	 * @return
	 */
	public boolean contains(Key x) {
		return map.containsKey(x);
	}

}
