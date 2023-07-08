public class ModCounter {

    private int myCount;

    public ModCounter() {
        myCount = 0;
    }

    public void increment() {
        myCount++;
    }

    public void reset() {
        myCount = 0;
    }

    public int value() {
        return myCount;
    }

}
