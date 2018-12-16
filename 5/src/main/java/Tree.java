import java.util.ArrayList;
import java.util.LinkedList;

public class Tree {

    protected Node root;

    public Tree(Node root){
        this.root = root;
    }

    public ArrayList<Node> getLists(){
        LinkedList<Node> pool = new LinkedList<>();
        pool.add(root);
        ArrayList<Node> lists = new ArrayList<>();
        while(pool.size() != 0){
            Node cur = pool.getLast();
            pool.remove(cur);
            Node left = cur.getNode(0);
            Node right = cur.getNode(1);
            if (left != null) pool.add(left);
            if (right != null) pool.add(right);
            if (left == null && right == null){
                lists.add(cur);
            }
        }
        return lists;
    }

}
