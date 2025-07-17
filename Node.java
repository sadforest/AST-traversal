import java.util.*;

public class Node {

    public String data;
    public Node parent;
    public List<Node> children;

    private List<Node> nodeList;

    public Node(String data) {
        this.data = data;
        this.children = new LinkedList<Node>();
        this.nodeList = new LinkedList<Node>();
        this.nodeList.add(this);
    }

    public Node addChild(String child) {
        Node childNode = new Node(child);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    private void registerChildForSearch(Node node) {
        nodeList.add(node);
        if (parent != null)
            parent.registerChildForSearch(node);
    }

    public Node findTreeNode(String data) {
        for (Node node : this.nodeList) {
            if (node.data.equals(data))
                return node;
        }

        return null;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

}
