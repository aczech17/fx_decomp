package superdecompressor.exceptions;

public class FileAlreadyExists extends Exception
{
    public FileAlreadyExists(String filename)
    {
        super("File " + filename + " already exists");
    }
}
