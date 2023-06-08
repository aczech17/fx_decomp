package fx_decomp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import superdecompressor.SuperDecompressor;
import superdecompressor.exceptions.*;
import superdecompressor.wordtree.Tree;

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

    boolean error = false;

    public void submit(ActionEvent event) throws EndOfFile, FileCorrupted, IOException {

        in1 = input1.getText();
        in2 = input2.getText();
        in3 = input3.getText();
        args[0] = in1;
        args[1] = in2;
        args[2] = in3;

        Tree tree = null;
        error = false;
        try
        {
            tree = SuperDecompressor.decompress(args);
        }
        catch (BadSignature badSignature)
        {
            pswd.setText("złe hasło");
            error = true;
        }
        catch (BadArguments e)
        {
            throw new RuntimeException(e); // it won't happen anyway
        }
        catch (FileNonexistent e)
        {
            pswd.setText("Nie ma takiego pliku");
            error = true;
        }

        if (!error)
        {
            var visualisation = new TreeVisualization(tree);
            visualisation.start(new Stage());
            pswd.setText("Zdekompresowano");
        }

    }

}
