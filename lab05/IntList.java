/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author WANG, Ruida
 */

public class IntList {

    /** The integer stored by this node. */
    public int item;
    /** The next node in this IntList. */
    public IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }

    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3 */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        //TODO: YOUR CODE HERE
        int thingsToReturn = 0;
        int size = 0;
        IntList thing = this;
        while ( thing.next != null){
            thing = thing.next;
            size += 1;
        }
        if(position<=size && position>=0) {

            IntList anotherThing = this;
            for (int i = 0; i < position; i++) {
                anotherThing = anotherThing.next;
            }

            thingsToReturn = anotherThing.item;
            return thingsToReturn;
        }else {
            throw new IllegalArgumentException();
        }


    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        //TODO: YOUR CODE HERE
        int size = 0;
        IntList thing = this;
        while (thing.next != null){
            thing = thing.next;
            size += 1;
        }
        size++;
        String thingsToReturn = new String();
        IntList current = this;
        for(int i = 0; i<size-1; i++){
            thingsToReturn = thingsToReturn + current.item + " ";
            current = current.next;
        }

        thingsToReturn = thingsToReturn + this.get(size-1);

        return thingsToReturn;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * IntList, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parenthesis. An example of this is on line 84.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntList)) {
            return false;
        }
        IntList otherLst = (IntList) obj;

        //TODO: YOUR CODE HERE
        boolean thingsToReturn = true;

        int sizeOfA = this.size(), sizeOfB = otherLst.size();

        if (sizeOfA != sizeOfB){
            thingsToReturn = false;
        }else {
            for(int i = 0; i<sizeOfA; i++){
                if(this.get(i) != otherLst.get(i)){
                    thingsToReturn = false;
                    break;
                }else{
                    continue;
                }
            }
        }

        return thingsToReturn;

    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        //TODO: YOUR CODE HERE
        IntList current = this;
        while (current.next != null){
            current = current.next;
        }
        current.next = new IntList(value, null);
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        //TODO: YOUR CODE HERE
        int size = this.size();
        int smallest = this.get(0);
        for (int i = 0; i < size; i++){
            if (this.get(i)<smallest){
                smallest = this.get(i);
            }
        }
        return smallest;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        //TODO: YOUR CODE HERE
        int squardSum = 0;
        int size = this.size();
        for (int i = 0; i<size; i++){
            squardSum += this.get(i) * this.get(i);
        }
        return squardSum;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        //TODO: YOUR CODE HERE
        if (A!=null) {
            if (B != null) {
                int sizeB = B.size();
                for (int i = 0; i < sizeB; i++) {
                    A.add(B.get(i));
                }
            }
            return A;
        }else {
            if (B != null) {
                return B;
            }else {
                return null;
            }
        }
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
     public static IntList catenate(IntList A, IntList B) {
        //TODO: YOUR CODE HERE
         IntList thingsToReturn = null;
         if(A != null){
             if(B != null){
                 thingsToReturn = new IntList(A.get(0), null);
                 int sizeA = A.size(), sizeB = B.size();
                 for (int i = 1; i<sizeA; i++){
                     thingsToReturn.add(A.get(i));
                 }
                 for (int i = 0; i<sizeB; i++){
                     thingsToReturn.add(B.get(i));
                 }
             }else{
                 thingsToReturn = A;
             }
         }else if (B != null){
             thingsToReturn = B;
         }else{
             thingsToReturn = null;
         }
         return thingsToReturn;
     }

     private int size(){
         if (this != null) {
             int size = 0;
             IntList current = this;
             while (current.next != null) {
                 size += 1;
                 current = current.next;
             }
             return size + 1;
         } else{
             return 0;
         }
     }
}