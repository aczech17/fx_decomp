module com.example.fx_decomp {
    requires javafx.controls;
    requires javafx.fxml;


    opens fx_decomp to javafx.fxml;
    exports fx_decomp;
}