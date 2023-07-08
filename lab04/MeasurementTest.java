import org.junit.Test;

import static org.junit.Assert.*;

public class MeasurementTest {
    @Test
    public void test1() {
        // TODO: stub for first test
        Measurement a = new Measurement(6);
        Measurement b = new Measurement(7, 8);
        Measurement c = new Measurement(6,3);
        Measurement plus = new Measurement(13, 11);
        Measurement minus = new Measurement(1, 5);
        Measurement multiple = new Measurement(31, 3);
        assertEquals(6, a.getFeet());
        assertEquals(7, b.getFeet());
        assertEquals(8, b.getInches());
        Measurement aaa = c.multiple(5);
        assertEquals(plus.getFeet(), c.plus(b).getFeet());
        assertEquals(plus.getInches(), c.plus(b).getInches());
        assertEquals(multiple.getFeet(), c.multiple(5).getFeet());
        assertEquals(multiple.getInches(), c.multiple(5).getInches());
        assertEquals(minus.getFeet(), b.minus(c).getFeet());
        assertEquals(minus.getInches(), b.minus(c).getInches());
        assertEquals(6+"'"+3+'"',c.toString());
    }

    // TODO: Add additional JUnit tests for Measurement.java here.
    @Test
    public void test2() {
        // TODO: stub for first test
        Measurement a = new Measurement(4);
        Measurement b = new Measurement(8, 8);
        Measurement c = new Measurement(6,11);
        Measurement plus = new Measurement(15, 7);
        Measurement minus = new Measurement(1, 9);
        Measurement multiple = new Measurement(43, 4);
        assertEquals(plus.getFeet(), c.plus(b).getFeet());
        assertEquals(plus.getInches(), c.plus(b).getInches());
        assertEquals(multiple.getFeet(), b.multiple(5).getFeet());
        assertEquals(multiple.getInches(), b.multiple(5).getInches());
        assertEquals(minus.getFeet(), b.minus(c).getFeet());
        assertEquals(minus.getInches(), b.minus(c).getInches());
        assertEquals(6+"'"+11+'"',c.toString());
    }

}