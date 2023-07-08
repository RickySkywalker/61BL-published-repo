import java.util.ArrayList;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    // TODO: YOUR CODE HERE (no code should be needed here if not 
    // implementing the more optimized version)

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);//indicates that the first item (i.e. the contents.get(0) is left null)
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    public ArrayList getContent(){
        return contents;
    }



    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        // TODO: YOUR CODE HERE
        return 2*index;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        // TODO: YOUR CODE HERE
        return 2 * index + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        // TODO: YOUR CODE HERE
        return index/2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        // TODO: YOUR CODE HERE
        E val1 = contents.get(index1);
        E val2 = contents.get(index2);
        if (val1 != null && val2 != null) {
            if (val1.compareTo(val2) >= 0) {
                return index2;
            } else {
                return index1;
            }
        }else if (val1 == null && val2 != null){
            return index2;
        }else if (val1 != null){
            return index1;
        }
        return 0;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        // TODO: YOUR CODE HERE
        return contents.get(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        // TODO: YOUR CODE HERE
        E currVal = contents.get(index);
        E parentVal = contents.get(getParentOf(index));
        if (currVal.compareTo(parentVal) <= 0 ){
            contents.remove(index);
            contents.add(index, parentVal);
            contents.remove(getParentOf(index));
            contents.add(getParentOf(index), currVal);
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        // TODO: YOUR CODE HERE
        E currVal = contents.get(index);
        int rightIndex = getRightOf(index), leftIndex = getLeftOf(index);
        int min = min(rightIndex, leftIndex);
        E minVal = contents.get(min);
        swap(index, min);
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        // TODO: YOUR CODE HERE
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        // TODO: YOUR CODE HERE
        if (! contents.contains(element)){
            contents.add(size+1, element);
            size++;
            int currIndex = size;
            int parentIndex = getParentOf(currIndex);
            E curr = contents.get(currIndex);
            E parent = contents.get(parentIndex);
            if (curr != null && parent != null) {
                while (curr.compareTo(parent) < 0 && currIndex != 1) {
                    bubbleUp(currIndex);
                    currIndex = parentIndex;
                    parentIndex = getParentOf(currIndex);
                    curr = contents.get(currIndex);
                    parent = contents.get(parentIndex);
                    if (parent == null){
                        break;
                    }
                }
            }

        }else{
            throw new IllegalArgumentException();
        }
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        // TODO: YOUR CODE HERE
        E thingToReturn = contents.get(1);
        swap(1, size);
        contents.remove(size);
        size--;
        int currIndex = 1;
        int leftChildIndex = getLeftOf(currIndex);
        int rightChildIndex = getRightOf(currIndex);
        if (rightChildIndex > contents.size()-1 || leftChildIndex > contents.size()-1){
            return thingToReturn;
        }
        int nextIndex = min(getLeftOf(currIndex), getRightOf(currIndex));
        E curr = contents.get(currIndex);
        E next = contents.get(nextIndex);
        while(curr.compareTo(next) > 0){
            swap(currIndex, nextIndex);
            currIndex = nextIndex;
            leftChildIndex = getLeftOf(currIndex);
            rightChildIndex = getRightOf(currIndex);
            if (rightChildIndex > contents.size()-1 || leftChildIndex > contents.size()-1){
                break;
            }
            nextIndex = min(leftChildIndex, rightChildIndex);
            curr = contents.get(currIndex);
            next = contents.get(nextIndex);
        }
        return thingToReturn;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        // TODO: YOUR CODE HERE
        /*if (contents.contains(element)){
            ArrayList<E> newContents;
            contents.remove(element);
            newContents = contents;
            contents = new ArrayList<>();
            contents.add(null);
            size = 0;
            for (int i = 1; i < newContents.size(); i++){
                E curr = newContents.get(i);
                insert(curr);
            }
            insert(element);
        }else{
            throw new NoSuchElementException();
        }*/
        if (!contains(element)) {
            throw new NoSuchElementException();
        }
        int targetIndex = 0;
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i) != null) {
                if(contents.get(i).equals(element)){
                    targetIndex = i;
                    setElement(targetIndex,element);
                    bubbleUp(targetIndex);
                    int newIndex = contents.indexOf(element);
                    bubbleDown(newIndex);
                    break;

                }
            }
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        // TODO: YOUR CODE HERE
        return contents.contains(element);
    }
}
