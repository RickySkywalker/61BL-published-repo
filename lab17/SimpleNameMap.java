import java.util.LinkedList;

public class SimpleNameMap {

    /* TODO: Instance variables here */
    public int size;
    public LinkedList<Entry>[] array;


    public SimpleNameMap() {
        // TODO: YOUR CODE HERE
        this.array = new LinkedList[26];
        this.size = 0;
        for (int i = 0; i < array.length; i++){
            array[i] = new LinkedList<Entry>();
        }
    }

    /* Returns the number of items contained in this map. */
    public int size() {
        // TODO: YOUR CODE HERE
        return size;
    }

    public static int hashCode(String key){
        return Math.floorMod(key.hashCode(), key.length());
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
        // TODO: YOUR CODE HERE
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

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public String get(String key) {
        // TODO: YOUR CODE HERE
        int hashCodeOfKey = hashCode(key);
        int arrayIndex = hashCodeOfKey%array.length;
        LinkedList<Entry> pointedLinkedList = array[arrayIndex];
        for (Entry curr : pointedLinkedList){
            if (curr.key.equals(key)){
                return curr.value;
            }
        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(String key, String value) {
        // TODO: YOUR CODE HERE
        resize();
        int hashCodeOfKey = hashCode(key);
        int arrayIndex = hashCodeOfKey % array.length;
        LinkedList<Entry> thisLL = array[arrayIndex];
        Entry newEntryToAdd = new Entry(key, value);
        thisLL.addLast(newEntryToAdd);
        size++;
    }

    public void resize(){
        double loadFactor = (double) this.size() / (double) array.length;


        if (loadFactor > 0.7){

            //reinitialize the new array
            LinkedList<Entry>[] newArray = new LinkedList[array.length*2];
            for (int i = 0; i < newArray.length; i++){
                newArray[i] = new LinkedList<Entry>();
            }


            for (LinkedList<Entry> currLs : array){
                for (Entry currEntry : currLs){
                    int currBucketIndex = currEntry.key.hashCode() % newArray.length;
                    newArray[currBucketIndex].addLast(currEntry);
                }
            }

            array = newArray;
        }
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public String remove(String key) {
        // TODO: YOUR CODE HERE
        int hashCodeOfKey = hashCode(key);
        int arrayIndex = hashCodeOfKey % array.length;
        LinkedList<Entry> pointedLinkedList = array[arrayIndex];
        for (int i = 0; i < pointedLinkedList.size(); i++){
            Entry curr = pointedLinkedList.get(i);
            if (curr.key.equals(key)){
                pointedLinkedList.remove(i);
                return curr.value;
            }
        }
        return null;
    }

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}