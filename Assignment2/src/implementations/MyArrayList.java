package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

/**
 * A dynamic array-based implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in an internally managed array
 * that grows automatically when capacity is reached. The growth amount is
 * determined by a configurable growth rate expressed as a decimal percentage
 * of the current array length (e.g., {@code 0.25} means 25% growth).
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyArrayList<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyArrayList implements ListADT
{
    /** Starting length of the internal array. */
    private static final int DEFAULT_CAPACITY = 10;

    /** Default growth rate (25%). */
    private static final double DEFAULT_GROWTH_RATE = 0.25;

    /** Internal array holding the elements. */
    private String[] elements;

    /** Number of elements in the list. */
    private int size;

    /** Growth rate as a decimal (0.25 = 25%). */
    private double growthRate;

    /**
     * Creates an empty list with the default growth rate.
     */
    public MyArrayList()
    {
        this( DEFAULT_GROWTH_RATE );
    }

    /**
     * Creates an empty list with the given growth rate.
     *
     * @param growthRate growth rate as a decimal
     */
    public MyArrayList( double growthRate )
    {
        elements = new String[ DEFAULT_CAPACITY ];
        size = 0;
        this.growthRate = growthRate;
    }

    /**
     * @return the number of elements
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Removes all elements from the list.
     */
    @Override
    public void clear()
    {
        for( int i = 0; i < size; i++ )
        {
            elements[ i ] = null;
        }
        size = 0;
    }

    /**
     * Inserts an element at the given index, shifting later elements right.
     *
     * @param index position to insert at
     * @param toAdd element to insert
     * @return true if added
     * @throws NullPointerException if toAdd is null
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public boolean add( int index, String toAdd )
            throws NullPointerException, IndexOutOfBoundsException
    {
        if( toAdd == null )
        {
            throw new NullPointerException( "Cannot store a null element." );
        }
        if( index < 0 || index > size )
        {
            throw new IndexOutOfBoundsException( "Index outside bounds: " + index );
        }

        if( size == elements.length )
        {
            grow();
        }

        for( int i = size; i > index; i-- )
        {
            elements[ i ] = elements[ i - 1 ];
        }

        elements[ index ] = toAdd;
        size++;
        return true;
    }

    /**
     * Appends an element to the end of the list.
     *
     * @param toAdd element to append
     * @return true if added
     * @throws NullPointerException if toAdd is null
     */
    @Override
    public boolean add( String toAdd ) throws NullPointerException
    {
        return add( size, toAdd );
    }

    /**
     * Appends every element of another list.
     *
     * @param toAdd list to append
     * @return true if added
     * @throws NullPointerException if toAdd is null
     */
    @Override
    public boolean addAll( ListADT toAdd ) throws NullPointerException
    {
        if( toAdd == null )
        {
            throw new NullPointerException( "Cannot add a null list." );
        }
        for( int i = 0; i < toAdd.size(); i++ )
        {
            this.add( toAdd.get( i ) );
        }
        return true;
    }

    /**
     * @param index position of the element
     * @return the element at index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public String get( int index ) throws IndexOutOfBoundsException
    {
        if( index < 0 || index >= size() || size() == 0 )
        {
            throw new IndexOutOfBoundsException( "Index out of bounds: " + index );
        }
        return elements[ index ];
    }

    /**
     * Removes the element at the given index, shifting later elements left.
     *
     * @param index position to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public String remove( int index ) throws IndexOutOfBoundsException
    {
        if( index < 0 || index >= size() )
        {
            throw new IndexOutOfBoundsException( "Index out of bounds: " + index );
        }

        String removed = elements[ index ];

        for( int i = index; i < size - 1; i++ )
        {
            elements[ i ] = elements[ i + 1 ];
        }

        elements[ size - 1 ] = null;
        size--;
        return removed;
    }

    /**
     * Removes the first occurrence of an element.
     *
     * @param toRemove element to remove
     * @return the removed element, or null if not found
     * @throws NullPointerException if toRemove is null
     */
    @Override
    public String remove( String toRemove ) throws NullPointerException
    {
        if( toRemove == null )
        {
            throw new NullPointerException( "Cannot remove a null element." );
        }

        int index = -1;

        for( int i = 0; i < size; i++ )
        {
            if( elements[ i ].equals( toRemove ) )
            {
                index = i;
                break;
            }
        }

        if( index > -1 )
        {
            return remove( index );
        }
        return null;
    }

    /**
     * Replaces the element at the given index.
     *
     * @param index position to change
     * @param toChange new element
     * @return the previous element
     * @throws NullPointerException if toChange is null
     * @throws IndexOutOfBoundsException if index is invalid
     */
    
    @Override
    public String set( int index, String toChange )
            throws NullPointerException, IndexOutOfBoundsException
    {
        if( toChange == null )
        {
            throw new NullPointerException( "Cannot store a null element." );
        }
        if( index < 0 || index >= size() )
        {
            throw new IndexOutOfBoundsException( "Index out of bounds: " + index );
        }

        String old = elements[ index ];
        elements[ index ] = toChange;
        return old;
    }

    /**
     * @return true if the list has no elements
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * @param toFind element to look for
     * @return true if the element is in the list
     * @throws NullPointerException if toFind is null
     */
    
    @Override
    public boolean contains( String toFind ) throws NullPointerException
    {
        if( toFind == null )
        {
            throw new NullPointerException( "Cannot search for a null element." );
        }
        if( isEmpty() )
        {
            return false;
        }

        for( int i = 0; i < size; i++ )
        {
            if( toFind.equals( elements[ i ] ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param toFind element to look for
     * @return index of the first match, or -1 if not found
     * @throws NullPointerException if toFind is null
     */
    @Override
    public int indexOf( String toFind ) throws NullPointerException
    {
        if( toFind == null )
        {
            throw new NullPointerException( "Cannot search for a null element." );
        }
        if( isEmpty() )
        {
            return -1;
        }

        for( int i = 0; i < size; i++ )
        {
            if( toFind.equals( elements[ i ] ) )
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Copies the elements into an array, making a new one if toHold is too small.
     *
     * @param toHold array to copy into
     * @return array containing the elements in order
     * @throws NullPointerException if toHold is null
     */
    
    @Override
    public String[] toArray( String[] toHold ) throws NullPointerException
    {
        if( toHold == null )
        {
            throw new NullPointerException( "Cannot copy to a null array." );
        }
        if( toHold.length < size )
        {
            toHold = Arrays.copyOf( toHold, size );
        }

        for( int i = 0; i < size; i++ )
        {
            toHold[ i ] = elements[ i ];
        }
        return toHold;
    }

    /**
     * @return an iterator over a snapshot of the list
     */
    @Override
    public Iterator iterator()
    {
        return new ArrayListIterator();
    }

    /**
     * Grows the internal array by the growth rate (at least one slot).
     */
    private void grow()
    {
        int increase = (int) ( elements.length * growthRate );
        if( increase < 1 )
        {
            increase = 1;
        }
        elements = Arrays.copyOf( elements, elements.length + increase );
    }

    /**
     * Iterator over a copy of the list taken when it was created.
     */
    private class ArrayListIterator implements Iterator
    {
        /** Snapshot of the elements. */
        private String[] copyOfElements;

        /** Position of the next element. */
        private int pos;

        /**
         * Creates the iterator and copies the current elements.
         */
        public ArrayListIterator()
        {
            copyOfElements = new String[ size ];

            for( int i = 0; i < size; i++ )
            {
                copyOfElements[ i ] = elements[ i ];
            }
            pos = 0;
        }

        /**
         * @return true if more elements remain
         */
        
        @Override
        public boolean hasNext()
        {
            return pos < copyOfElements.length;
        }

        /**
         * @return the next element
         * @throws NoSuchElementException if none remain
         */
        
        @Override
        public String next() throws NoSuchElementException
        {
            if( !hasNext() )
            {
                throw new NoSuchElementException( "No more elements in the iteration." );
            }
            return copyOfElements[ pos++ ];
        }
    }
}