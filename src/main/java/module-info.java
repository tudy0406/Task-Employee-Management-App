module pt.project.test2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens pt.project.test2 to javafx.fxml;
    exports pt.project.test2;
}