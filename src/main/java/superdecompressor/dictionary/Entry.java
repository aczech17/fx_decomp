package superdecompressor.dictionary;

import java.util.Vector;

public class Entry
{
    private final Vector<Boolean> originalWord;
    private final Vector<Boolean> codeword;
    Entry(Vector<Boolean> originalWord, Vector<Boolean> codeword)
    {
        this.originalWord = originalWord;
        this.codeword = codeword;
    }
    public Vector<Boolean> getOriginalWord()
    {
        return originalWord;
    }
    public Vector<Boolean> getCodeword()
    {
        return codeword;
    }
}
