
package implementations;

import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

/**
 * A doubly linked list implementation of the ListADT interface.
 * Stores String elements in nodes connected through predecessor
 * and successor references.
 */
public class MyDLL implements ListADT
{
    private MyDLLNode head;
    private MyDLLNode tail;
    private int size;

    /**
     * Creates an empty doubly linked list.
     */
    public MyDLL()
    {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
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
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds an element at the specified index.
     *
     * @param index the position where the element is inserted
     * @param toAdd the String to insert
     * @return true if the element was added
     * @throws NullPointerException if toAdd is null
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public boolean add(int index, String toAdd)
    {
        if (toAdd == null)
        {
            throw new NullPointerException("Cannot add a null element.");
        }

        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        MyDLLNode newNode = new MyDLLNode(toAdd);

        if (size == 0)
        {
            head = newNode;
            tail = newNode;
        }
        else if (index == 0)
        {
            newNode.setSucc(head);
            head.setPred(newNode);
            head = newNode;
        }
        else if (index == size)
        {
            newNode.setPred(tail);
            tail.setSucc(newNode);
            tail = newNode;
        }
        else
        {
            MyDLLNode current = getNode(index);
            MyDLLNode previous = current.getPred();

            newNode.setPred(previous);
            newNode.setSucc(current);

            previous.setSucc(newNode);
            current.setPred(newNode);
        }

        size++;
        return true;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param toAdd the String to add
     * @return true if the element was added
     * @throws NullPointerException if toAdd is null
     */
    @Override
    public boolean add(String toAdd)
    {
        return add(size, toAdd);
    }

    /**
     * Adds all elements from another list to the end of this list.
     *
     * @param toAdd the list whose elements will be added
     * @return true if at least one element was added
     * @throws NullPointerException if toAdd is null
     */
    @Override
    public boolean addAll(ListADT toAdd)
    {
        if (toAdd == null)
        {
            throw new NullPointerException("The supplied list cannot be null.");
        }

        if (toAdd.isEmpty())
        {
            return false;
        }

        boolean changed = false;
        Iterator iterator = toAdd.iterator();

        while (iterator.hasNext())
        {
            add(iterator.next());
            changed = true;
        }

        return changed;
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index the position of the element
     * @return the String at that position
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public String get(int index)
    {
        return getNode(index).getData();
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * @param index the position of the element to remove
     * @return the removed String
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public String remove(int index)
    {
        MyDLLNode nodeToRemove = getNode(index);
        String removedData = nodeToRemove.getData();

        MyDLLNode previous = nodeToRemove.getPred();
        MyDLLNode next = nodeToRemove.getSucc();

        if (previous == null)
        {
            head = next;
        }
        else
        {
            previous.setSucc(next);
        }

        if (next == null)
        {
            tail = previous;
        }
        else
        {
            next.setPred(previous);
        }

        size--;

        if (size == 0)
        {
            head = null;
            tail = null;
        }

        return removedData;
    }

    /**
     * Removes the first occurrence of the specified String.
     *
     * @param toRemove the String to remove
     * @return the removed String, or null if it was not found
     * @throws NullPointerException if toRemove is null
     */
    @Override
    public String remove(String toRemove)
    {
        if (toRemove == null)
        {
            throw new NullPointerException("Cannot remove a null element.");
        }

        int index = indexOf(toRemove);

        if (index == -1)
        {
            return null;
        }

        return remove(index);
    }

    /**
     * Replaces the element at the specified index.
     *
     * @param index the position to update
     * @param toChange the replacement String
     * @return the previous String at that position
     * @throws NullPointerException if toChange is null
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public String set(int index, String toChange)
    {
        if (toChange == null)
        {
            throw new NullPointerException("Cannot set an element to null.");
        }

        MyDLLNode node = getNode(index);
        String oldData = node.getData();

        node.setData(toChange);

        return oldData;
    }

    /**
     * Checks whether the list contains any elements.
     *
     * @return true if the list is empty
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Checks whether the specified String exists in the list.
     *
     * @param toFind the String to search for
     * @return true if the String is found
     * @throws NullPointerException if toFind is null
     */
    @Override
    public boolean contains(String toFind)
    {
        return indexOf(toFind) != -1;
    }

    /**
     * Returns the index of the first occurrence of the specified String.
     *
     * @param toFind the String to search for
     * @return the index, or -1 if it is not found
     * @throws NullPointerException if toFind is null
     */
    @Override
    public int indexOf(String toFind)
    {
        if (toFind == null)
        {
            throw new NullPointerException("Cannot search for null.");
        }

        MyDLLNode current = head;
        int index = 0;

        while (current != null)
        {
            if (current.getData().equals(toFind))
            {
                return index;
            }

            current = current.getSucc();
            index++;
        }

        return -1;
    }

    /**
     * Copies the list elements into the supplied array.
     * If the supplied array is too small, a new array is created.
     *
     * @param toHold the array to hold the elements
     * @return an array containing the list elements in order
     * @throws NullPointerException if toHold is null
     */
    @Override
    public String[] toArray(String[] toHold)
    {
        if (toHold == null)
        {
            throw new NullPointerException("The supplied array cannot be null.");
        }

        String[] result = toHold;

        if (toHold.length < size)
        {
            result = new String[size];
        }

        MyDLLNode current = head;
        int index = 0;

        while (current != null)
        {
            result[index] = current.getData();
            current = current.getSucc();
            index++;
        }

        if (result.length > size)
        {
            result[size] = null;
        }

        return result;
    }

    /**
     * Creates an iterator over a snapshot of the list.
     * Changes to the original list will not affect the iterator.
     *
     * @return an independent iterator
     */
    @Override
    public Iterator iterator()
    {
        return new DLLIterator(toArray(new String[size]));
    }

    /**
     * Returns the node at the specified index.
     * Traverses from the closest end of the list.
     *
     * @param index the node position
     * @return the node at the specified position
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    private MyDLLNode getNode(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        MyDLLNode current;

        if (index < size / 2)
        {
            current = head;

            for (int i = 0; i < index; i++)
            {
                current = current.getSucc();
            }
        }
        else
        {
            current = tail;

            for (int i = size - 1; i > index; i--)
            {
                current = current.getPred();
            }
        }

        return current;
    }

    /**
     * Iterator that traverses a private snapshot of the list.
     */
    private static class DLLIterator implements Iterator
    {
        private final String[] elements;
        private int position;

        /**
         * Creates an iterator using a copy of the list elements.
         *
         * @param elements the snapshot to traverse
         */
        private DLLIterator(String[] elements)
        {
            this.elements = elements.clone();
            this.position = 0;
        }

        /**
         * Checks whether another element is available.
         *
         * @return true if another element exists
         */
        @Override
        public boolean hasNext()
        {
            return position < elements.length;
        }

        /**
         * Returns the next element and advances the iterator.
         *
         * @return the next String
         * @throws NoSuchElementException if no elements remain
         */
        @Override
        public String next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("No more elements in the list.");
            }

            return elements[position++];
        }
    }
}
