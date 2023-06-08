package superdecompressor;

import superdecompressor.dictionary.Dictionary;
import superdecompressor.exceptions.*;
import superdecompressor.wordtree.Tree;

import java.io.File;
import java.io.IOException;

public class SuperDecompressor
{
    private static Tree decompress_file(ProgramConfig programConfig) throws IOException, EndOfFile, FileCorrupted, BadSignature
    {
        FileReader reader = new FileReader(programConfig.getTempFilename());
        reader.getNextByte(); // forget it

        FileConfig fileConfig = new FileConfig(reader);
        Dictionary dictionary = new Dictionary(reader, fileConfig);

        Tree tree = new Tree(dictionary);

        FileWriter writer = new FileWriter(programConfig.getOutputFilename());

        while (true)
        {
            try
            {
                var codeword = reader.getNextCodeword(dictionary, fileConfig);
                var originalWord = dictionary.getOriginalWord(codeword);
                writer.writeBits(originalWord);
            }
            catch (EndOfFile eof)
            {
                reader.close();
                writer.writeBitsLeft();
                return tree;
            }
        }
    }
    public static Tree decompress(String[] args)
            throws IOException, FileCorrupted, EndOfFile, BadSignature, BadArguments, FileNonexistent, FileAlreadyExists
    {
        ProgramConfig programConfig;
        programConfig = new ProgramConfig(args);



        if (Util.getFileSize(programConfig.getInputFilename()) == 0)
        {
            File emptyFile = new File(programConfig.getOutputFilename()); // Create an empty file.
            if (!emptyFile.createNewFile())
                throw new FileAlreadyExists(programConfig.getOutputFilename());
        }

        Decrypt.decrypt(programConfig);
        Tree tree = decompress_file(programConfig);

        // remove the temporary file
        File file = new File(programConfig.getTempFilename());
        if (!file.delete())
            System.out.println("Could not remove temporary file " + programConfig.getTempFilename() + ".");

        return tree;
    }
}
