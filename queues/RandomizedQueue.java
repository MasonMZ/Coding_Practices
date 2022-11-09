/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
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
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("Nothing to remove in the deque!");
        }
        int targetID = StdRandom.uniformInt(size);
        Item targetItem = itemArray[targetID];
        return targetItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RdQueueIterator();
    }

    private class RdQueueIterator implements Iterator<Item> {
        public int itemNum = size;

        public boolean hasNext() {
            return (itemNum > 0);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items!");
            }
            int targetID = StdRandom.uniformInt(itemNum);
            Item targetItem = itemArray[targetID];
            itemArray[targetID] = itemArray[itemNum - 1];
            itemArray[itemNum - 1] = targetItem;
            itemNum -= 1;
            return targetItem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


    public static void main(String[] args) {
        RandomizedQueue<String> rd1 = new RandomizedQueue<>();
        System.out.println(rd1.isEmpty());
        rd1.enqueue("front");
        System.out.println(rd1.isEmpty());
        System.out.println(rd1.size());
        rd1.enqueue("middle");
        System.out.println(rd1.size());
        rd1.enqueue("back");
        System.out.println(rd1.size());
        rd1.enqueue("tail");
        rd1.enqueue("head");
        rd1.enqueue("extra");
        rd1.enqueue("extra1");
        rd1.enqueue("extra2");
        System.out.println(rd1.size());
        System.out.println(rd1.dequeue());
        System.out.println(rd1.size());
        System.out.println(rd1.sample());
        System.out.println(rd1.size());
        System.out.println();
        for (String i : rd1) {
            System.out.println(i);
        }
    }
}
