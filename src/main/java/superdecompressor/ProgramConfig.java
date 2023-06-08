package superdecompressor;

import superdecompressor.exceptions.BadArguments;
import superdecompressor.exceptions.FileNonexistent;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

public class ProgramConfig
{
    private static final String tmpFilenamePrefix = "decrypted.tmp";

    private final String inputFilename;
    private final String outputFilename;
    private final String tmpFilename;
    private final String password;

    private static boolean fileExists(String filename)
    {
        File file = new File(filename);
        return file.exists();
    }

    private String setTempFileName() throws FileAlreadyExistsException
    {
        for (int number = 0; number < Integer.MAX_VALUE; number++)
        {
            String name = tmpFilenamePrefix + number;
            if (!fileExists(name) && !name.equals(outputFilename))
                return name;
        }

        throw new FileAlreadyExistsException("Could not create temporary file. No number fitting.");
    }

    public ProgramConfig(String[] args) throws BadArguments, FileAlreadyExistsException, FileNonexistent
    {
        if (args.length < 2)
            throw new BadArguments("");

        this.inputFilename = args[0];
        if (!fileExists(inputFilename))
            throw new FileNonexistent("File " + inputFilename + " doesn't exist.");

        this.outputFilename = args[1];

        this.tmpFilename = setTempFileName();

        if (args.length >= 3)
            password = args[2];
        else
            password = null;
    }

    public String getInputFilename()
    {
        return inputFilename;
    }

    public String getOutputFilename()
    {
        return outputFilename;
    }

    public String getPassword()
    {
        return password;
    }

    public String getTempFilename()
    {
        return tmpFilename;
    }
}
