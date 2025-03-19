module pt.project.test2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jdk.jshell;
    requires java.desktop;


    opens pt.project.test2 to javafx.fxml;
    exports pt.project.test2;
    exports pt.project.test2.controllers;
    opens pt.project.test2.controllers to javafx.fxml;
}