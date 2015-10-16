import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PriorityQueueTest {

	private PriorityQueueBH<Integer> pq;
	
	private final Integer[] ARRAY = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};

	@Before
	public void setUpPQ()
	{
		// ARRAY is used to initialize the priority queue
		pq = new PriorityQueueBH<Integer>(ARRAY);
		
		pq.buildMaxHeap();
		// pq now: 16 14 10 8 7 9 3 2 4 1
	}

	
	@Test
	public void testMax()
	{
		// Assert 16 to be max key and ensure the heap array is not modified
		assertEquals((Integer)16, pq.maximum());
		
		assertEquals("16 14 10 8 7 9 3 2 4 1 ", pq.toString());
	}
	
	@Test
	public void testExtractMax() 
	{
		// Assert that extract max returns the largest
		assertEquals((Integer)16, pq.extractMax());
		
		// Assert that 16 is deleted from the priority queue
		assertEquals("14 8 10 4 7 9 3 2 1 ", pq.toString());
	}
	
	@Test
	public void testIncreaseKey()
	{
		// Increase key at index 9 to 15 and assert equals
		pq.increaseKey(9, 15);
		
		assertEquals("16 15 10 14 7 9 3 2 8 1 ", pq.toString());
		
		// Try to change key at index 4 into 10 (invalid)
		// Assert that heap is not affected
		pq.increaseKey(4, 10);
		
		assertEquals("16 15 10 14 7 9 3 2 8 1 ", pq.toString());
	}
	
	@Test
	public void testInsertKey()
	{
		// Insert 1 into the heap
		pq.insert(1);

		assertEquals("16 14 10 8 7 9 3 2 4 1 1 ", pq.toString());
		
		// Insert 15 into the heap
		pq.insert(15);
		assertEquals("16 14 15 8 7 10 3 2 4 1 1 9 ", pq.toString());
		
		pq.insert(8);
		System.out.println(pq);
	}

}
