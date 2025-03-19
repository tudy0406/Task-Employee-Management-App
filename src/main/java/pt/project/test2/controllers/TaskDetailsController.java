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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.TaskManagement;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskDetailsController {

    @FXML
    public AnchorPane taskDetailsPane;
    public Button refreshBtn;

    public ButtonBar complexTaskBtns;
    public Button addTasksComplexBtn;
    public Button removeTasksBtn;
    public Button changeStatusBtn;
    public TextField taskIdField;
    public TextField taskNameField;
    public TextField taskTypeField;
    public TextField taskStatusField;
    public TextField startHourField;
    public TextField endHourField;

    public VBox tasksTableComplex;
    public TableView<Task> tasksListTable;
    public TableColumn<Task, Integer> taskIdColumn;
    public TableColumn<Task, String> taskNameColumn;
    public TableColumn<Task, String> taskTypeColumn;
    public TableColumn<Task, String> taskStatusColumn;
    public ObservableList<Task> tasksData = FXCollections.observableArrayList();

    public VBox simpleTaskProperties;

    public TableView<Employee> employeesTable;
    public TableColumn<Employee, Integer> employeeIdColumn;
    public TableColumn<Employee, String> employeeNameColumn;
    public TableColumn<Employee, Integer> employeeWorkDurationColumn;
    private ObservableList<Employee> employeesData = FXCollections.observableArrayList();
    private List<Task> selectedTasks = new ArrayList<>();

    private Task task;

    @FXML
    public void initialize() {
        taskIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getIdTask()));
        taskNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getTaskName()));
        taskTypeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getType()));
        taskStatusColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStatusTask()));

        employeeIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getIdEmployee()));
        employeeNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getName()));
        employeeWorkDurationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(TaskManagement.calculateEmployeeWorkDuration(cellData.getValue().getIdEmployee())));
        taskIdField.setEditable(false);
        taskNameField.setEditable(false);
        taskTypeField.setEditable(false);
        taskStatusField.setEditable(false);
        startHourField.setEditable(false);
        endHourField.setEditable(false);

        tasksListTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Task selectedTask = tasksListTable.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    selectTask(selectedTask);
                }
            }
        });

        tasksListTable.setRowFactory(tv -> new TableRow<Task>() {
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

    private void selectTask(Task task) {
        if(selectedTasks.contains(task)){
            selectedTasks.remove(task);
        }else{
            selectedTasks.add(task);
        }
    }

    public void onChangeStatusBtnClick() throws IOException {
        TaskManagement.modifyTaskStatus(TaskManagement.getAssignedEmployees(task.getIdTask()).getFirst().getIdEmployee(), task.getIdTask());
        try{
            Utility.saveData();
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        reloadWindow();
    }

    public void onRemoveBtnClick(){
        for(Task t : selectedTasks){
            Utility.removeFromComplexTask(task.getIdTask(), t.getIdTask());
        }
        loadTasksData();
        setDetails();
        try{
            Utility.saveData();
        }catch(Exception e){
            throw new RuntimeException(e.getCause());
        }
        selectedTasks.clear();
    }

    public void onAddTasksComplexBtnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/addTasks.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
           throw new RuntimeException("JOHNULEEEEEEE");
        }

        AddTasksController controller = loader.getController();
        controller.setTask(this.task);

        Stage stage = new Stage();
        stage.setTitle("Add Tasks");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onRefreshBtnClick(){
        reloadWindow();
    }

    public void setTask(Task task) {

        this.task = task;
        if(task.getType().equals("Simple")){
            simpleTaskProperties.setVisible(true);
            tasksTableComplex.setVisible(false);
            complexTaskBtns.setVisible(false);
            startHourField.setText(String.valueOf(((SimpleTask)task).getStartHour()));
            endHourField.setText(String.valueOf(((SimpleTask)task).getEndHour()));
        }else{
            simpleTaskProperties.setVisible(false);
            tasksTableComplex.setVisible(true);
            complexTaskBtns.setVisible(true);
            loadTasksData();
        }

        loadEmployeesData();
        setDetails();
    }

    private void loadTasksData() {
        tasksData.clear();
        tasksData.addAll(((ComplexTask)this.task).getTasks());
        tasksListTable.setItems(tasksData);
    }

    private void loadEmployeesData(){
        employeesData.clear();
        employeesData.addAll(TaskManagement.getAssignedEmployees(task.getIdTask()));
        if(employeesData.isEmpty()){
            changeStatusBtn.setDisable(true);
        }
        employeesTable.setItems(employeesData);
    }

    private void reloadWindow(){
        setDetails();
        if(!task.getType().equals("Simple")){
            loadTasksData();
        }
        loadEmployeesData();
    }

    private void setDetails(){
        taskIdField.setText(String.valueOf(task.getIdTask()));
        taskNameField.setText(task.getTaskName());
        taskTypeField.setText(task.getType());
        taskStatusField.setText(task.getStatusTask());
    }
}
