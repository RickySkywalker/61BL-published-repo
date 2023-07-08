package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest<T> {

    /**
     * You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test.
     */

    public static Deque<Integer> ad;

    @Test
    public void testAddFirst() {
        ad = new ArrayDeque<>();
        ad.addFirst(6);
        ad.addFirst(5);
        ad.addFirst(3);
        ad.addFirst(8);
        ad.addFirst(9);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(10);
        String expected = "10 2 1 9 8 3 5 6";
        assertEquals(expected, ad.toString());
    }
    @Test
    public void testAddLast(){
        ad = new ArrayDeque<>();
        ad.addLast(6);
        ad.addLast(5);
        ad.addLast(3);
        ad.addLast(8);
        ad.addLast(9);
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(10);
        String expected = "6 5 3 8 9 1 2 10";
        assertEquals(expected, ad.toString());

    }
    @Test
    public void  testIsEmpty(){
        ad = new ArrayDeque<>();
        boolean expected = true;
        assertEquals(expected,ad.isEmpty());
        ad.addLast(6);
        ad.addFirst(5);
        ad.addLast(3);
        expected = false;
        assertEquals(expected,ad.isEmpty());

    }
    @Test
    public void testSize(){
        ad = new ArrayDeque<>();
        int expected = 0;
        assertEquals(expected,ad.size());
        ad.addLast(6);
        ad.addFirst(5);
        ad.addLast(3);
        expected = 3;
        assertEquals(expected,ad.size());


    }
    @Test
    public void testRemoveFirst(){
        ad = new ArrayDeque<>();
        ad.addFirst(6);
        ad.addFirst(5);
        ad.addFirst(3);
        ad.addFirst(8);
        ad.addFirst(9);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(10);;
        ad.removeFirst();
        String expected = "2 1 9 8 3 5 6";
        assertEquals(expected,ad.toString());



    }
    @Test
    public void testRemoveLast(){
        ad = new ArrayDeque<>();
        ad.addFirst(6);
        ad.addFirst(5);
        ad.addFirst(3);
        ad.addFirst(8);
        ad.addFirst(9);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(10);;
        ad.removeLast();
        String expected = "10 2 1 9 8 3 5";
        assertEquals(expected,ad.toString());
    }
    @Test
    public void testGet(){
        ad = new ArrayDeque<>();
        ad.addFirst(6);
        ad.addFirst(5);
        ad.addFirst(3);
        ad.addFirst(8);
        ad.addFirst(9);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(10);;
        ad.removeLast();
        int actual = (int)ad.get(4);
        assertEquals(8,actual);
    }

}



