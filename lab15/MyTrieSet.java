import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61BL{

    public class Node{
        public LinkedList<Node> map;
        public boolean isKey;
        public char inside;

        public Node(){
            this.map = new LinkedList<>();
        }

        public Node(char c, boolean isKey){
            this.inside = c;
            this.isKey = isKey;
            this.map = new LinkedList<Node>();
        }

        public boolean mapContainsKey(char c){
            for (Node current : map){
                if (c == current.inside){
                    return true;
                }
            }
            return false;
        }

        public void mapPut(char c){
            Node newNode = new Node(c, false);
            map.addLast(newNode);
        }

        public Node mapGet(char c){
            for(Node current : map){
                if (current.inside == c){
                    return current;
                }
            }
            System.out.println("No such map");
            return null;
        }

        public LinkedList<String> prefixHelper(LinkedList<String> currentList, String currentString){
            if (this.isKey && this.map.isEmpty()){
                currentString = currentString + this.inside;
                currentList.addLast(currentString);
                return currentList;
            }
            if (this.isKey){
                currentString = currentString + this.inside;
                for(Node thisNode : this.map){
                    LinkedList<String> listOfThis = thisNode.prefixHelper(currentList, currentString);
                }
                currentList.addLast(currentString);
                return currentList;
            }else{
                currentString = currentString + this.inside;
                for(Node thisNode : this.map){
                    thisNode.prefixHelper(currentList, currentString);
                }
                return  currentList;
            }
        }

    }

    public Node root;

    public MyTrieSet(){
        root = new Node();
    }

    @Override
    public void clear(){
        root = new Node();
    }

    @Override
    public boolean contains(String key){
        if (key == null || key.length() < 1){
            return false;
        }

        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++){
            char c = key.charAt(i);
            if (!curr.mapContainsKey(c)){
                return false;
            }
            curr = curr.mapGet(c);
        }
        if (curr.isKey == true){
            return true;
        }
        return false;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.mapContainsKey(c)) {
                curr.mapPut(c);
            }
            curr = curr.mapGet(c);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix){
        List<String> lsToReturn = new LinkedList<String>();
        List<Node> helperList = new LinkedList<>();
        if (prefix == null || prefix.length() < 1) {
            return null;
        }
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            if (curr.mapContainsKey(c)){
                curr = curr.mapGet(c);
            }
        }
        lsToReturn = curr.prefixHelper(new LinkedList<String>(), prefix.substring(0,prefix.length()-1));
        return lsToReturn;
    }



    @Override
    public String longestPrefixOf(String key){
        throw new UnsupportedOperationException();
    }


    public static void main(String[] args) {
        MyTrieSet t = new MyTrieSet();
        t.add("hello");
        t.add("hi");
        t.add("help");
        t.add("zebra");
    }

}
