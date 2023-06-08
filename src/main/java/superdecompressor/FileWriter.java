package superdecompressor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

public class FileWriter
{
    private final RandomAccessFile output;
    private long currentBitNumber = 0;
    private byte currentByte = 0;

    public FileWriter(String filename) throws FileNotFoundException
    {
        output = new RandomAccessFile(filename, "rw");
    }

    public void writeBits(Vector<Boolean> bits) throws IOException
    {
        for (var bit: bits)
        {
            byte bitShifted = bit ? (byte) (1 << (7 - currentBitNumber)) : 0; // if bit is 1, shift it
            currentByte |= bitShifted;
            currentBitNumber++;

            if (currentBitNumber == 8)
            {
                output.write(currentByte);
                currentByte = 0;
                currentBitNumber = 0;
            }
        }
    }

    public void writeBitsLeft() throws IOException
    {
        if (currentBitNumber % 8 != 0)
            output.write(currentByte);
    }

    public void writeByte(byte b) throws IOException
    {
        output.write(b);
    }

    public void close() throws IOException
    {
        output.close();
    }
}
