import java.util.ArrayList;

public class AStar {
    private Routes routes;
    private String source;
    private String destination;
    private ArrayList<Node> path;

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
        Node start = new Node(source, 0, null);
        start.setF(calculateF(start));
        openList.add(start);

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
                if (successor.getName().equalsIgnoreCase(destination)) {
                    closeList.add(successor);
                    openList = new ArrayList<>();
                    break;
                }
                else {
                    //ii) else, compute both g and h for successor
                    successor.setF(calculateF(successor));

                    // iii) if a node with the same position as successor is in the OPEN list which has a lower f than successor, skip this successor
                    boolean found = false;
                    for (Node n: openList){
                        if (n.getName().equalsIgnoreCase(successor.getName()) && n.getF() <= successor.getF()) {
                            found = true;
                            break;
                        }
                    }
                    if (found) continue;

                    // iV) if a node with the same position as successor is in the CLOSED list which has a lower f than successor, skip this successor otherwise, add the node to the open list
                    for (Node n: closeList){
                        if (n.getName().equalsIgnoreCase(successor.getName()) && n.getF() <= successor.getF()) {
                            found = true;
                            break;
                        }
                    }
                    if (found) continue;
                    openList.add(successor);
                }
            }

            // e) push q on the closed list
            closeList.add(q);
        }

        this.path = closeList;
    }

    private int calculateF(Node successor) {
        // Calculate g -> movement cost from source to successor
        int g = 0;
        if (successor.getParent() != null){
            Node parent = successor.getParent();
            while (parent != null){
                g += parent.getF();
                parent = parent.getParent();
            }
        }

        // Calculate h -> movement cost from successor to destination
        int h = calculateEuclideanDistance(routes.getCity(successor.getName()), routes.getCity(destination));

        return g + h;
    }

    /**
     * Calculates euclidean distance between two cities' coordinaties
     * @param city1 City 1
     * @param city2 City 2
     * @return Distance in m between two cities' coordinates
     */
    private int calculateEuclideanDistance(City city1, City city2){
        double dist = Math.sqrt(
                        Math.pow(city1.getLatitude() - city2.getLatitude(), 2) +
                        Math.pow(city1.getLongitude() - city2.getLongitude(), 2)
        ) * 111139; // 111139 is constant value to convert from pythagorean hypothenuse of coordinates to m.

        return (int) dist;
    }

    /**
     * Generate successor list of node -> Generates all cities that have not yet visited
     * @param node Node to generate successors from
     * @return Returns array of connecting nodes
     */
    private ArrayList<Node> generateSuccessors(Node node) {
        ArrayList<Node> successors = new ArrayList<>();

        for (City c: routes.getCities()) {
            // If a direct connection exists, it is a successor
            if (routes.getConnection(node.getName(), c.getName()) != null){
                // Create new node, F will be calculated later, and set parent to current node
                Node successor = new Node(c.getName(), Integer.MAX_VALUE, node);
                successors.add(successor);
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
        System.out.println("A* Path from " + source.toUpperCase() + " to " + destination.toUpperCase());
        System.out.print(source.toUpperCase() + " ");
        for (int i = path.size()-1; i >= 1; i--) {
            System.out.print(path.get(i).getName().toUpperCase() + " ");
        }
        System.out.println();
    }
}
