package fx_decomp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import superdecompressor.SuperDecompressor;
import superdecompressor.exceptions.*;

import java.io.IOException;

public class Controller {

    @FXML
    private Button sub;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;
    @FXML
    private TextField input3;

    @FXML
    private TextField pswd;

    String in1;
    String in2;
    String in3;
    String path;

    String[] args = new String[3];
    public void submit(ActionEvent event) throws EndOfFile, FileCorrupted, IOException {

        in1 = input1.getText();
        in2 = input2.getText();
        in3 = input3.getText();
        args[0] = in1;
        args[1] = in2;
        args[2] = in3;

        try
        {
            SuperDecompressor.decompress(args);
        }
        catch (BadSignature badSignature)
        {
            pswd.setText("złe hasło");
        } catch (BadArguments e)
        {
            throw new RuntimeException(e);
        } catch (FileAlreadyExists e)
        {
            throw new RuntimeException(e);
        } catch (FileNonexistent e)
        {
            throw new RuntimeException(e);
        }


    }

}
