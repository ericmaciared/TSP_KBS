import java.util.ArrayList;

public class AStar {
    private Routes routes;
    private String source;
    private String destination;

    public AStar(String source, String dest, Routes routes) {
        this.source = source;
        this.destination = dest;
        this.routes = routes;

        solve();
    }

    private void solve(){
        // A* Search Algorithm
        // 1.  Initialize the open list put the starting node on it (f at zero)
        ArrayList<Node> openList = new ArrayList<>();
        openList.add(new Node(source, 0));

        // 2.  Initialize the closed list
        ArrayList<Node> closeList = new ArrayList<>();

        // 3.  while the open list is not empty
        while (!openList.isEmpty()) {
            // a) find the node with the least f on the open list, call it "q"
            Node q = findNodeWithLeastF(openList);

            // b) pop q off the open list
            openList.remove(q);

            // c) generate q's 8 successors and set their parents to q
            ArrayList<Node> successors = generateSuccessors(q);

            // d) for each successor
            for (Node successor: successors) {
                //i) if successor is the goal, stop search
                if (successor.getName().equals(destination)) {
                    // TODO: Exit?
                }
                else {
                    //ii) else, compute both g and h for successor
                    successor.setF(calculateF(successor));

                    // iii) if a node with the same position as successor is in the OPEN list which has a lower f than successor, skip this successor

                    // iV) if a node with the same position as successor is in the CLOSED list which has a lower f than successor, skip this successor otherwise, add the node to the open list

                }
            }

            // e) push q on the closed list
            closeList.add(q);
        }
    }

    private int calculateF(Node successor) {
        // Calculate g -> movement cost from source to successor

        // Calculate h -> movement cost from successor to destination
        return 0;
    }

    /**
     * Generate successor list of node -> Generates all cities that have not yet visited
     * @param node
     * @return
     */
    private ArrayList<Node> generateSuccessors(Node node) {
        ArrayList<Node> successors = new ArrayList<Node>();

        for (City c: routes.getCities()) {
            // If a direct connection exists, it is a successor
            if (routes.getConnection(node.getName(), c.getName()) != null){
                successors.add(new Node(c.getName(), -1));
            }
        }

        return successors;
    }


    /**
     * Returns node with minimum F value in list
     * @param openList list of nodes
     * @return Node with minimum F value
     */
    private Node findNodeWithLeastF(ArrayList<Node> openList) {
        int minVal = Integer.MAX_VALUE;
        Node minimum = null;
        for (Node n: openList) {
            if (n.getF() < minVal) {
                minimum = n;
                minVal = n.getF();
            }
        }
        return minimum;
    }

    /**
     * Prints out TSP results of the algorithm conclusion
     */
    public void tsp() {

    }
}
