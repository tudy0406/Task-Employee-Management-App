package pt.project.test2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.project.test2.businessLogic.Utility;

public class AddEmployeeController {

    @FXML
    public AnchorPane addEmployeePane;
    public Button submitBtn;
    public Button cancelBtn;
    public TextField employeeIdField;
    public TextField employeeNameField;

    public void onSubmitBtnClick() {
        try {
            if(checkFields()){
                int employeeId = Integer.parseInt(employeeIdField.getText());
                String employeeName = employeeNameField.getText();
                try{
                    Utility.createEmployee(employeeId, employeeName);
                    try{
                        Utility.saveData();
                    }catch(Exception e){
                        throw new RuntimeException(e.getMessage());
                    }
                    ControllersController.showMessage("Employee added successfully!\nPlease refresh employees page");
                }catch(IllegalArgumentException e){
                    ControllersController.showMessage(e.getMessage());
                }
            }else{
                ControllersController.showMessage("Please fill in all the fields");
            }
        }catch(NumberFormatException e){
            ControllersController.showMessage(e.getMessage());
        }
        ((Stage) addEmployeePane.getScene().getWindow()).close();

    }

    public void onCancelBtnClick() {
        ((Stage) addEmployeePane.getScene().getWindow()).close();
    }

    private boolean checkFields(){
        return !employeeIdField.getText().isEmpty() && !employeeNameField.getText().isEmpty();
    }

}
