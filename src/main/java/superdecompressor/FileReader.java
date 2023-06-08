package superdecompressor;

import superdecompressor.dictionary.Dictionary;
import superdecompressor.exceptions.EndOfFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

public class FileReader
{
    private final RandomAccessFile input;
    private long currentBitNumber = 0;
    private byte currentByte = 0;

    public FileReader(String filename) throws FileNotFoundException
    {
        input = new RandomAccessFile(filename, "r");
    }

    public long getSize() throws IOException
    {
        return input.length();
    }

    public byte getNextByte() throws IOException
    {
        currentByte = input.readByte();
        currentBitNumber += 8;
        return currentByte;
    }

    public Vector<Boolean> getNextNBits(long n) throws EndOfFile
    {
        var bits = new Vector<Boolean>();

        for (int bitsRead = 0; bitsRead < n; bitsRead++)
        {
            boolean bit = getNextBit();
            bits.add(bit);
        }

        return bits;
    }

    private boolean getNextBit() throws EndOfFile
    {
        long bitPosition = this.currentBitNumber % 8;
        if (bitPosition == 0)
        {
            try
            {
                this.currentByte = input.readByte();
            }
            catch (IOException exc)
            {
                throw new EndOfFile("EOF");
            }
        }

        boolean bit = (this.currentByte & (1 << (7 - bitPosition))) != 0;
        this.currentBitNumber++;

        return bit;
    }

    public Vector<Boolean> getNextCodeword(Dictionary dictionary, FileConfig config) throws EndOfFile
    {
        if (currentBitNumber > config.getSize() - 1 - config.getPadding())
            throw new EndOfFile("padding reached");

        var possibleCodeword = new Vector<Boolean>();
        while (true)
        {
            boolean nextBit = getNextBit();
            possibleCodeword.add(nextBit);

            if (dictionary.getOriginalWord(possibleCodeword) != null)
                return possibleCodeword;
        }
    }

    public void close() throws IOException
    {
        input.close();
    }
}
