package tech.antee.cba.tasks.huffman;

public class Node implements Comparable<Node> {

    public Node left;

    public Node right;

    public Character symbol;

    public int priority;

    public Node(Character symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    public Node(Character symbol, int priority, Node left, Node right) {
        this.symbol = symbol;
        this.priority = priority;
        this.left = left;
        this.right = right;
    }

    // Begin with left, to the right
    public String nodeCode(Character symbol, String code) {
        if (this.symbol == symbol) {
            return  code;
        } else {
            if (left != null) {
                String childCode = left.nodeCode(symbol, code + 0);
                if (childCode != null) {
                    return childCode;
                }
            }
            if (right != null) {
                String childCode = right.nodeCode(symbol, code + 1);
                if (childCode != null) {
                    return childCode;
                }
            }
        }
        return null;
    }

    @Override
    public int compareTo(Node o) {
        return o.priority - priority;
    }

}
