import java.util.*;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    @SuppressWarnings("unchecked")
    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        LinkedList<Edge> currLs = adjLists[v1];
        for (Edge curr : currLs){
            if (curr.to == v2){
                curr.weight = weight;
                return;
            }
        }
        currLs.addLast(new Edge(v1, v2, weight));
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        // TODO: YOUR CODE HERE
        LinkedList<Edge> currAdjLs = adjLists[from];
        for (Edge curr : currAdjLs){
            if (curr.from == from && curr.to == to){
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        // TODO: YOUR CODE HERE
        LinkedList<Integer> returnLs = new LinkedList<>();
        LinkedList<Edge> vNeighbors = adjLists[v];
        for (Edge curr : vNeighbors){
            returnLs.addLast(curr.to);
        }
        return returnLs;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        // TODO: YOUR CODE HERE
        int n = 0;
        for (LinkedList<Edge> currLs : adjLists){
            for (Edge currEdge : currLs){
                if (currEdge.to == v){
                    n++;
                }
            }
        }
        return n;
    }

    private LinkedList<Integer> inDegreeList(int v){
        LinkedList<Integer> n = new LinkedList<>();
        for (LinkedList<Edge> currLs : adjLists){
            for (Edge currEdge : currLs){
                if (currEdge.to == v){
                    n.addLast(currEdge.from);
                }
            }
        }
        return n;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>(neighbors(curr));
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }

        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        // TODO: YOUR CODE HERE
        if (start != stop) {
            ArrayList<Integer> dfsResult = (ArrayList<Integer>) dfs(start);
            return dfsResult.contains(stop);
        }
        return true;
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        // TODO: YOUR CODE HERE
        LinkedList<Integer> returnLs = new LinkedList<>();
        if (pathExists(start, stop)){
            returnLs.addLast(stop);
            if (start != stop) {
                LinkedList<Integer> helperLs = new LinkedList<>();
                Iterator<Integer> iter = new DFSIterator(start);
                while (iter.hasNext()) {
                    int next = iter.next();
                    if (next != stop) {
                        helperLs.addLast(next);
                    } else {
                        break;
                    }
                }

                helperLs.addLast(stop);
                LinkedList<Integer> inDegreeLs = inDegreeList(stop);
                while (!inDegreeLs.contains(start)) {
                    int parent = inDegreeLs.get(0);
                    for (int curr : inDegreeLs) {
                        if (helperLs.contains(curr)) {
                            parent = curr;
                            break;
                        }
                    }
                    inDegreeLs = inDegreeList(parent);
                    returnLs.addFirst(parent);
                }

                returnLs.addFirst(start);
            }
        }
        return returnLs;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private final LinkedList<Integer> fringe;
        private final LinkedList<Integer> visited = new LinkedList<>();

        // TODO: Instance variables here!

        TopologicalIterator() {
            fringe = new LinkedList<>();
            // TODO: YOUR CODE HERE
            for (int i = 0; i < vertexCount; i++){
                int[] currentInDegree = new int[vertexCount];
                currentInDegree[i] = inDegree(i);
                if (inDegree(i) == 0){
                    fringe.add(i);
                }
            }
        }

        public boolean hasNext() {
            // TODO: YOUR CODE HERE
            if (!fringe.isEmpty()){
                for (int curr : fringe){
                    if (!visited.contains(curr)){
                        return true;
                    }
                }
            }
            return false;
        }

        public Integer next() {
            // TODO: YOUR CODE HERE
            int curr = fringe.remove(0);
            while(visited.contains(curr)){
                curr = fringe.remove(0);
            }
            LinkedList<Integer> lst = new LinkedList<>();
            for (int i : neighbors(curr)) {
                lst.addLast(i);
            }
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.addLast(e);
            }
            visited.add(curr);
            return curr;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    public void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    public void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    public void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    public void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    public void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    public void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    public void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();
    }
}