/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node sentinal;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;

        public Node(Item i, Node prN, Node nxN) {
            item = i;
            previous = prN;
            prN.next = this;
            next = nxN;
            nxN.previous = this;
        }

        public Node() {

        }
    }


    // construct an empty deque
    public Deque() {
        size = 0;
        Node ref1 = new Node();
        Node ref2 = new Node();
        sentinal = new Node(null, ref1, ref2);
        sentinal.previous = sentinal;
        sentinal.next = sentinal;
        first = sentinal;
        last = sentinal;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The added item should NOT be null.");
        }
        Node newFirst = new Node(item, sentinal, first);
        first = newFirst;
        if (size == 0) {
            last = newFirst;
        }
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The added item should NOT be null.");
        }
        Node newLast = new Node(item, last, sentinal);
        last = newLast;
        if (size == 0) {
            first = newLast;
        }
        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Nothing to remove in the deque!");
        }
        Item targetItem = first.item;
        sentinal.next = first.next;
        first.next.previous = sentinal;
        first = first.next;
        if (size == 1) {
            last = sentinal;
        }
        size -= 1;
        return targetItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Nothing to remove in the deque!");
        }
        Item targetItem = last.item;
        sentinal.previous = last.previous;
        last.previous.next = sentinal;
        last = last.previous;
        if (size == 1) {
            first = sentinal;
        }
        size -= 1;
        return targetItem;
    }

    private class DequeIterator implements Iterator<Item> {
        private int itemCount = 0;
        private Node currNode = sentinal;

        public boolean hasNext() {
            return (itemCount < size);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items!");
            }
            itemCount += 1;
            currNode = currNode.next;
            return currNode.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        Iterator<Item> dequeIterator = new DequeIterator();
        return dequeIterator;
    }


    public static void main(String[] args) {
        Deque<String> d1 = new Deque<>();
        System.out.println(d1.isEmpty());
        d1.addFirst("front");
        System.out.println(d1.isEmpty());
        System.out.println(d1.size());
        d1.addFirst("middle");
        System.out.println(d1.size());
        d1.addLast("back");
        System.out.println(d1.size());
        d1.addLast("tail");
        d1.addFirst("head");
        d1.addFirst("extra");
        d1.addFirst("extra1");
        d1.addLast("extra2");
        System.out.println(d1.size());
        System.out.println(d1.removeFirst());
        System.out.println(d1.size());
        System.out.println(d1.removeLast());
        System.out.println(d1.size());
        System.out.println();
        for (String i : d1) {
            System.out.println(i);
        }
    }
}
