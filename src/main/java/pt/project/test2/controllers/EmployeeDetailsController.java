package pt.project.test2.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.jshell.execution.Util;
import pt.project.test2.businessLogic.TaskManagement;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.Employee;
import pt.project.test2.dataModel.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDetailsController {

    @FXML
    public AnchorPane employeeDetailsPane;
    public Button refreshBtn;
    public Button assignTasksBtn;

    public TextField employeeIdField;
    public TextField employeeNameField;
    public TextField workDurationField;
    public TextField completedTasksField;
    public TextField uncompletedTasksField;

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

        employeeIdField.setEditable(false);
        employeeNameField.setEditable(false);
        workDurationField.setEditable(false);
        completedTasksField.setEditable(false);
        uncompletedTasksField.setEditable(false);

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

    public void onRefreshBtnClick(){
        loadTasks();
        setDetails();
    }

    public void onRemoveBtnClick(){
        for(Task t : selectedTasks){
            TaskManagement.removeTaskFromEmployee(employee.getIdEmployee(), t);
        }
        loadTasks();
        try{
            Utility.saveData();
        }catch(Exception e){
            throw new RuntimeException(e.getCause());
        }
        selectedTasks.clear();
    }

    public void onAssignTasksBtnClick(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/assignTasks.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("JOHNULEEEEEEE");
        }

        AssignTasksController controller = loader.getController();
        controller.setEmployee(this.employee);

        Stage stage = new Stage();
        stage.setTitle("Assign Tasks");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;

        loadTasks();
        setDetails();
    }

    private void selectTask(Task task) {
        if(selectedTasks.contains(task)){
            selectedTasks.remove(task);
        }else{
            selectedTasks.add(task);
        }
    }

    private void loadTasks() {
        tasks.clear();
        tasks.addAll(TaskManagement.getMap().get(employee));
        tasksTable.setItems(tasks);
    }

    private void setDetails(){
        Map<String, Integer> tmpMap = Utility.getEmployeeWork().get(employee.getName());
        employeeIdField.setText(String.valueOf(employee.getIdEmployee()));
        employeeNameField.setText(employee.getName());
        workDurationField.setText(String.valueOf(TaskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee())));
        completedTasksField.setText(String.valueOf(tmpMap.get("Completed")));
        uncompletedTasksField.setText(String.valueOf(tmpMap.get("Uncompleted")));
    }

}
