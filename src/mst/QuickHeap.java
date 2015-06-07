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
				//shift chunk one position to the left
				heap[(S.get(pidx) - 1) % capacity] = heap[(S.get(pidx + 1) + 1) & capacity];
				pidx++;
			}
		}
		
	}
	
	public void decreaseKey(Key oldKey, Key newKey){
		//testversion without hashmap to find element
		
		//search element
		int pos = 0;
		for(int i = 0; i < heap.length; i++){
			if(heap[i].equals(oldKey))
				break;
			pos++;
		}
		
		//search pidx
		int pidx = 0;
		while(true){
			if((S.get(pidx)+1) >= pos){
				pidx++;
			}
			else
				break;
		}
		
		//if key to decrease is pivot element
		if(pos == pidx){
			S.remove(pidx);
		}
		
		//decrease element
		while(true){
			if(S.size() == pidx + 1){
				heap[pos] = newKey;
				return;
			}
			else if(smaller(heap[S.get(pidx)+1], newKey)){
				heap[pos] = newKey;
				return;
			}
			else{
				//place element at the right of the next pivot in current position
				heap[pos] = heap[S.get(pidx+1)+1];
				//move pivot s[pidx+1] one position to the right
				S.set(pidx+1, (S.get(pidx+1)+1));
				//look at next chunk
				pos = S.get(pidx+1);
				pidx++;
			}
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
