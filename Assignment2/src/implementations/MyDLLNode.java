
package implementations;

/**
 * A single node in a doubly linked list.
 * Each node stores a String and references to its predecessor
 * and successor.
 */
public class MyDLLNode
{
    private String data;
    private MyDLLNode pred;
    private MyDLLNode succ;

    /**
     * Creates a node containing the specified String.
     *
     * @param data the String stored in this node
     */
    public MyDLLNode(String data)
    {
        this.data = data;
        this.pred = null;
        this.succ = null;
    }

    /**
     * Returns the data stored in this node.
     *
     * @return the stored String
     */
    public String getData()
    {
        return data;
    }

    /**
     * Updates the data stored in this node.
     *
     * @param data the new String
     */
    public void setData(String data)
    {
        this.data = data;
    }

    /**
     * Returns the predecessor node.
     *
     * @return the previous node, or null if this is the head
     */
    public MyDLLNode getPred()
    {
        return pred;
    }

    /**
     * Sets the predecessor node.
     *
     * @param pred the previous node
     */
    public void setPred(MyDLLNode pred)
    {
        this.pred = pred;
    }

    /**
     * Returns the successor node.
     *
     * @return the next node, or null if this is the tail
     */
    public MyDLLNode getSucc()
    {
        return succ;
    }

    /**
     * Sets the successor node.
     *
     * @param succ the next node
     */
    public void setSucc(MyDLLNode succ)
    {
        this.succ = succ;
    }
}
