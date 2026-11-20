package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;
import utilities.StackADT;

/**
 * An array-backed implementation of the {@link StackADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyArrayList},
 * treating the end of the list as the top of the stack. {@link #push(String)}
 * appends to the end; {@link #pop()} and {@link #peek()} access the last
 * element. A stack follows a <strong>Last-In, First-Out (LIFO)</strong>
 * ordering policy: the last element pushed is the first one returned.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyStack<E>}), allowing any object type to be stored in the stack.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyStack implements StackADT
{
	/**
	 * Index 0 = bottom of the stack , index size()-1 = top.
	 */
	
	private final ListADT list;
	
	public MyStack()
	{
		list = new MyArrayList();
	}
	
	@Override
	public void push(String toAdd) throws NullPointerException
	{
		if(toAdd == null)
		{
			throw new NullPointerException("Cannot push a null element.");
		}
		list.add(toAdd);
	}
	
	@Override
	public String pop() throws EmptyStackException
	{
		if( list.isEmpty() )
        {
            throw new EmptyStackException();
        }
        return list.remove( list.size() - 1 );
	}
	
	@Override
    public String peek() throws EmptyStackException
    {
        if( list.isEmpty() )
        {
            throw new EmptyStackException();
        }
        return list.get( list.size() - 1 );
    }
	
	 @Override
	    public void clear()
	    {
	        list.clear();
	    }
	 
	 @Override
	    public boolean isEmpty()
	    {
	        return list.isEmpty();
	    }
	 
	 @Override
	 public String[] toArray(String[] holder) throws NullPointerException 
	 {
		 if( holder == null )
	        {
	            throw new NullPointerException( "The holder array cannot be null." );
	        }
	        return list.toArray( holder );
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
	        for( int i = list.size() - 1; i >= 0; i-- )
	        {
	            if( toFind.equals( list.get( i ) ) )
	            {
	                return list.size() - i;   // 1-based distance from the top
	            }
	        }
	        return -1;
	    }
	
	 @Override
	    public Iterator iterator()
	    {
	        return list.iterator();
	    }
	 
	 @Override
	    public boolean equals( StackADT that )
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
	    public int size()
	    {
	        return list.size();
	    }
}