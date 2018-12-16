import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HuffmanTree extends Tree {


    private HuffmanTree(Node root) {
        super(root);
    }

    public static HuffmanTree createHuffmanTree(String text){
        Map<Character, Integer> freqMap = getFreqMap(text);
        HuffmanTree hufTree = getTree(freqMap);
        return hufTree;
    }

    private static Map<Character, Integer> getFreqMap(String text){
        Map<Character, Integer> freqMap = new HashMap();
        for(char c: text.toCharArray()){
            int value = freqMap.getOrDefault(c, 0) + 1;
            freqMap.put(c, value);
        }
        return freqMap;
    }

    private static HuffmanTree getTree(Map<Character, Integer> freqMap){
        ArrayList<Node> nodes = new ArrayList<>();
        for(char c: freqMap.keySet()){
            nodes.add(new Node(c, freqMap.get(c)));
        }
        Node min1;
        Node min2;
        while (nodes.size() > 1){
            min1 = nodes.get(0);
            min2 = nodes.get(1);
            if (min1.getWeight() > min2.getWeight()){
                Node temp = min2;
                min2 = min1;
                min1 = temp;
            }
            for(int i = 2; i < nodes.size(); i++){
                Node cur = nodes.get(i);
                if (min1.getWeight() > cur.getWeight()){
                    min2 = min1;
                    min1 = cur;
                }
                else{
                    if (min2.getWeight() > cur.getWeight()){
                        min2 = cur;
                    }
                }
            }
            Node newNode = new Node(min1, min2);
            nodes.remove(min1);
            nodes.remove(min2);
            nodes.add(newNode);
        }
        return new HuffmanTree(nodes.get(0));
    }

    public Map<Character, String> getHuffmanCode(){
        Map<Character, String> result = new HashMap<>();
        ArrayList<Node> lists = getLists();
        for(Node node: lists){
            char c = node.getText().charAt(0);
            Node cur = node;
            String code = "";
            while (cur.getParent() != null){
                code = String.valueOf(cur.getParent().getEdge(cur)) + code;
                cur = cur.getParent();
            }
            result.put(c, code);
        }
        return result;
    }



}
