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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.TaskManagement;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.Employee;
import pt.project.test2.dataModel.Task;

import java.io.IOException;
import java.util.List;

public class EmployeesController {

    @FXML
    public AnchorPane employeesPane;
    public Button filterEmployeesBtn;
    public Button addEmployeeBtn;
    public Button homeBtn;
    public Button refreshBtn;

    public TableView<Employee> employeesTable;
    public TableColumn<Employee, Integer> employeeIdColumn;
    public TableColumn<Employee, String> employeeNameColumn;
    public TableColumn<Employee, String> workDurationColumn;
    private ObservableList<Employee> employeesObservableList = FXCollections.observableArrayList();;
    private boolean filtered = false;

    public void initialize() {
        employeeIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getIdEmployee()));
        employeeNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getName()));
        workDurationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(TaskManagement.calculateEmployeeWorkDuration(cellData.getValue().getIdEmployee())));

        employeesTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
                if (selectedEmployee != null) {
                    viewEmployeeDetails(selectedEmployee);
                }
            }
        });

        loadEmployees();
    }

    public void onHomeBtnClick() throws IOException {
        ControllersController.onHomeBtnClick(employeesPane);
    }

    public void onFilterEmployeesBtnClick() {
        filtered = !filtered;
        loadEmployees();
    }

    public void onAddEmployeeBtnClick() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/addEmployee.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Employee");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void onRefreshBtnClick() {
        loadEmployees();
    }

    private void loadEmployees() {
        employeesObservableList.clear();
        if(!filtered) {
            employeesObservableList.addAll(TaskManagement.getMap().keySet().stream().toList());
        }else{
            employeesObservableList.addAll(Utility.filterEmployees());
        }
        employeesTable.setItems(employeesObservableList);
    }

    private void viewEmployeeDetails(Employee employee) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pt/project/test2/employeeDetails.fxml"));
            Parent root = loader.load();

            EmployeeDetailsController controller = loader.getController();
            controller.setEmployee(employee);
            Stage stage = new Stage();
            stage.setTitle("View Employee");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
