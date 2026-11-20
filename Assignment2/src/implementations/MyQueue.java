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
     * Index 0 = front of the queue, index size()-1 = back.
     */
	
	private final ListADT list;

    public MyQueue()
    {
        list = new MyDLL();
    }
    
    @Override
    public void enqueue(String toAdd) throws NullPointerException
    {
    	if (toAdd == null)
    	{
    		throw new NullPointerException("Cannot enqueue a null element");
    	}
    	list.add(toAdd);
    }
   
    @Override
    public String dequeue() throws EmptyQueueException
    {
        if( list.isEmpty() )
        {
            throw new EmptyQueueException( "Cannot dequeue from an empty queue." );
        }
        return list.remove( 0 );
    }
    
    @Override
    public String peek() throws EmptyQueueException
    {
        if( list.isEmpty() )
        {
            throw new EmptyQueueException( "Cannot peek at an empty queue." );
        }
        return list.get( 0 );
    }
    
    @Override
    public void dequeueAll()
    {
        list.clear();
    }
    
    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }
    
    @Override
    public boolean contains( String toFind ) throws NullPointerException
    {
        if( toFind == null )
        {
            throw new NullPointerException( "Cannot search for a null element." );
        }
        return list.contains( toFind );
    }
    
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
    
    @Override
    public Iterator iterator()
    {
        return list.iterator();
    }
    
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
    
    @Override
    public String[] toArray( String[] holder ) throws NullPointerException
    {
        if( holder == null )
        {
            throw new NullPointerException( "The holder array cannot be null." );
        }
        return list.toArray( holder );
    }

    @Override
    public int size()
    {
        return list.size();
    }
}