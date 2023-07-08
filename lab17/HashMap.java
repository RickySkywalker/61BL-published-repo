import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V>, Iterable<K> {


    public int size;
    public LinkedList<Entry>[] array;
    public double maxLoadFactor = 0.75;

    public HashMap(){
        this.array = new LinkedList[16];
        this.size = 0;
        for (int i = 0; i < array.length; i++){
            array[i] = new LinkedList<Entry>();
        }
    }

    public HashMap(int initialCapacity){
        this.array = new LinkedList[initialCapacity];
        this.size = 0;
        for (int i = 0; i < array.length; i++){
            array[i] = new LinkedList<Entry>();
        }
    }

    /* Creates a new hash map with INITIALCAPACITY and LOADFACTOR. */
    HashMap(int initialCapacity, double loadFactor){
        this.array = new LinkedList[initialCapacity];
        this.size = 0;
        for (int i = 0; i < array.length; i++){
            array[i] = new LinkedList<Entry>();
        }
        this.maxLoadFactor = loadFactor;
    }

    public int capacity(){
        return array.length;
    }

    public void clear(){
        this.array = new LinkedList[10];
        this.size = 0;
        for (int i = 0; i < array.length; i++){
            array[i] = new LinkedList<Entry>();
        }
    }

    public boolean containsKey(K key){
        int hashCodeOfKey = hashCode(key);
        int arrayIndex = hashCodeOfKey%array.length;
        LinkedList<Entry> pointedLinkedList = array[arrayIndex];
        for (Entry curr : pointedLinkedList){
            if (curr.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    public int hashCode(K key){
        return Math.abs(key.hashCode());
    }

    public V get(K key){
        int hashCodeOfKey = hashCode(key);
        int arrayIndex = hashCodeOfKey%array.length;
        LinkedList<Entry> pointedLinkedList = array[arrayIndex];
        for (Entry curr : pointedLinkedList){
            if (curr.key.equals(key)){
                return (V)curr.value;
            }
        }
        return null;
    }

    public void put(K key, V value){
        resize();
        int hashCodeOfKey = hashCode(key);
        int arrayIndex = hashCodeOfKey % array.length;
        LinkedList<Entry> thisLL = array[arrayIndex];
        for (Entry curr : thisLL){
            if(curr.key.equals(key)){
                curr.value = value;
                return;
            }
        }
        Entry newEntryToAdd = new Entry(key, value);
        thisLL.addLast(newEntryToAdd);
        size++;
    }


    public void resize(){

        double loadFactor = (double) (size + 1) / (double) array.length;

        if (loadFactor > maxLoadFactor){

            //reinitialize the new array
            LinkedList<Entry>[] newArray = new LinkedList[array.length*2];
            for (int i = 0; i < newArray.length; i++){
                newArray[i] = new LinkedList<Entry>();
            }


            for (LinkedList<Entry> currLs : array){
                for (Entry currEntry : currLs){
                    int currBucketIndex = hashCode((K)currEntry.key) % newArray.length;
                    newArray[currBucketIndex].addLast(currEntry);
                }
            }

            array = newArray;
        }
    }

    public boolean remove(K key, V value){
        int hashCodeOfKey = hashCode(key);
        int arrayIndex = hashCodeOfKey % array.length;
        LinkedList<Entry> pointedLinkedList = array[arrayIndex];
        for (int i = 0; i < pointedLinkedList.size(); i++){
            Entry curr = pointedLinkedList.get(i);
            if (curr.key.equals(key)){
                pointedLinkedList.remove(i);
                return true;
            }
        }
        return false;
    }

    public V remove(K key){
        int hashCodeOfKey = hashCode(key);
        int arrayIndex = hashCodeOfKey % array.length;
        LinkedList<Entry> pointedLinkedList = array[arrayIndex];
        for (int i = 0; i < pointedLinkedList.size(); i++){
            Entry curr = pointedLinkedList.get(i);
            if (curr.key.equals(key)){
                pointedLinkedList.remove(i);
                size--;
                return (V)curr.value;
            }
        }
        return null;
    }

    public int size(){
        return size;
    }

    public Iterator<K> iterator(){
        return null;
    }


    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(HashMap.Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof HashMap.Entry
                    && key.equals(((HashMap.Entry) other).key)
                    && value.equals(((HashMap.Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }



    class HashMapIterator implements Iterator<K>{

        public HashMapIterator(){

        }

        @Override
        public boolean hasNext(){
            return false;
        }

        @Override
        public K next(){
            return null;
        }

    }


}
