package superdecompressor;

import superdecompressor.exceptions.BadSignature;
import superdecompressor.exceptions.EndOfFile;
import superdecompressor.exceptions.FileCorrupted;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class FileConfig
{
    final String properSignature = "CRSK";
    private final int padding;
    private final int lastDictionaryWordLength;
    private final int wordLength;
    private final int codewordLengthBitCount;
    private final int dictionaryEntryCount;
    private final long size;


    public int getPadding()
    {
        return padding;
    }

    public int getLastDictionaryWordLength()
    {
        return lastDictionaryWordLength;
    }

    public int getWordLength()
    {
        return wordLength;
    }

    public int getCodewordLengthBitCount()
    {
        return codewordLengthBitCount;
    }

    public int getDictionaryEntryCount()
    {
        return dictionaryEntryCount;
    }

    public long getSize()
    {
        return size;
    }

    public FileConfig(FileReader reader) throws IOException, FileCorrupted, EndOfFile, BadSignature
    {
        size = reader.getSize() * 8;

        byte[] signatureBytes =
        {
                reader.getNextByte(),
                reader.getNextByte(),
                reader.getNextByte(),
                reader.getNextByte(),
        };

        String signature = new String(signatureBytes, StandardCharsets.UTF_8);
        if (!signature.equals(properSignature))
            throw new BadSignature();

        padding = reader.getNextByte();
        lastDictionaryWordLength = reader.getNextByte();

        Vector<Boolean> wordLengthBits = reader.getNextNBits(2);

        if (!wordLengthBits.get(0) && !wordLengthBits.get(1)) // 00
            wordLength = 8;
        else if (!wordLengthBits.get(0) && wordLengthBits.get(1)) // 01
            wordLength = 12;
        else if (wordLengthBits.get(0) && !wordLengthBits.get(1)) // 10
            wordLength = 16;
        else // 11 - corruption
            throw new FileCorrupted("Bad word length");

        Vector<Boolean> maxCodewordLengthBits = reader.getNextNBits(wordLength);
        codewordLengthBitCount = Util.nonLeadingZerosBitCount(maxCodewordLengthBits);

        Vector<Boolean> dictionaryEntryCountBits = reader.getNextNBits(wordLength);
        dictionaryEntryCount = Util.wordToInt(dictionaryEntryCountBits) + 1;
    }
}
