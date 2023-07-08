import org.junit.Test;
import static org.junit.Assert.*;

public class BooleanSetTest {

    @Test
    public void testBasics() {
        BooleanSet aSet = new BooleanSet(100);
        assertEquals(0, aSet.size());
        for (int i = 0; i < 100; i += 2) {
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }
        assertEquals(50, aSet.size());

        for (int i = 0; i < 100; i += 2) {
            aSet.remove(i);
            assertFalse(aSet.contains(i));
        }
        assertTrue(aSet.isEmpty());
        assertEquals(0, aSet.size());
    }

    @Test
    public void test2(){
        BooleanSet set1 = new BooleanSet(100);
        for (int i = 1; i < 5; i++){
            set1.add(i);
        }
        int[] toArray = set1.toIntArray();

        for (int i = 1; i < 5; i++){
            assertTrue(i == toArray[i-1]);
        }
    }
}