package spell;

public class Node implements INode {
    private int count;
    private Node[] children;

    public Node () {
        count = 0;
        children = new Node[26];
    }
    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public Node[] getChildren() { //TODO: consider returning INode[] instead of Node[]
        return children;
    }
}
