package pt.project.test2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.Utility;

import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(
                    HelloApplication.class.getResource("/pt/project/test2/hello-view.fxml")
            ));

            Utility.loadData();
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> {
                try {
                    Utility.saveData();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
