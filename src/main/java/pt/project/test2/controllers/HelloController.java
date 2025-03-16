package pt.project.test2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.Utility;

import java.io.IOException;

public class HelloController {
    @FXML
    public AnchorPane helloPanel;
    public Button employees;
    public Button tasks;
    public Button assignTask;

    @FXML
    protected void onEmployeesButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/employees.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.setRoot(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Employees");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.NONE);

        primaryStage.show();
        ((Stage) helloPanel.getScene().getWindow()).close();
    }

    @FXML
    protected void onCloseButtonClick() {
        try {
            Utility.saveData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) helloPanel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onTasksButtonClick(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/tasks.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        Scene scene = new Scene(root);
        scene.setRoot(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Tasks");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.NONE);

        primaryStage.show();
        ((Stage) helloPanel.getScene().getWindow()).close();
    }
}