package pt.project.test2.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllersController {

    public static void onHomeBtnClick(AnchorPane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader(ControllersController.class.getResource("/pt/project/test2/hello-view.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        scene.setRoot(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.NONE);

        primaryStage.show();

        ((Stage) pane.getScene().getWindow()).close();
    }

    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
