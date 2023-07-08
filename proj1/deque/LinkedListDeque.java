package deque;

public class LinkedListDeque<T> implements Deque<T>{
    private class DequeNode {
        public T item;
        public DequeNode next;
        public DequeNode previous;

        public DequeNode(DequeNode previous, T item, DequeNode next){
            this.item = item;
            this.next = next;
            this.previous = previous;
        }

        @Override
        public boolean equals(Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DequeNode that = (DequeNode) o;
            return item == that.item;
        }


    }


    //The first item (if it exists is at sentinel.next
    private DequeNode sentinel;
    private int size;


    @SuppressWarnings("unchecked")
    public T getRecursive(int index){
        if (index < size) {
            int i = 0;
            DequeNode current = sentinel.next;
            while (i < index) {
                current = current.next;
                i++;
            }
            T thingToReturn = (T) current.item;
            return thingToReturn;
        }
        return null;
    }

    //creation of the empty Deque
    public LinkedListDeque(){
        sentinel = new DequeNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }


    @Override
    public String toString() {
        DequeNode l = sentinel.next;
        String result = "";

        while (l != sentinel) {
            result += l + " ";
            l = l.next;
        }

        return result.trim();
    }

    public int size(){
        return size;
    }


    //used to add from the first of the deque
    @SuppressWarnings("unchecked")
    public void addFirst(T x){
        DequeNode otherThings = sentinel.next;
        sentinel.next = new DequeNode(sentinel, x, otherThings);
        otherThings.previous = sentinel.next;
        size++;
    }

    //Used to add the from the last of the deque
    @SuppressWarnings("unchecked")
    public void addLast(T x){
        DequeNode otherThings = sentinel.previous;
        sentinel.previous = new DequeNode(otherThings, x, sentinel);
        otherThings.next = sentinel.previous;
        size++;
    }


    //Used to test is the Deque empty
    @Override
    public boolean isEmpty(){
        if (sentinel.next == sentinel && sentinel.previous == sentinel){
            return true;
        }else{
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int index){
        if (index < size) {
            int i = 0;
            DequeNode current = sentinel.next;
            while (i < index) {
                current = current.next;
                i++;
            }
            T thingToReturn = (T) current.item;
            return thingToReturn;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public T removeFirst(){
        if (size != 0) {
            T returnThing = (T) sentinel.next.item;
            DequeNode temp = sentinel.next.next;
            sentinel.next = temp;
            temp.previous = sentinel;
            size--;
            return  returnThing;
        }else{
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public T removeLast(){
        if (size != 0){
            T returnThing = (T) sentinel.previous.item;
            DequeNode temp = sentinel.previous.previous;
            sentinel.previous = temp;
            temp.next = sentinel;
            size--;
            return returnThing;
        }else{
            return null;
        }
    }

    public void printDeque(){
        if(size != 1){
            DequeNode current = sentinel.next;
            while (current.next != sentinel){
                System.out.print(current.item + " ");
                current = current.next;
            }
            System.out.print(current.item);
            System.out.println("");
        }
    }


    private boolean contain(T x){
        boolean booleanToReturn = false;
        DequeNode current = sentinel.next;
        while(current.next != sentinel){
            if(current.item == x){
                booleanToReturn = true;
            }
        }
        return booleanToReturn;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o){

        boolean thingToReturn = true;
        if (o.getClass() == this.getClass()){
            LinkedListDeque a1 = (LinkedListDeque) o;
            if (a1.size() == this.size()){
                int length = this.size();
                for (int i = 0; i < length; i++){
                    if(!a1.get(i).equals(this.get(i))){
                        thingToReturn = false;
                        break;
                    }
                }
            }else{
                thingToReturn = false;
            }

        }else{
            thingToReturn = false;
        }
        return thingToReturn;
    }

}
