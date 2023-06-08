package superdecompressor.wordtree;

import superdecompressor.dictionary.Dictionary;

import java.util.Vector;

public class Tree
{
    private Node head;
    private void addWordTraverse(Vector<Boolean> originalWord, Vector<Boolean> codeword, Node node)
    {
        if (codeword.isEmpty())
        {
            node.value = originalWord;
            return;
        }

        boolean bit = codeword.remove(0);
        if (!bit) // bit == 0
        {
            if (node.left == null)
                node.left = new Node();
            addWordTraverse(originalWord, codeword, node.left);
        }
        else // bit == 1
        {
            if (node.right == null)
                node.right = new Node();
            addWordTraverse(originalWord, codeword, node.right);
        }
    }

    public Tree(Dictionary dictionary)
    {
        if (dictionary.getSize() == 0)
        {
            head = null;
            return;
        }

        head = new Node();
        if (dictionary.getSize() == 1)
        {
            var originalWord = dictionary.iterator().next().getOriginalWord();
            head.value = originalWord;
            return;
        }

        for (var entry: dictionary)
        {
            Vector<Boolean> codeword = (Vector<Boolean>) entry.getCodeword().clone(); // copy!
            addWordTraverse(entry.getOriginalWord(), codeword, head);
        }
    }
}
