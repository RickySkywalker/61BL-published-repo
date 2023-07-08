import org.junit.Test;

import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void sizeOf() {
        UnionFind current = new UnionFind(20);
        assertEquals(1, current.sizeOf(2));
        current.union(1, 2);
        current.union(2,5);
        assertEquals(3, current.sizeOf(5));
    }

    @Test
    public void parent() {
        UnionFind current = new UnionFind(20);
        current.union(1, 2);
        current.union(1, 5);
        assertEquals(2, current.parent(5));

        assertEquals(-3, current.parent(2));
    }

    @Test
    public void connected() {
        UnionFind current = new UnionFind(20);
        current.union(1, 2);
        current.union(2,5);

        assertTrue(current.connected(1,5));
        assertFalse(current.connected(3,5));
    }

    @Test
    public void find() {
        UnionFind current = new UnionFind(20);
        current.union(1, 2);
        current.union(1,5);
        current.union(11,12);
        current.union(11,1);

        assertEquals(2,current.find(11));
        assertEquals(2, current.find(5));
        assertEquals(2,current.find(2));
        assertEquals(19, current.find(19));

    }

    @Test
    public void union() {
        UnionFind current = new UnionFind(20);
        current.union(1, 2);
        current.union(1,5);
        current.union(11,12);
        current.union(11,1);
        current.union(2, 2);
        current.union(11, 1);

        assertTrue(current.connected(5,12));
        assertEquals(2, current.parent(12));
        assertEquals(5, current.sizeOf(11));
        assertEquals(-5, current.parent(2));
    }

    @Test
    public void findRoot1() {
        UnionFind current = new UnionFind(20);
        current.union(1, 2);
        current.union(1,5);
        current.union(11,12);
        current.union(11,1);
        current.union(5,10);

        System.out.println(current.find(1));
        System.out.println(current.parent(5));

    }
}