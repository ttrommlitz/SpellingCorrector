package spell;

public class Trie implements ITrie {
    private Node root;
    private int wordCount;
    private int nodeCount;

    public Trie() {
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }
    @Override
    public void add(String word) {
        /*
        increment wordCount
        -Convert word to lowercase
        -process each character, one at a time (loop)
        -start inserting at the root
        -array index = currLetter - 'a'
        -char letter = (char)('a' + index)

        (loop)
        -find child in current node that corresponds to currLetter
        -See if there is a node there, if not create a new one at currentNode.children[index] (increment nodeCount)
        -set currentNode to currentNode.children[index]
        -set currLetter to next letter in word

        -at end of loop, increment wordCount for currNode
         */
        wordCount++;
        word = word.toLowerCase();
        Node currNode = root;
        for (int i = 0; i < word.length(); i++) {
            char currLetter = word.charAt(i);
            int index = currLetter - 'a';
            if (currNode.getChildren()[index] == null) {
                currNode.getChildren()[index] = new Node();
                nodeCount++;
            }
            currNode = currNode.getChildren()[index];
        }
        currNode.setCount(currNode.getValue() + 1);
    }

    @Override
    public INode find(String word) {
        String word2 = word.toLowerCase();
        Node currentNode = root;
        for (int i = 0; i < word2.length(); i++) {
            char currLetter = word2.charAt(i);
            int index = currLetter - 'a';
            if (currentNode.getChildren()[index] == null) {
                return null;
            }
            currentNode = currentNode.getChildren()[index];
        }
        return currentNode;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() { //recursive
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelper(root, currWord, output);

        return output.toString();
    }

    @Override
    public boolean equals(Object obj) { //recursive
        if (obj == null) { return false; }
        else if (this == obj) { return true; }
        else if (this.getClass() != obj.getClass()) { return false; }
        Trie newTrie = (Trie)obj;
        if (this.getWordCount() != newTrie.getWordCount()) { return false; }
        else if (this.getNodeCount() != newTrie.getNodeCount()) { return false; }
        else {
            return equalsHelper(this.root, newTrie.root);
        }
    }

    @Override
    public int hashCode() {
        /*
        Combine: wordCount, nodeCount, and index of each non-null child of the root
         */
        int hash = wordCount * (nodeCount << 1);
        //TODO: Rewrite to be constant time
        for (int i = 0; i < root.getChildren().length; i++) {
            if (root.getChildren()[i] != null) {
                hash += i;
            }
        }
        return hash;
    }

    //helpers
    private void toStringHelper(Node currNode, StringBuilder currWord, StringBuilder output) {
        if (currNode.getValue() > 0) {
            output.append(currWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < currNode.getChildren().length; i++) {
            Node child = currNode.getChildren()[i];
            if (child != null) {
                char childLetter = (char)('a' + i);
                currWord.append(childLetter);
                toStringHelper(child, currWord, output);
                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }

    private boolean equalsHelper(Node n1, Node n2) {
        if (n1.getValue() != n2.getValue()) { return false; }
        for (int i = 0; i < n1.getChildren().length; i++) {
            if ((n1.getChildren()[i] == null) == (n2.getChildren()[i] != null)) { return false; }
        }
        for (int i = 0; i < n1.getChildren().length; i++) {
            if (n1.getChildren()[i] != null) {
                if (!equalsHelper(n1.getChildren()[i], n2.getChildren()[i])) {
                    return false;
                }
            }
        }
        return true;
    }
}
