public class Node {
    private String name;
    private int f;

    public Node(String name, int f) {
        this.name = name;
        this.f = f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
}
