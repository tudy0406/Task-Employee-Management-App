package pt.project.test2.dataModel;

import java.io.Serializable;

public class Employee implements Serializable {

    private int idEmployee;
    private String name;

    public Employee(int idEmployee, String name) {
        this.idEmployee = idEmployee;
        this.name = name;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return idEmployee;
    }

    @Override
    public String toString() {
        return "Employee [idEmployee=" + idEmployee + ", name=" + name + "]";
    }
}
