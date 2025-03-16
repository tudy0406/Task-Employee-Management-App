package pt.project.test2.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.SimpleTask;
import pt.project.test2.dataModel.Task;
import java.io.IOException;

public class TasksController {
    @FXML
    public AnchorPane tasksPane;
    public Button createTaskBtn;
    public Button homeBtn;
    public TableView<Task> tasksTable;
    public TableColumn<Task, Integer> taskIdColumn;
    public TableColumn<Task, String> taskNameColumn;
    public TableColumn<Task, String> taskTypeColumn;
    public TableColumn<Task, String> taskStatusColumn;
    private ObservableList<Task> tasksData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        taskIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getIdTask()));
        taskNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getTaskName()));
        taskTypeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getType()));
        taskStatusColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStatusTask()));

        tasksTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    viewTaskDetails(selectedTask);
                }
            }
        });

        loadTasksData();
    }

    private void loadTasksData() {
        tasksData.clear();
        tasksData.addAll(Utility.getTaskList());
        tasksTable.setItems(tasksData);
    }

    @FXML
    protected void onCloseButtonClick() {
        try {
            Utility.saveData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) tasksPane.getScene().getWindow();
        stage.close();
    }

    public void onCreateTaskBtnClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/createTask.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        scene.setRoot(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("CreateTask");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.NONE);

        primaryStage.show();
    }

    public void onHomeBtnClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/hello-view.fxml"));
        Parent root = null;
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

        ((Stage) tasksPane.getScene().getWindow()).close();
    }

    private void viewTaskDetails(Task task) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/viewSpecificTask.fxml"));
            Parent root = loader.load();

            ViewSpecificTaskController controller = loader.getController();
            controller.setTask(task);
            Stage stage = new Stage();
            stage.setTitle("View Task");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}