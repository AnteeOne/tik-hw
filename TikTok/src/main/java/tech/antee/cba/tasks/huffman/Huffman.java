package tech.antee.cba.tasks.huffman;

import java.util.ArrayList;
import java.util.Collections;

public class Huffman {

    public Node encodeText(ArrayList<Node> nodes) {
        try {
            while (nodes.size() >= 2) {

                Collections.sort(nodes);
                int lastNodePosition = nodes.size() - 1;

                Node childLeft = nodes.remove(lastNodePosition--);
                Node childRight = nodes.remove(lastNodePosition);
                Node nodeParent = new Node(
                        null,
                        childRight.priority + childLeft.priority,
                        childLeft,
                        childRight
                );
                nodes.add(nodeParent);
            }
            return nodes.get(0);

        } catch (IndexOutOfBoundsException e) {
            return new Node(null, 0);
        }
    }

    public String decodeText(String textEncoded, Node decodeTree) {
        try {
            StringBuilder result = new StringBuilder();
            Node node = decodeTree;
            int index = 0;
            while (index < textEncoded.length()) {
                if (textEncoded.charAt(index) == '0') {
                    node = node.left;
                } else {
                    node = node.right;
                }
                if (node.symbol != null) {
                    result.append(node.symbol);
                    node = decodeTree;
                }
                index++;
            }
            return result.toString();
        } catch (Exception e) {
            return "";
        }
    }
}


