/** Binary heap interface provides methods on a binary heap
 * 
 * @author nganhoang
 *
 * @param <T>
 */

public interface BinaryHeap<T extends Comparable<T>> {
	
	public void maxHeapify(int i);
	
	public void buildMaxHeap();
}
