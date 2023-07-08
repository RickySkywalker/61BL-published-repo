package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> lld = new LinkedListDeque<Integer>();

    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement)." +
                " The code you submit to the AG shouldn't have any print statements!");

        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
        lld.addFirst(0);
        assertFalse("lld1 should now contain 1 item", lld.isEmpty());

    }

    /* Test about add first method*/
    @Test
    public void addFirstTest() {
        lld = new LinkedListDeque<Integer>();
        lld.addFirst(0);
        assertFalse(lld.isEmpty());
        lld.addFirst(1);
        assertTrue(lld.get(1).equals(0));
        assertTrue(lld.get(0).equals(1));

        String Ricky = "Ricky", Sherry = "Sherry";

        Deque<String> ls1 = new LinkedListDeque<String>();
        ls1.addFirst(Ricky);
        ls1.addFirst(Sherry);

        assertTrue(ls1.get(0).equals(Sherry));
        assertTrue(ls1.get(1).equals(Ricky));
    }

    // Test about the add last method
    @Test
    public void addLastTest(){
        lld = new LinkedListDeque<Integer>();
        lld.addLast(0);
        lld.addLast(1);
        lld.addLast(2);

        assertTrue(lld.get(0) == 0);
        assertTrue(lld.get(1) == 1);

        String Ricky = "Ricky", Sherry = "Sherry";

        Deque<String> ls1 = new LinkedListDeque<String>();
        ls1.addLast(Ricky);
        ls1.addLast(Sherry);

        assertTrue(ls1.get(1).equals(Sherry));
        assertTrue(ls1.get(0).equals(Ricky));

    }

    @Test
    public void get(){
        lld = new LinkedListDeque<Integer>();
        lld.addLast(0);
        lld.addLast(1);
        lld.addLast(2);

        assertEquals(0, (int) lld.get(0));
        assertEquals(1, (int) lld.get(1));
        assertEquals(2, (int) lld.get(2));
    }

    @Test
    public void removeFirst(){
        lld = new LinkedListDeque<Integer>();
        lld.addLast(0);
        lld.addLast(1);
        lld.addLast(2);

        int a = lld.removeFirst();
        assertEquals(0, a);
        assertEquals(2, lld.size());
        assertEquals(1, (int) lld.get(0));
        assertEquals(2, (int) lld.get(1));
    }

    @Test
    public void removeLast(){
        lld = new LinkedListDeque<>();
        lld.addLast(0);
        lld.addLast(1);
        lld.addLast(2);

        int a = lld.removeLast();
        assertEquals(2,a);
        assertEquals(2, lld.size());
        assertEquals(0, (int) lld.get(0));
        assertEquals(1, (int) lld.get(1));

    }

    @Test
    public void printDeque(){
        lld = new LinkedListDeque<Integer>();
        lld.addLast(0);
        lld.addLast(1);
        lld.addLast(2);
        lld.printDeque();

        String Ricky = "Ricky", Sherry = "Sherry";

        Deque<String> ls1 = new LinkedListDeque<String>();
        ls1.addLast(Ricky);
        ls1.addLast(Sherry);

        ls1.printDeque();

    }


    @Test
    public void equalCheck(){
        lld = new LinkedListDeque<>();
        for (int i = 0; i < 20; i++){
            lld.addLast(i);
        }

        LinkedListDeque<Integer> test1 = new LinkedListDeque<>();
        for (int i = 0; i < 20; i++){
            test1.addLast(i);
        }

        assertTrue(lld.equals(test1));

        LinkedListDeque<String> test2 = new LinkedListDeque<>(), test3 = new LinkedListDeque<>();
        String Ricky = "Ricky",loves = "loves", Sherry = "Sherry";
        test2.addLast(Ricky);
        test2.addLast(loves);
        test2.addLast(Sherry);

        test3.addLast(Ricky);
        test3.addLast(Sherry);

        assertFalse(test2.equals(test3));

        lld = new LinkedListDeque<>();
        for (int i = 0; i < 20; i++){
            if (i == 10){
                lld.addLast(1);
            }
            lld.addLast(i);
        }

        LinkedListDeque<Integer> test4 = new LinkedListDeque<>();
        for (int i = 0; i < 20; i++){
            if (i == 10){
                test4.addLast(i);
            }
            test4.addLast(i);
        }

        assertFalse(lld.equals(test4));

    }
}