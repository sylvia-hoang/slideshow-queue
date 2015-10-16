
public interface PriorityQueue<T extends Comparable<T>> {
	
	// Insert a key into  element
	public void insert(T key);
	
	// Return the largest key (like peek)
	public T maximum();
	
	// Return and remove the largest key
	public T extractMax();
	
	// Increase the key at index i to the given key
	public void increaseKey(int i, T key); 
	
}
