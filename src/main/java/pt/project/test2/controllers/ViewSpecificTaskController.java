package pt.project.test2.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import pt.project.test2.businessLogic.TaskManagement;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.ComplexTask;
import pt.project.test2.dataModel.Employee;
import pt.project.test2.dataModel.Task;

public class ViewSpecificTaskController {

    @FXML
    public Button addTaskComplexBtn;
    public Button changeStatusBtn;
    public VBox tasksTableComplex;
    public Label tasksLIstLabel;
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

    }


    public void setTask(Task task) {

        this.task = task;
        if(task.getType().equals("Simple")){
            simpleTaskProperties.setVisible(true);
            tasksTableComplex.setVisible(false);
            addTaskComplexBtn.setVisible(false);
        }else{
            simpleTaskProperties.setVisible(false);
            tasksTableComplex.setVisible(true);
            addTaskComplexBtn.setVisible(true);
            loadTasksData();
        }

        loadEmployeesData();
   }

    private void loadTasksData() {
        tasksData.clear();
        tasksData.addAll(((ComplexTask)this.task).getTasks());
        tasksListTable.setItems(tasksData);
    }

    private void loadEmployeesData(){
        employeesData.clear();
        employeesData.addAll(TaskManagement.getAssignedEmployees(task.getIdTask()));
        employeesTable.setItems(employeesData);
    }

}
