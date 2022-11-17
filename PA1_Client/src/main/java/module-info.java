/**
 * Necessary code to
 * require and use fxml
 */
module edu.ucdenver {
    requires javafx.controls;
    requires javafx.fxml;

//    opens edu.ucdenver to javafx.fxml;
    opens edu.ucdenver.app to javafx.fxml;
    exports edu.ucdenver.app;
}