import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleNameMapTest {

    @Test
    public void test1(){
        SimpleNameMap a = new SimpleNameMap();
        a.put("Skywalker", "Ricky");
        a.put("Ding", "Sherry");
        a.put("Xu", "Amanda");
        a.put("Napoleon", "Bonaparte");

        assertTrue(a.containsKey("Skywalker"));
        assertTrue(a.containsKey("Ding"));
        assertFalse(a.containsKey("Ricky"));
    }

    @Test
    public void test2(){
        SimpleNameMap a = new SimpleNameMap();
        a.put("Skywalker", "Ricky");
        a.put("Ding", "Sherry");
        a.put("Xu", "Amanda");
        a.put("Napoleon", "Bonaparte");
        a.put("Shen", "ShiXi");

        assertEquals("Ricky", a.get("Skywalker"));
        assertEquals("Sherry", a.get("Ding"));
        assertEquals("Amanda", a.remove("Xu"));
        assertEquals(null, a.remove("K"));
        assertEquals("ShiXi", a.get("Shen"));
        assertEquals(5, a.size);
    }

}