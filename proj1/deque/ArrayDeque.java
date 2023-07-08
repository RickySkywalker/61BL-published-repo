package deque;

public class ArrayDeque <T> implements Deque<T> {
    private int nextFirst;
    private T[] current;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        current = (T[])new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;

    }
    @Override
    public void addFirst(T item) {
        if (current.length - size != 1) { // if there are more than 1 empty ele;
            current[nextFirst] = item;
            nextFirst = minusOne(nextFirst);
        }
        else {
            resize(current.length * 4);
            current[nextFirst] = item;
            nextFirst = minusOne(nextFirst);
        }
        size += 1;
    }
    @Override
    public void addLast(T item) {
        if (current.length - size != 1) {
            current[nextLast] = item;
            nextLast = plusOne(nextLast);
        }
        else {
            resize(current.length * 4);
            current[nextLast] = item;
            nextLast = plusOne(nextLast);
        }
        size += 1;

    }

    private void resize(int arrayLength) {
        if (arrayLength > current.length) {
            T [] temp = (T[])new Object[arrayLength];
            if (nextFirst == nextLast) {
                for (int i = 0; i < nextLast; i++) {
                    temp[i] = current[i];
                }
                for (int i = nextFirst + 1; i < current.length; i++) {
                    temp[i + 3 * current.length] = current[i];
                }
                nextFirst += 3 * current.length;
                current = temp;
            }
        }
    }
    @Override
    public boolean isEmpty() {
        for (int i = 0; i < current.length; i++) {
            if (current[i] != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void printDeque() {
        System.out.println(current);
    }

    @Override
    public T removeFirst() {
        if (current.length > 8 ) {
            if (size / current.length < 0.25) {
                removeEmptySpots();
            }
        }
        T ans;
        int head = plusOne(nextFirst);
        ans = current[head];
        current[head] = null;
        nextFirst = head;
        size -= 1;
        return ans;

    }

    private void removeEmptySpots() {
        //assumes that the distance shortest distance between nextFirst and nextLast is more than 1
        T[] temp =  (T[])new Object[size+2];
        int index = nextFirst;
        int tempIndex = 0;
        nextFirst = 0;
        while (index != plusOne(nextLast)){
            temp[tempIndex] = current[index];
            index = plusOne(index);
            tempIndex++ ;
        }
        nextLast = size + 1;
        current = temp;

    }

    @Override
    public T removeLast() {
        if (current.length > 8 ) {
            if (size / current.length < 0.25) {
                removeEmptySpots();
            }
        }
        T ans;
        int tail = minusOne(nextLast);
        ans = current[tail];
        current[tail] = null;
        nextLast = tail;
        size -= 1;
        return ans;
    }

    private int plusOne(int index) {
        int after;
        if (index == current.length - 1) {
            after = 0;
        }
        else {
            after = index + 1;
        }
        return after;

    }

    private int minusOne(int index) {
        int previous;
        if (index == 0) {
            previous = current.length - 1;
        }
        else {
            previous = index - 1;
        }
        return previous;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        int tempIndex= plusOne(nextFirst);
        int amount = 0;
        while (tempIndex != nextLast){
            if(amount == index){
                return current[plusOne(nextFirst)+index];
            }
            amount++;
            tempIndex = plusOne(tempIndex);
        }
        return null;
        /*
        int head = plusOne(nextFirst);
        if (head + index < current.length) {
            return current[head + index];
        }
        else {
            int restIndex = head + index - current.length;
            return current[restIndex];
        }*/

    }
    @Override
    public boolean equals(Object o) {
        int thisHead = plusOne(nextFirst);
        o = new ArrayDeque<>();
        if (size != ((ArrayDeque<T>) o).size) {
            return false;
        }
        int otherHead = plusOne(((ArrayDeque<T>) o).nextFirst);
        while(thisHead!=nextLast){
            if (thisHead != otherHead) {
                return false;
            }
            thisHead = plusOne(thisHead);
            otherHead = plusOne(otherHead);
        }
        return true;
        /*
        if (current.toString()== o.toString()) {
            return true;
        }else{
            return false;
        }*/
    }
    @Override
    public String toString () {
        String str = "";
        int size = current.length;
        int head = plusOne(nextFirst);
        int index = head;
        while(index != minusOne(nextLast)){
            if (current[index] != null) {
                str += current[index];
            }
            str += " ";
            index = plusOne(index);
        }
        if (current[index] != null) {
            str += current[index];
        }
        return str;

    }
}