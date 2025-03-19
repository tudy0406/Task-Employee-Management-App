package pt.project.test2.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.Task;
import java.util.ArrayList;
import java.util.List;

public class CreateTaskController {
    @FXML
    public AnchorPane createTaskPane;
    public TextField taskIdField;
    public TextField taskNameField;
    public ChoiceBox<String> taskTypeChoiceBox;
    public VBox simpleTaskProperties;
    public TextField startHourField;
    public TextField endHourField;
    public TableView<Task> tasksListComplex;
    public TableColumn<Task, Integer> taskIdColumn;
    public TableColumn<Task, String> taskNameColumn;
    public TableColumn<Task, String> taskTypeColumn;
    public TableColumn<Task, String> taskStatusColumn;
    private ObservableList<Task> tasksList = FXCollections.observableArrayList();
    private List<Task> selectedTasks = new ArrayList<>();

    public Button submitBtn;
    public Button cancelBtn;

    public void initialize() {
        ObservableList<String> taskTypes = FXCollections.observableArrayList(
                "Simple Task",
                "Complex Task"
        );
        taskTypeChoiceBox.setItems(taskTypes);
        taskTypeChoiceBox.setValue(taskTypes.getFirst());
        taskTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Task type changed from " + oldValue + " to " + newValue);
            handleTaskTypeChange(newValue);
        });

        tasksListComplex.setVisible(false);


        taskIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getIdTask()));
        taskNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getTaskName()));
        taskTypeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getType()));
        taskStatusColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStatusTask()));

        tasksListComplex.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Task selectedTask = tasksListComplex.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    selectTask(selectedTask);
                }
            }
        });

        tasksListComplex.setRowFactory(tv -> new TableRow<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (task == null || empty) {
                    setStyle(""); // Reset styling for empty rows
                } else {
                    if (selectedTasks.contains(task)) {
                        setStyle("-fx-background-color: #707070; -fx-text-fill: black;"); // Highlight color
                    } else {
                        setStyle(""); // Reset to default
                    }
                }
            }
        });

        loadTasks();
    }

    public void onSubmitBtnClick(){
        if(!checkFields()){
            ControllersController.showMessage("Please fill all the required fields!");
            return;
        }
        int taskId = Integer.parseInt(taskIdField.getText());

        if(taskTypeChoiceBox.getSelectionModel().getSelectedItem().equals("Simple Task")){
            int startHour = Integer.parseInt(startHourField.getText());
            int endHour = Integer.parseInt(endHourField.getText());
            Utility.createTask(taskId, taskNameField.getText(),startHour, endHour);
        }else{
            Utility.createTask(taskId, taskNameField.getText(), Utility.getTasksListByIds(selectedTasks));
        }
        try{
            Utility.saveData();
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

        ControllersController.showMessage("Task created successfully!\nPlease refresh tasks page");
        ((Stage) this.createTaskPane.getScene().getWindow()).close();

    }

    public void onCancelBtnClick(){
        ((Stage) createTaskPane.getScene().getWindow()).close();
    }

    private boolean checkFields(){
        if(taskIdField.getText().isEmpty() || taskNameField.getText().isEmpty()){
            return false;
        }
        if(taskTypeChoiceBox.getSelectionModel().getSelectedItem().equals("Simple Task")){
            return !startHourField.getText().isEmpty() && !endHourField.getText().isEmpty();
        }
        return true;
    }

    private void handleTaskTypeChange(String newValue) {
        if(newValue.equals("Simple Task")) {
            tasksListComplex.setVisible(false);
            simpleTaskProperties.setVisible(true);
        }else{
            tasksListComplex.setVisible(true);
            simpleTaskProperties.setVisible(false);
        }
    }

    private void loadTasks() {
        tasksList.clear();
        tasksList.addAll(Utility.getTaskList().stream().filter(task -> task.getStatusTask().equals("Uncompleted")).toList());
        tasksListComplex.setItems(tasksList);
    }

    private void selectTask(Task task) {
        if(selectedTasks.contains(task)) {
            selectedTasks.remove(task);
        }else{
            selectedTasks.add(task);
        }
        tasksListComplex.refresh();
    }
}
