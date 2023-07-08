import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {
    @Test
    public void test1(){
        Graph g3 = new Graph(7);
        g3.generateG3();
        g3.printDFS(0);
        g3.printDFS(6);
        g3.printPath(0, 6);
        g3.printPath(0, 3);
    }

    @Test
    public void test2(){
        Graph g = new Graph(9);
        g.addUndirectedEdge(0,1);
        g.addUndirectedEdge(1,3);
        g.addUndirectedEdge(3,2);
        g.addUndirectedEdge(2,0);
        g.addUndirectedEdge(2,7);
        g.addUndirectedEdge(7,6);
        g.addUndirectedEdge(3,6);
        g.addUndirectedEdge(4,3);
        g.addUndirectedEdge(8,4);
        g.addUndirectedEdge(8,1);
        g.addUndirectedEdge(4,5);
        g.addUndirectedEdge(5,6);
        System.out.println(g.path(0,5));
    }


    @Test
    public void test3(){
        Graph g = new Graph(7);
        g.addUndirectedEdge(0,1);
        g.addUndirectedEdge(0,2);
        g.addUndirectedEdge(1,3);
        g.addUndirectedEdge(1,4);
        g.addUndirectedEdge(2,5);
        g.addUndirectedEdge(2,6);
        System.out.println(g.path(0,6));
    }
}