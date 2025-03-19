package pt.project.test2.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.TaskManagement;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.Employee;
import pt.project.test2.dataModel.Task;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class AssignTasksController {

    @FXML
    public AnchorPane assignTasksPane;
    public Button submitBtn;
    public Button cancelBtn;
    public TableView<Task> tasksTable;
    public TableColumn<Task, Integer> taskIdColumn;
    public TableColumn<Task, String> taskNameColumn;
    public TableColumn<Task, String> taskTypeColumn;
    public TableColumn<Task, String> taskStatusColumn;
    private Employee employee;
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private List<Task> selectedTasks = new ArrayList<>();

    public void initialize() {
        taskIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getIdTask()));
        taskNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getTaskName()));
        taskTypeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getType()));
        taskStatusColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStatusTask()));

        tasksTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    selectTask(selectedTask);
                }
            }
        });

        tasksTable.setRowFactory(tv -> new TableRow<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (task == null || empty) {
                    setStyle("");
                } else {
                    if (selectedTasks.contains(task)) {
                        setStyle("-fx-background-color: #a1a1a1; -fx-text-fill: black;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    public void onSubmitBtnClick(){
        try{
            for(Task task : selectedTasks){
                TaskManagement.assignTaskToEmployee(employee.getIdEmployee(), task);
            }

            try{
                Utility.saveData();
                ControllersController.showMessage("Tasks assigned successfully!\n Please refresh employee details page");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ((Stage)assignTasksPane.getScene().getWindow()).close();
        }catch(IllegalArgumentException | IllegalAccessException e){
            ControllersController.showMessage(e.getMessage());
        }

    }

    public void onCancelBtnClick() {
        ((Stage)assignTasksPane.getScene().getWindow()).close();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;

        loadTasks();
    }

    private void loadTasks() {
        tasks.clear();
        tasks.addAll(TaskManagement.getFilteredTasks(employee));
        tasksTable.setItems(tasks);
    }

    private void selectTask(Task task) {
        if(selectedTasks.contains(task)) {
            selectedTasks.remove(task);
        }else{
            selectedTasks.add(task);
        }
    }
}
