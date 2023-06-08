package superdecompressor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

public class Util
{
    public static int nonLeadingZerosBitCount(Vector<Boolean> word)
    {
        if (word.size() == 1) // only 0 bit is one bit
            return 1;

        int count = word.size();
        for (Boolean bit : word)
        {
            if (!bit) // bit == 0
                count--;
            else
                break;
        }
        return count;
    }

    public static int wordToInt(Vector<Boolean> word)
    {
        int result = 0;
        for (int i = 0; i < word.size(); i++)
        {
            boolean bit = word.get(i);
            if (bit)
            {
                result |= (1 << (31 - i));
            }
        }

        result = result >>> (32 - word.size()); // Logical shift
        return result;
    }

    public static long getFileSize(String filename) throws IOException
    {
        try (RandomAccessFile file = new RandomAccessFile(filename, "r"))
        {
            return file.length();
        }
        catch (FileNotFoundException exception)
        {
            throw new FileNotFoundException("");
        }
    }

    public static String getBinaryString(Vector<Boolean> word)
    {
        if (word == null)
            return "";

        String str = "";
        for (var bit: word)
        {
            if (bit == false)
                str += "0";
            else
                str += "1";
        }

        return str;
    }
}
