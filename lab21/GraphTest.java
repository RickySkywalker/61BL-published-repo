import com.sun.source.tree.AssertTree;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void test1(){
        Graph g1 = new Graph();
        g1.addEdge(1, 2, 2);
        g1.addEdge(2, 4, 2);
        g1.addEdge(1, 4, 3);
        g1.addEdge(4, 3, 2);

        g1.addEdge(20, 10, 4);


        Graph g2 = g1.kruskals();
        g2 = g1.prims(0);

        Graph g3 = new Graph();
        g3.addEdge(1, 2, 2);
        g3.addEdge(2, 4, 2);
        g3.addEdge(4, 3, 2);

        g3.addEdge(20, 10, 0);


    }

}