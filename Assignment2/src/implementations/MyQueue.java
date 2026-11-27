package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.ListADT;
import utilities.QueueADT;

/**
 * A doubly linked list-backed implementation of the {@link QueueADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyDLL}, treating
 * index {@code 0} as the front of the queue and the last index as the back.
 * {@link #enqueue(String)} appends to the back; {@link #dequeue()} and
 * {@link #peek()} access the front. A queue follows a
 * <strong>First-In, First-Out (FIFO)</strong> ordering policy: the first
 * element enqueued is the first one returned.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyQueue<E>}), allowing any object type to be stored in the queue.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyQueue implements QueueADT
{
	 /**
     * Underlying storage. Index 0 is the front of the queue and
     * index {@code size() - 1} is the back.
     */
	
	private final ListADT list;


    /**
     * Creates a new, empty queue backed by a {@link MyDLL}.
     */
	
    public MyQueue()
    {
        list = new MyDLL();
    }
    
    /**
     * Adds the specified element to the back of this queue.
     *
     * @param toAdd  the {@link String} element to add; must not be {@code null}
     * @throws NullPointerException if {@code toAdd} is {@code null}
     */
    
    @Override
    public void enqueue(String toAdd) throws NullPointerException
    {
    	if (toAdd == null)
    	{
    		throw new NullPointerException("Cannot enqueue a null element");
    	}
    	list.add(toAdd);
    }
    
    /**
     * Removes and returns the element at the front of this queue.
     *
     * @return the {@link String} element removed from the front of the queue
     * @throws EmptyQueueException if the queue contains no elements
     */
   
    @Override
    public String dequeue() throws EmptyQueueException
    {
        if( list.isEmpty() )
        {
            throw new EmptyQueueException( "Cannot dequeue from an empty queue." );
        }
        return list.remove( 0 );
    }
    
    /**
     * Returns the element at the front of this queue without removing it.
     *
     * @return the {@link String} element currently at the front of the queue
     * @throws EmptyQueueException if the queue contains no elements
     */ 
    @Override
    public String peek() throws EmptyQueueException
    {
        if( list.isEmpty() )
        {
            throw new EmptyQueueException( "Cannot peek at an empty queue." );
        }
        return list.get( 0 );
    }
    
    /**
     * Removes all elements from this queue, leaving it empty.
     */
    
    @Override
    public void dequeueAll()
    {
        list.clear();
    }
    
    /**
     * Returns {@code true} if this queue contains no elements.
     *
     * @return {@code true} if the queue is empty; {@code false} otherwise
     */
    
    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }
    
    /**
     * Returns {@code true} if this queue contains at least one occurrence of
     * the specified element, compared using {@link String#equals(Object)}.
     *
     * @param toFind  the element to search for; must not be {@code null}
     * @return {@code true} if the element is present; {@code false} otherwise
     * @throws NullPointerException if {@code toFind} is {@code null}
     */
    
    @Override
    public boolean contains( String toFind ) throws NullPointerException
    {
        if( toFind == null )
        {
            throw new NullPointerException( "Cannot search for a null element." );
        }
        return list.contains( toFind );
    }
    
    /**
     * Returns the 1-based position of the specified element measured from the
     * front of this queue. The front element is at position 1. If the element
     * occurs more than once, the occurrence nearest the front is used.
     * A {@code null} argument can never be present, so it returns {@code -1}.
     *
     * @param toFind  the element to search for
     * @return the 1-based distance from the front, or {@code -1} if not found
     */
    
    @Override
    public int search( String toFind )
    {
        if( toFind == null )
        {
            return -1;
        }
        int index = list.indexOf( toFind );
        return ( index < 0 ) ? -1 : index + 1;
    }
    
    /**
     * Returns an iterator over the elements of this queue in front-to-back
     * order. The iterator traverses its own deep copy of the elements, so it
     * is unaffected by later changes to the queue.
     *
     * @return an {@link Iterator} over this queue, front to back
     */
    
    @Override
    public Iterator iterator()
    {
        return list.iterator();
    }
    
    /**
     * Compares this queue to another queue for equality. Two queues are equal
     * if they hold equal elements in the same front-to-back order.
     *
     * @param that  the queue to compare to; a {@code null} argument returns
     *              {@code false}
     * @return {@code true} if both queues contain equal elements in the same
     *         order; {@code false} otherwise
     */
    @Override
    public boolean equals( QueueADT that )
    {
        if( that == this )
        {
            return true;
        }
        if( that == null || this.size() != that.size() )
        {
            return false;
        }
        Iterator mine = this.iterator();
        Iterator theirs = that.iterator();
        while( mine.hasNext() && theirs.hasNext() )
        {
            if( !mine.next().equals( theirs.next() ) )
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Copies all elements of this queue into an array in front-to-back order.
     * If the provided array is large enough it is filled and returned;
     * otherwise a new array of sufficient size is allocated and returned.
     *
     * @param holder  the array to fill; must not be {@code null}
     * @return an array containing all elements of this queue, front to back
     * @throws NullPointerException if {@code holder} is {@code null}
     */
    
    @Override
    public String[] toArray( String[] holder ) throws NullPointerException
    {
        if( holder == null )
        {
            throw new NullPointerException( "The holder array cannot be null." );
        }
        return list.toArray( holder );
    }
    

    /**
     * Returns the number of elements currently in this queue.
     *
     * @return the current size of the queue; never negative
     */


    @Override
    public int size()
    {
        return list.size();
    }
}