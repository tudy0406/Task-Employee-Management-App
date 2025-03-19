package pt.project.test2.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.Task;

import java.util.*;

public class AddTasksController {

    @FXML
    public AnchorPane addTasksPane;
    public TableView<Task> tasksList;
    public TableColumn<Task, Integer> taskIdColumn;
    public TableColumn<Task, String> taskNameColumn;
    public TableColumn<Task, String> taskTypeColumn;
    public TableColumn<Task, String> taskStatusColumn;
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    public Button submitBtn;
    public Button cancelBtn;
    private List<Task> selectedTasks = new ArrayList<>();
    private Task task;

    public void initialize() {
        taskIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getIdTask()));
        taskNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getTaskName()));
        taskTypeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getType()));
        taskStatusColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStatusTask()));

        tasksList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Task selectedTask = tasksList.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    selectTask(selectedTask);
                }
            }
        });

        tasksList.setRowFactory(tv -> new TableRow<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (task == null || empty) {
                    setStyle(""); // Reset styling for empty rows
                } else {
                    if (selectedTasks.contains(task)) {
                        setStyle("-fx-background-color: #a1a1a1; -fx-text-fill: black;"); // Highlight color
                    } else {
                        setStyle(""); // Reset to default
                    }
                }
            }
        });

    }

    public void setTask(Task task) {
        this.task = task;
        loadTasks();
    }

    private void loadTasks() {
        tasks.clear();
        tasks.addAll(Utility.filterTasks(this.task));
        tasksList.setItems(tasks);
    }

    private void selectTask(Task task) {
        if(selectedTasks.contains(task)) {
            selectedTasks.remove(task);
        }else{
            selectedTasks.add(task);
        }
    }

    public void onSubmitBtnClick() {
        for(Task t : selectedTasks){
            Utility.addToComplexTask(task.getIdTask(), t.getIdTask());
        }
        try{
            Utility.saveData();
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        ControllersController.showMessage("Tasks added successfully!\n Please refresh the task details page");
        ((Stage) addTasksPane.getScene().getWindow()).close();

    }

    public void onCancelBtnClick() {
        ((Stage) addTasksPane.getScene().getWindow()).close();
    }
}
