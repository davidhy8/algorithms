/* *****************************************************************************
 *  Name: David Yang
 *  Date: June 8, 2020
 *  Description: Deque (two sided queue)
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;
    //private LinkedList<Item> deque;


    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
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
        validate(item);
        if (size == 0) {
            first = last = new Node();
            first.item = item;
            first.prev = null;
            first.next = null;
        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.prev = null;
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        validate(item);
        if (size == 0) {
            last = first = new Node();
            last.item = item;
            last.next = null;
            last.prev = null;
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = oldLast;
            oldLast.next = last;
        }
        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        Node oldFirst = first;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = oldFirst.next;
            first.prev = null;
        }
        size -= 1;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException();
        Node oldLast = last;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = oldLast.prev;
            last.next = null;
        }
        size -= 1;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        // note supported
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void validate(Item item) {
        if (item == null)
            throw new IllegalArgumentException("this item is null");
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("B");
        deque.addFirst("A");
        deque.addLast("C");
        for (String s : deque) System.out.println(s);
        deque.removeFirst();
        deque.removeLast();
        for (String s : deque) System.out.println(s);
        System.out.println(deque.size());
        System.out.println(deque.isEmpty());
        Iterator<String> iter = deque.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}

