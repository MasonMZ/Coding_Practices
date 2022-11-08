/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private int initialSize = 16;
    private Item[] itemArray;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        itemArray = (Item[]) new Object[initialSize];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The added item should NOT be null.");
        }
        if (size == itemArray.length) {
            upSize();
        }
        itemArray[size] = item;
        size += 1;
    }

    // helper function to upSize the array if the size reaches the maximum length
    private void upSize() {
        Item[] newArray = (Item[]) new Object[size * 2];
        System.arraycopy(itemArray, 0, newArray, 0, size);
        itemArray = newArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Nothing to remove in the deque!");
        }
        int targetID = StdRandom.uniformInt(size);
        Item targetItem = itemArray[targetID];
        itemArray[targetID] = itemArray[size - 1];
        itemArray[size - 1] = null;
        size -= 1;
        if (size <= itemArray.length / 4) {
            downSize();
        }
        return targetItem;
    }

    private void downSize() {
        Item[] newArray = (Item[]) new Object[itemArray.length / 2];
        System.arraycopy(itemArray, 0, newArray, 0, size);
        itemArray = newArray;
    }

    // return a random item (but do not remove it)
    public Item sample()


    public static void main(String[] args) {

    }
}
