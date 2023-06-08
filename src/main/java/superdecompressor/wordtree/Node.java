package superdecompressor.wordtree;

import java.util.Vector;

public class Node
{
    Vector<Boolean> value = null;
    Node left = null;
    Node right = null;

    public Node getLeft()
    {
        return left;
    }

    public Node getRight()
    {
        return right;
    }
}
