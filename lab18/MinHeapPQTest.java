import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ<String> test = new MinHeapPQ<>();
        test.insert("Ricky", 1);
        test.insert("Sherry", 2);
        test.insert("Amanda", 3);
        test.insert("David", 4);
        test.insert("Empire", 0);
        assertEquals("Empire", test.peek());
        assertEquals("Empire", test.poll());
        assertEquals(4, test.size());
        test.changePriority("Sherry", 0);
        assertEquals("Sherry", test.peek());
    }
}
