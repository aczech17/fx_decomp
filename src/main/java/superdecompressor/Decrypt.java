package superdecompressor;

import superdecompressor.exceptions.BadArguments;
import superdecompressor.exceptions.FileCorrupted;

import java.io.IOException;

public class Decrypt
{
    private static byte getKey(String password)
    {
        byte key = 0;
        for (byte b : password.getBytes())
        {
            key = (byte) (key ^ b);
        }
        return key;
    }

    public static void decrypt(ProgramConfig programConfig)
            throws FileCorrupted, IOException, BadArguments
    {
        String inputFilename = programConfig.getInputFilename();
        String outputFilename = programConfig.getTempFilename();
        String password = programConfig.getPassword();

        FileReader reader = new FileReader(inputFilename);
        byte key;
        try
        {
            byte zeroByte = reader.getNextByte();
            if (zeroByte == 0x00) // not encrypted
                key = 0;
            else if (zeroByte == (byte)0xFF)
            {
                if (password == null)
                    throw new BadArguments("No password provided.");
                key = getKey(password);
            }
            else // not encrypted, not decrypted, file corrupted
                throw new FileCorrupted("encryption signature invalid");
        }
        catch (IOException exc)
        {
            throw new FileCorrupted("file empty");
        }

        FileWriter writer = new FileWriter(outputFilename);
        writer.writeByte((byte)0x00);

        while (true)
        {
            byte b;
            try
            {
                b = reader.getNextByte();
            }
            catch (IOException exc) // read until end
            {
                break;
            }
            b = (byte) (b ^ key);
            writer.writeByte(b);
        } // while
        writer.close();
    }
}
