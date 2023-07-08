import org.junit.Test;

import static org.junit.Assert.*;

public class ModCounterTest {

    public int N;
    @Test
    public void testConstructor() {
        ModCounter c = new ModCounter();
        assertEquals(0, c.value());
    }

    @Test
    public void testIncrement() throws Exception {
        ModCounter c = new ModCounter();
        c.increment();
        assertEquals(1,c.value());
        c.increment();
        assertEquals(2, c.value());
    }

    @Test
    public void testReset() throws Exception {
        ModCounter c = new ModCounter();
        c.increment();
        c.reset();
        assertEquals(0, c.value());
    }
}