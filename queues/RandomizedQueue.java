/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int N = 0;


    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        validate(item);
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        int num = StdRandom.uniform(N);
        Item result = s[num];
        s[num] = s[--N];
        if (N == s.length / 4) resize(s.length / 2);
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int num = StdRandom.uniform(N);
        return s[num];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int i = N;
        private Item[] sRandom;

        public RandomizedQueueIterator() {
            sRandom = (Item[]) new Object[N];
            for (int j = 0; j < N; j++) {
                sRandom[j] = s[j];
            }
            StdRandom.shuffle(sRandom);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return sRandom[--i];
        }
    }


    private void validate(Item item) {
        if (item == null)
            throw new IllegalArgumentException("this item is null");
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> test2 = new RandomizedQueue<String>();
        test2.enqueue("to");
        test2.enqueue("be");
        test2.enqueue("or");
        test2.enqueue("not");
        test2.enqueue("aman");
        test2.enqueue("agra");
        test2.enqueue("cool");
        for (String s : test2) {
            for (String s2 : test2) {
                System.out.print(s2 += " ");
            }
            System.out.print(s += " ");
        }
    }

}
