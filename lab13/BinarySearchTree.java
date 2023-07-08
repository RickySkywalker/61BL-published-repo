import java.util.ArrayList;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. */
    public BinarySearchTree() {
        // TODO: YOUR CODE HERE
        root =null;
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        // TODO: YOUR CODE HERE
        this.root = root;
    }


    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        // TODO: YOUR CODE HERE
        if (root != null) {
            return containsHelper(key, root);
        }else{
            return false;
        }
    }


    public boolean containsHelper(T key, TreeNode node){
        if (node.item.compareTo(key) == 0){
            return true;
        }else if (node.item.compareTo(key) > 0 && node.left != null){
            return containsHelper(key, node.left);
        }else if (node.item.compareTo(key) < 0 && node.right != null){
            return containsHelper(key, node.right);
        }else{
            return  false;
        }
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        // TODO: YOUR CODE HERE
        if (!this.contains(key)) {
            if (root == null) {
                root = new TreeNode(key);
            } else {
                TreeNode current = addHelper(key, root);
                if (current.item.compareTo(key) > 0) {
                    current.left = new TreeNode(key);
                } else if (current.item.compareTo(key) < 0) {
                    current.right = new TreeNode(key);
                }
            }
        }
    }

    public TreeNode addHelper(T key, TreeNode node){
        if (node.item.compareTo(key) > 0 && node.left == null){
            return node;
        }else if (node.item.compareTo(key) < 0 && node.right == null){
            return node;
        }else if (node.item.compareTo(key) > 0 && node.left != null){
            return addHelper(key, node.left);
        }else if (node.item.compareTo(key) < 0 && node.right != null){
            return addHelper(key, node.right);
        }
        return null;
    }

    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}