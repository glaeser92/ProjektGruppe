package mst;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

public class QuickHeap<Key extends hasID> {

	private int capacity;
	private int realCapacity;
	private int idx;
	private Key[] heap;
	private int[] posH;
	private Stack<Integer> S;
	private int n;

	/**
	 * Initializes an QuickHeap with the max of N or A.length elements and
	 * copies the elements of A into the QuickHeap
	 * 
	 * @param A
	 * @param N
	 */
	public QuickHeap(Key[] A, int N) {
		S = new Stack<>();
		capacity = max(N, A.length) + 1;
		realCapacity = capacity - 1;
		heap = (Key[]) new hasID[capacity];
		posH = new int[realCapacity];
		n = A.length - 1;
		S.push(A.length);
		idx = 0;
		copy(heap, A);

		for (int i = 0; i < n; i++) {
			posH[i] = heap[i].getID();
		}
	}

	/**
	 * Initializes an empty QuickHeap with space for N elements
	 * 
	 * @param N
	 */
	public QuickHeap(int N) {
		S = new Stack<>();
		capacity = N + 1;
		realCapacity = N;
		heap = (Key[]) new hasID[capacity];
		S.push(0);
		idx = 0;
		n = 0;
		posH = new int[realCapacity];

		for (int i = 0; i < posH.length; i++) {
			posH[i] = -1;
		}
	}

	/**
	 * Replaces oldKey with newKey and restores the ordering of the elements
	 * 
	 * @param oldKey
	 * @param newKey
	 */
	public void decreaseKey(Key newItem, int itemId) {
		int pos = posH[itemId];
		int pidS = findChunk(pos); //zu überprüfen!!!
		int pivotPos = S.get(pidS);
		
		if(pivotPos == pos){
			extractPivot(pidS);
		}
		else pidS++;
		
		int sSize = S.size();
		if(pidS == sSize){
			heap[pos] = newItem;
			return;
		}
		
		int nextPivotPos = S.get(pidS);
		Key nextPivot = heap[nextPivotPos];
		if(smallerOrEqual(nextPivot, newItem)){
			heap[pos] = newItem;
			return;
		}
		else {
			int elementToMovePos = nextPivotPos + 1;
			Key temp = heap[elementToMovePos];
			heap[pos] = temp;
			posH[temp.getID()] = pos;
			heap[elementToMovePos] = newItem;
			posH[itemId] = elementToMovePos;
			insert(newItem, pidS, itemId);
		}
	}
	
	private void extractPivot(int pidS){
		int sSize = S.size();
		for(int i = pidS; i < sSize - 1; i++){
			S.set(i, S.get(i+1));
		}
		S.pop();
	}
	
	private void insert(Key item, int pidS, int itemId) {
		int sSize = S.size();
		int pivotPos, newPivotPos, nextPivotPos, elementToMovePos;
		Key temp, pivot, nextPivot;
		
		//if pidS is the virtual pivot
		if(pidS == 0){
			pivotPos = S.get(0);
			S.set(0, pivotPos+1);
			heap[pivotPos % capacity] = item;
			posH[itemId] = pivotPos % capacity;
			
			pidS++;
			
			if(pidS == sSize)
				return;
			else {
				nextPivotPos = S.get(pidS);
				nextPivot = heap[nextPivotPos % capacity];
				if(smaller(item, nextPivot)){
					elementToMovePos = nextPivotPos+1;
					temp = heap[elementToMovePos];
					heap[pivotPos % capacity] = temp;
					posH[temp.getID()] = pivotPos % capacity;
					heap[elementToMovePos % capacity] = item;
					posH[itemId] = elementToMovePos % capacity;
				}
				else{
					return;
				}
			}
		}
		
		while(true){
			pivotPos = S.get(pidS);
			pivot = heap[pivotPos];
			newPivotPos = pivotPos+1;
			S.set(pidS, newPivotPos);
			
			heap[newPivotPos % capacity] = pivot;
			posH[pivot.getID()] = newPivotPos % capacity;
			heap[pivotPos % capacity] = item;
			posH[itemId] = pivotPos % capacity;
			
			pidS++;
			if(pidS == sSize){
				return;
			}
			else {
				nextPivotPos = S.get(pidS);
				nextPivot = heap[nextPivotPos % capacity];
				if(smaller(item, nextPivot)){
					elementToMovePos = nextPivotPos+1;
					temp = heap[elementToMovePos];
					heap[pivotPos % capacity] = temp;
					posH[temp.getID()] = pivotPos % capacity;
					heap[elementToMovePos % capacity] = item;
					posH[itemId] = elementToMovePos % capacity;
				}
				else{
					return;
				}
			}
		}
	}

