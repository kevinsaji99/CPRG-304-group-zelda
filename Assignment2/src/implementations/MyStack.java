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
     * Underlying storage. Index 0 is the bottom of the stack and
     * index {@code size() - 1} is the top.
     */
	
	private final ListADT list;
	
	/**
     * Creates a new, empty stack backed by a {@link MyArrayList}.
     */
	
	public MyStack()
	{
		list = new MyArrayList();
	}
	
	/**
     * Pushes the specified element onto the top of this stack by appending
     * it to the end of the underlying list.
     *
     * @param toAdd  the {@link String} element to push; must not be {@code null}
     * @throws NullPointerException if {@code toAdd} is {@code null}
     */
	
	@Override
	public void push(String toAdd) throws NullPointerException
	{
		if(toAdd == null)
		{
			throw new NullPointerException("Cannot push a null element.");
		}
		list.add(toAdd);
	}
	
	 /**
     * Removes and returns the element at the top of this stack.
     *
     * @return the {@link String} element removed from the top of the stack
     * @throws EmptyStackException if the stack contains no elements
     */
	
	@Override
	public String pop() throws EmptyStackException
	{
		if( list.isEmpty() )
        {
            throw new EmptyStackException();
        }
        return list.remove( list.size() - 1 );
	}
	
	 /**
     * Returns the element at the top of this stack without removing it.
     *
     * @return the {@link String} element currently at the top of the stack
     * @throws EmptyStackException if the stack contains no elements
     */
	
	@Override
    public String peek() throws EmptyStackException
    {
        if( list.isEmpty() )
        {
            throw new EmptyStackException();
        }
        return list.get( list.size() - 1 );
    }
	
	 /**
     * Removes all elements from this stack, leaving it empty.
     */
	
	 @Override
	    public void clear()
	    {
	        list.clear();
	    }
	 
	 /**
	     * Returns {@code true} if this stack contains no elements.
	     *
	     * @return {@code true} if the stack is empty; {@code false} otherwise
	     */
	 
	 @Override
	    public boolean isEmpty()
	    {
	        return list.isEmpty();
	    }
	 
	 /**
	     * Copies all elements of this stack into an array in bottom-to-top order.
	     * If the provided array is large enough it is filled and returned;
	     * otherwise a new array of sufficient size is allocated and returned.
	     *
	     * @param holder  the array to fill; must not be {@code null}
	     * @return an array containing all elements of this stack, bottom to top
	     * @throws NullPointerException if {@code holder} is {@code null}
	     */
	 
	 @Override
	 public String[] toArray(String[] holder) throws NullPointerException 
	 {
		 if( holder == null )
	        {
	            throw new NullPointerException( "The holder array cannot be null." );
	        }
	        return list.toArray( holder );
	 }
	 
	 /**
	     * Returns {@code true} if this stack contains at least one occurrence of
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
	     * top of this stack. The top element is at position 1. If the element
	     * occurs more than once, the occurrence nearest the top is used.
	     * A {@code null} argument can never be present, so it returns {@code -1}.
	     *
	     * @param toFind  the element to search for
	     * @return the 1-based distance from the top, or {@code -1} if not found
	     */
	 
	 @Override
	    public int search( String toFind )
	    {
	        if( toFind == null )
	        {
	            return -1;
	        }
	        
	        // Scan from the top (end of list) toward the bottom (index 0) so the
	        // occurrence nearest the top is found first.
	        for( int i = list.size() - 1; i >= 0; i-- )
	        {
	            if( toFind.equals( list.get( i ) ) )
	            {
	                return list.size() - i;   // 1-based distance from the top
	            }
	        }
	        return -1;
	    }
	
	 /**
	     * Returns an iterator over the elements of this stack in bottom-to-top
	     * order. The iterator traverses its own deep copy of the elements, so it
	     * is unaffected by later changes to the stack.
	     *
	     * @return an {@link Iterator} over this stack, bottom to top
	     */
	 
	 @Override
	    public Iterator iterator()
	    {
	        return list.iterator();
	    }
	 
	 /**
	     * Compares this stack to another stack for equality. Two stacks are equal
	     * if they hold equal elements in the same bottom-to-top order.
	     *
	     * @param that  the stack to compare to; a {@code null} argument returns
	     *              {@code false}
	     * @return {@code true} if both stacks contain equal elements in the same
	     *         order; {@code false} otherwise
	     */
	 
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

	  /**
	     * Returns the number of elements currently in this stack.
	     *
	     * @return the current depth of the stack; never negative
	     */
	 
	    @Override
	    public int size()
	    {
	        return list.size();
	    }
}