package pt.project.test2;

import pt.project.test2.businessLogic.TaskManagement;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.Employee;
import pt.project.test2.dataModel.SimpleTask;
import pt.project.test2.dataModel.Task;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Task task1 = new SimpleTask(1, "Doit", "Uncompleted",8, 18);
        Task task2 = new SimpleTask(2,  "Make", "Uncompleted",8, 18);
        Task task3 = new SimpleTask(3,  "Join", "Uncompleted",8, 80);
        Task task4 = new SimpleTask(4,  "Start", "Uncompleted",8, 18);
        Task task5 = new SimpleTask(5,  "Finish", "Uncompleted",8, 18);
        Task task6 = new SimpleTask(6,  "Correcting", "Uncompleted",8, 50);

        Employee e1 = new Employee(1, "John Doe");
        Employee e2 = new Employee(2, "Jane Doe");
        Employee e3 = new Employee(3, "Smith Doe");

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);

        Utility.setTaskList(tasks);
        Utility.createSimpleTask(task6.getIdTask(), task6.getTaskName(), 8, 50);

        for(Task task : Utility.getTaskList()){
            System.out.println(task);
        }

        Utility.addEmployee(e1);
        Utility.addEmployee(e2);
        Utility.addEmployee(e3);

        TaskManagement.assignTaskToEmployee(1,task1);
        TaskManagement.assignTaskToEmployee(1,task2);
        TaskManagement.assignTaskToEmployee(2,task3);
        TaskManagement.assignTaskToEmployee(2,task4);
        TaskManagement.assignTaskToEmployee(3,task5);
        TaskManagement.assignTaskToEmployee(3,task6);

        TaskManagement.modifyTaskStatus(3, 6);
        TaskManagement.modifyTaskStatus(2, 3);

        for(Task task : Utility.getTaskList()){
            System.out.println(task);
        }

        Utility.filterEmployees();
    }
}
