import java.util.*;

class Main{
    //task 1
    /*
    At first we start from A and go to the C
    1) A - C
    Next from C go the B , because we have a path between them
    2) C - B
    We cannot go from B to D(doesnt exist path) instead go to E (we already
    went to A and C)
    3) B - E
    From E we go to the G
    4) E - G
    and the last path from G to F beacause  already went paths that F have
    5) G - F

    and there is reversed step by step backtrack to  A
    finally, D will marked as last path because we did not covered it earlier


    From C we go to D
    6) C - D

   So, final order will be like this :
       A → C → B → E → G → F → D


    //task 2
    At first we start from A and add it to the queue
   1) A

   From A we visit all its neighbors: C, B, D
   2) A - C, B, D

   Next we process C, but all its neighbors (A, B, D) are already visited

   Then we process B, and add new nodes E and G
   3) B - E, G

   Next we process D, but all its neighbors are already visited

   Then we process E, and add F
    4) E - F

   Next we process G, but all its neighbors are already visited

   Finally we process F, and no new nodes are found


    Final order:
    A → C → B → D → E → G → F



    //task 3


        private final Map<String, List<String>> adj = new HashMap<>();

        public void addEdge(String v, String w) {
            adj.computeIfAbsent(v, k -> new ArrayList<>()).add(w);
        }

        public List<String> getNeighbors(String v) {
            return adj.getOrDefault(v, new ArrayList<>());
        }

        public Set<String> vertices() {
            return adj.keySet();
        }


    //dfs
    public static void dfs(Graph g, String start) {
        Set<String> visited = new HashSet<>();
        System.out.print("DFS: ");
        dfsHelper(g, start, visited);
        System.out.println();
    }

    private static void dfsHelper(Graph g, String v, Set<String> visited) {
        visited.add(v);
        System.out.print(v + " ");

        for (String neighbor : g.getNeighbors(v)) {
            if (!visited.contains(neighbor)) {
                dfsHelper(g, neighbor, visited);
            }
        }
    }


    //BFS
    public static void bfs(Graph g, String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();

        visited.add(start);
        q.add(start);

        System.out.print("BFS: ");

        while (!q.isEmpty()) {
            String v = q.poll();
            System.out.print(v + " ");

            for (String neighbor : g.getNeighbors(v)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    q.add(neighbor);
                }
            }
        }

        System.out.println();
    }



    // task 4
    We want to find the shortest path from Edinburgh to Dundee using Dijkstra’s algorithm.
    We start from Edinburgh with distance 0, while all other nodes (Glasgow, Stirling, Perth, Dundee)
    have distance infinity (not directed way). From Edinburgh we update the distances to
    its neighbors: Stirling = 50, Glasgow = 70, Perth = 100.
    The smallest distance is Stirling (50), so we move to Stirling.
    From Stirling we update: Glasgow = 50 + 50 = 100 (but already 70, so we keep 70),
    Perth = 50 + 40 = 90 (better than 100, so we update to 90). The next smallest is Glasgow (70),
    we visit it but no shorter paths are found. Then we choose Perth (90) and update Dundee = 90 + 60 = 150.
    Finally, we reach Dundee with total distance 150.
    Therefore, the shortest path is Edinburgh → Stirling → Perth → Dundee with total distance 150.


*/


    static final int INF = Integer.MAX_VALUE;
    public static void main(String[] arg){

        String[] nodes = {"Edinburgh", "Stirling", "Glasgow", "Perth", "Dundee"};

        int[][] graph = {
                {0,   50,  70, 100, INF},
                {50,   0,  50,  40, INF},
                {70,  50,   0, INF, INF},
                {100, 40, INF,   0,  60},
                {INF, INF, INF,  60,   0}
        };
        int n = nodes.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        int[] prev = new int[n];

        Arrays.fill(dist, INF);
        Arrays.fill(prev, -1);

        dist[0] = 0;

        for (int i = 0; i < n; i++) {

            int u = -1;

            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (graph[u][v] != INF) {
                    int newDist = dist[u] + graph[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        prev[v] = u;
                    }
                }
            }
        }

        System.out.println("Distance to Dundee = " + dist[4]);

        List<String> path = new ArrayList<>();
        for (int at = 4; at != -1; at = prev[at]) {
            path.add(nodes[at]);
        }
        Collections.reverse(path);
        System.out.println("Path: " + path);
    }
}