import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryTreeTest {

    @Test
    public void heightTest(){
        BinaryTree t;
        t = new BinaryTree();
        assertEquals(0, t.height());
        t.sampleTree1();
        assertEquals(2, t.height());
        t.sampleTree3();
        assertEquals(4,t.height());
    }

    @Test
    public void isBalancedTest(){
        BinaryTree t;
        t = new BinaryTree();
        assertTrue(t.isCompletelyBalanced());
        t.sampleTree1();
        assertTrue((t.isCompletelyBalanced()));
        t.sampleTree3();
        assertFalse(t.isCompletelyBalanced());
    }

    @Test
    public void fibTreeTest(){
        BinaryTree t = BinaryTree.fibTree(6);
        assertEquals(8, t.getRoot().getItem());
    }

}