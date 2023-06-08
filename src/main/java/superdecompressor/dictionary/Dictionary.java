package superdecompressor.dictionary;

import superdecompressor.FileConfig;
import superdecompressor.FileReader;
import superdecompressor.Util;
import superdecompressor.exceptions.EndOfFile;

import java.util.Iterator;
import java.util.Vector;

public class Dictionary implements Iterable<Entry>
{
    private final Vector<Entry> entries;

    public Dictionary(FileReader reader, FileConfig config) throws EndOfFile
    {
        entries = new Vector<>();

        int entryCount = config.getDictionaryEntryCount();
        int wordLength = config.getWordLength();
        int codewordLengthBitCount = config.getCodewordLengthBitCount();
        int lastDictionaryWordLength = config.getLastDictionaryWordLength();

        for (int i = 0; i < entryCount; i++)
        {
            Vector<Boolean> originalWord;
            if (i == entryCount - 1) // the last entry
                originalWord = reader.getNextNBits(lastDictionaryWordLength);
            else
                originalWord = reader.getNextNBits(wordLength);

            var codeWordLengthCoded = reader.getNextNBits(codewordLengthBitCount);
            int codewordLength = Util.wordToInt(codeWordLengthCoded);

            Vector<Boolean> codeword = reader.getNextNBits(codewordLength);

            entries.add(new Entry(originalWord, codeword));
        }
    }

    public int getSize()
    {
        return entries.size();
    }

    public Vector<Boolean> getOriginalWord(Vector<Boolean> codeword)
    {
        for (var entry: entries)
        {
            var originalWord = entry.getOriginalWord();
            var entryCodeword = entry.getCodeword();
            if (entryCodeword.equals(codeword))
                return originalWord;
        }
        return null;
    }

    @Override
    public Iterator<Entry> iterator()
    {
        return entries.iterator();
    }
}
