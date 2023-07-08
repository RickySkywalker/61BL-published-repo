import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void test1() {
        MinHeap<Integer> aaa = new MinHeap<>();
        aaa.insert(10);
        aaa.insert(20);
        aaa.insert(5);
        aaa.insert(1);
        aaa.insert(0);
        aaa.insert(40);
        aaa.insert(100);
        System.out.println(aaa);
        assertTrue(7 == aaa.size());
        assertEquals(0, (int) aaa.findMin());
        assertEquals(0, (int) aaa.removeMin());
        assertEquals(1, (int) aaa.removeMin());
        assertTrue(5 == aaa.size());
        assertTrue(aaa.contains(100));
        assertFalse(aaa.contains(30));
    }

    @Test
    public void test2(){
        MinHeap<Integer> aaa = new MinHeap<>();
        aaa.insert(10);
        aaa.insert(20);
        aaa.insert(5);
        aaa.insert(1);
        aaa.insert(0);
        aaa.insert(40);
        aaa.insert(100);
        System.out.println(aaa);
        aaa.update(20);
        System.out.println(aaa);
        assertEquals(7, aaa.size());
    }
}
