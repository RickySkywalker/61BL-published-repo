public class UnionFind {

    /* TODO: Add instance variables here. */
    public static int[] UnionFind;
    public int size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        this.size = N;
        UnionFind = new int[N];
        for (int i = 0; i < N; i++){
            UnionFind[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        int root = findRoot(v);
        return -UnionFind[root];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return UnionFind[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int v1Root = findRoot(v1), v2Root = findRoot(v2);
        if (v1Root == v2Root){
            return true;
        }else{
            return false;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if (v>=0 && v<this.size) {
            int vRoot = findRoot(v);
            int current = v;
            while (current != vRoot){
                int currentParent = UnionFind[current];
                UnionFind[current] = vRoot;
                current = currentParent;
            }
            return vRoot;
        }else{
            throw new IllegalArgumentException();
        }

    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE

        if(v1>=0 && v1<this.size && v2>=0 && v2<this.size) {
            int v1Root = findRoot(v1), v2Root = findRoot(v2);
            if (v1Root != v2Root) {
                if (-UnionFind[v1Root] > -UnionFind[v2Root]) {
                    UnionFind[v1Root] += UnionFind[v2Root];
                    UnionFind[v2Root] = v1Root;
                } else {
                    UnionFind[v2Root] += UnionFind[v1Root];
                    UnionFind[v1Root] = v2Root;
                }
            }
        }

    }

    public static int findRoot(int current){
        if(UnionFind[current] < 0){
            return current;
        }else{
            return findRoot(UnionFind[current]);
        }
    }
}