	public void add(Key item){
		n++;
		int itemId = item.getID();
		int pos = posH[itemId];
		if(pos > -1)
			throw new IllegalArgumentException("Element is already in the heap");
		else
			insert(item, 0, itemId);
	}
	/**
	 * Deletes the key at position pos
	 * 
	 * @param pos
	 */
	public void delete(int pos) {
		int pidx = findChunk(pos);
		// if pos is a pivot we extract it from S
		// and consider the next pivot
		if (S.get(pidx) == pos) {
			S.remove(pidx);
			pidx--;
		}
		// move element to left of the pivot to pos,
		// move pivot one position to the left, update
		// pos and continue with the next pivot
		for (int i = pidx; i >= 0; i--) {
			heap[pos % capacity] = heap[(S.get(i) - 1) % capacity];
			posH[heap[pos % capacity].getID()] = heap[(S.get(i) - 1) % capacity].getID();
			heap[(S.get(i) - 1) % capacity] = heap[S.get(i) % capacity];
			posH[heap[S.get(i)].getID()] = S.get(i) - 1;
			S.set(i, S.get(i) - 1);
			pos = S.get(i) + 1;
		}
		n--;
	}

	/**
	 * Deletes Key x
	 * 
	 * @param x
	 */
	public void delete(Key x) {
		if (posH[x.getID()] == -1) {
			throw new NoSuchElementException("Key is not in the QuickHeap");
		} else {
			int pos = posH[x.getID()];
			int pidx = findChunk(pos);
			if (S.get(pidx) == pos) {
				S.remove(pidx);
				pidx--;
			}
			for (int i = pidx; i >= 0; i--) {
				heap[pos % capacity] = heap[(S.get(i) - 1) % capacity];
				posH[heap[pos % capacity].getID()] = heap[(S.get(i) - 1) % capacity].getID();
				heap[(S.get(i) - 1) % capacity] = heap[S.get(i) % capacity];
				posH[heap[(S.get(i) - 1) % capacity].getID()] = heap[S.get(i) % capacity].getID();
				S.set(i, S.get(i) - 1);
				pos = S.get(i) + 1;
			}
			n--;
		}
	}

	/**
	 * Incrementally gives the next smallest element of the QuickHeap
	 * 
	 * @param idx
	 * @param S
	 * @return
	 */
	private Key incrementalQuickSort(int idx, Stack<Integer> S) {
		Random rand = new Random();
		int pidx;
		if (idx == S.peek()) {
			// S.pop();
			return heap[idx % capacity];
		}
		pidx = rand.nextInt(S.peek() - idx) + idx;
		// pidx = S.peek() - 1;
		int pidxNew = partition(pidx, idx, S.peek() - 1);
		S.push(pidxNew);
		return incrementalQuickSort(idx, S);
	}

	private Key incrementalQuickSortIterative(int idx, Stack<Integer> S) {
		Random rand = new Random();
		int pidx;
		while (S.peek() != idx) {
			pidx = rand.nextInt(S.peek() - idx) + idx;
			//pidx = S.peek() - 1;
			int pidxNew = partition(pidx, idx, S.peek() - 1);
			S.push(pidxNew);
		}
		return heap[idx % capacity];
	}

	/**
	 * Helper routine for incrementalQuickSort. Partitions the QuickHeap from
	 * leftIndex to rightIndex with the pivot element pivot
	 * 
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
	 * 
	 * @return
	 */
	public Key findMin() {
		incrementalQuickSort(idx, S);
		return heap[idx % capacity];
	}

	/**
	 * Extracts the smallest Element
	 * 
	 * @return
	 */
	public Key extractMin() {
		if(isEmpty()){
			return null;
		}
		incrementalQuickSortIterative(idx, S);
		idx++;
		S.pop();
		n--;
		posH[heap[(idx - 1) % capacity].getID()] = -1;
		return heap[(idx - 1) % capacity];
	}

	/**
	 * Returns 1 if i <= j
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean smaller(Key i, Key j) {
		return ((Comparable<Key>) i).compareTo(j) < 0;
	}
	
	private boolean smallerOrEqual(Key i, Key j){
		return ((Comparable<Key>)i).compareTo(j) <= 0;
	}

	/**
	 * Swaps elements at index1 and index2
	 * 
	 * @param index1
	 * @param index2
	 */
	private void swap(int index1, int index2) {
		Key temp = heap[index1 % capacity];
		heap[index1 % capacity] = heap[index2 % capacity];
		posH[heap[index2 % capacity].getID()] = index1 % capacity;
		heap[index2 % capacity] = temp;
		posH[temp.getID()] = index2 % capacity;
	}

	/**
	 * Copies elements from src to dest
	 * 
	 * @param dest
	 * @param src
	 */
	private void copy(Key[] dest, Key[] src) {
		System.arraycopy(src, 0, dest, 0, src.length);
	}

	/**
	 * Returns the bigger integer
	 * 
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
	 * 
	 * @param pos
	 * @return
	 */
	private int findChunk(int pos) {
		int pidx = 0;
		while (pidx < S.size() && S.get(pidx) >= pos)
			pidx++;
		return pidx - 1;
	}

	/**
	 * Returns true if QuickHeap is empty
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return (n == 0);
	}

	/**
	 * Returns true if QuickHeap contains x
	 * 
	 * @param x
	 * @return
	 */
	public boolean contains(Key x) {
		return (posH[x.getID()] != -1);
	}
}