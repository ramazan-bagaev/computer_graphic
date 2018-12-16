public class Node {

    private String text;
    private int weight;

    private Node left;
    private Node right;

    private Node parent;

    public Node(char c, int weight){
        this.text = String.valueOf(c);
        this.weight = weight;
    }

    public Node(Node left, Node right){
        this.left = left;
        this.right = right;
        text = left.text + right.text;
        weight = left.weight + right.weight;
        left.setParent(this);
        right.setParent(this);
    }

    public String getText(){
        return text;
    }

    public Node getNode(int code){
        if (code == 0) return left;
        if (code == 1) return right;
        return null;
    }

    public int getWeight(){
        return text.length();
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public int getEdge(Node node){
        if (node == left) return 0;
        if (node == right) return 1;
        return -1;
    }
}
