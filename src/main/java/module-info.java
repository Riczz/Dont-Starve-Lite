module riczz {
    requires javafx.controls;
    requires javafx.fxml;

    opens dslite.controllers to javafx.fxml;
    exports dslite;
}