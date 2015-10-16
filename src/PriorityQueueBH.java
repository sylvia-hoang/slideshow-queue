
/** PriorityQueueBH is a max priority queue implemented on a max-ordered heap
 * Implements the BinaryHeap and PriorityQueue interfaces
 * 
 * @author nganhoang
 *
 * @param <T>
 */
public class PriorityQueueBH<T extends Comparable<T>> implements BinaryHeap<T>, PriorityQueue<T>
{
	// binary heap for priority queue implementation
	private Comparable<T>[] heap;

	private int heapSize;


	/*********** CONSTRUCTORS ***********/

	/** Instantiating a priority queue given an array 
	 * 
	 * @param array
	 */
	public PriorityQueueBH(Comparable<T>[] array)
	{
		// Initialize heap to be one element larger than given array 
		heap = (Comparable<T>[]) new Comparable[array.length + 1];

		// Copy array into the heap, starting at index 1
		System.arraycopy(array, 0, heap, 1, array.length);

		// no. of heap elements inside the heap equals array length
		heapSize = array.length;
	}


	@Override
	public void insert(T key) 
	{

		// Increase heap size -- now the size of newHeap
		heapSize = heapSize + 1;

		// new heap with length being one element longer than the old one
		Comparable<T>[] newHeap = (Comparable<T>[]) new Comparable[heapSize + 1];

		// copy current heap over
		System.arraycopy(heap, 1, newHeap, 1, heapSize-1);

		// Reinitialize heap to be of the same length as new heap
		heap = (Comparable<T>[]) new Comparable[heapSize + 1];

		// Heap is now equal to the new heap
		heap = newHeap;

		//		System.out.println(this.toString());

		// Set key to be in the final leaf in the heap array
		heap[heapSize] = key;

		// Build max heap to maintain the property
		buildMaxHeap();	

	}

	@Override
	/** Return the largest key in the priority queue
	 * @return max key
	 */
	public T maximum() 
	{
		return (T) heap[1];
	}

	@Override
	/** Return and delete the largest key in the priority queue
	 * @return max key
	 */
	public T extractMax() 
	{	
		if (heapSize < 1)
			return null;

		T max = (T) heap[1];

		// exchange root with the current last leaf
		heap[1] = heap[heapSize];

		// decrease heap size
		heapSize = heapSize - 1;

		// max heapify from the root
		maxHeapify(1);

		return max;		
	}

	
	/** Increase key at index i to the given key
	 * @param index, key
	 */
	@Override
	public void increaseKey(int i, T key) 
	{

		if (heap[i].compareTo(key) > 0)
			System.err.println("new key is smaller than current key");
		else
		{
			// Set key to be the value of indicated node
			heap[i] = key;

			// Maintain the max heap property
			// While hasn't reached the root, and current key's parent is smaller key 
			while ( (i > 1) && (heap[parent(i)].compareTo((T) heap[i]) < 0) )
			{
				// Exchange current key with its parent
				Comparable<T> temp = heap[i];
				heap[i] = heap[parent(i)];
				heap[parent(i)] = temp;

				// Set i to be index of parent key
				i = parent(i);
			}
		}
	}


	/** Maintain max-heap property of the subtree rooted at index i
	 * @param index i marking root of subtree to be heapified
	 */
	@Override
	public void maxHeapify(int i)
	{
		// Calculate indexes of its left and right nodes 
		int left = left(i);
		int right = right(i);

		// Index of the largest node 
		int largest;

		// if left index is valid, and left value is larger than value at index i
		if ( (left <= heapSize) && ( heap[left].compareTo((T) heap[i]) > 0) )
			largest = left;
		else 
			largest = i;

		// if right index is valid, and right value is larger than current largest value
		if ( (right <= heapSize) && ( heap[right].compareTo((T) heap[largest]) > 0) )
			largest = right;

		// If index of largest value now different from i
		if (largest != i)
		{
			// Swap values at i and largest
			Comparable<T> temp = heap[i];
			heap[i] = heap[largest];
			heap[largest] = temp;

			// Recursive call to maintain max-heap property of subtree rooted at largest
			maxHeapify(largest);
		}
	}


	/** Construct a max heap from the current heap array 
	 */
	@Override
	public void buildMaxHeap()
	{
		// get index to start max heapify
		int i = (int) Math.floor( (heapSize+1)/2 );

		// max heapify each subtree rooted at node indexed from i till 1
		for (int j = i; j > 0; j--)
		{
			maxHeapify(j);
		}		
	}

	/** toString() method
	 * @return String representation of priority queue
	 */
	public String toString()
	{
		Comparable[] pqHeap = getHeap();

		String heapString = "";

		for (int i = 1; i <= heapSize; i++)
			heapString += pqHeap[i] + " ";

		return heapString;
	}


	/********* HELPER METHODS ***********/
	
	/** Return index of heap[i]'s left node
	 * @param i
	 * @return left i
	 */
	private int left(int i)
	{
		return 2*i;
	}

	/** Return index of heap[i]'s right node
	 * @param i
	 * @return right i
	 */
	private int right(int i)
	{
		return 2*i + 1;
	}

	/** Return index of heap[i]'s parent
	 * @param i
	 * @return parent of i
	 */
	private int parent(int i)
	{
		return (int) Math.floor(i/2);
	}

	/**
	 * @return the heap
	 */
	public Comparable[] getHeap() {
		return heap;
	}

	/**
	 * @return the heapSize
	 */
	public int getHeapSize() {
		return heapSize;
	}

}
