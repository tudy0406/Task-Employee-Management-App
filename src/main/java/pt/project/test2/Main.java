package pt.project.test2;

import pt.project.test2.businessLogic.TaskManagement;
import pt.project.test2.businessLogic.Utility;
import pt.project.test2.dataModel.Employee;
import pt.project.test2.dataModel.SimpleTask;
import pt.project.test2.dataModel.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        /*Task task1 = new SimpleTask(1, "Doit", "Uncompleted","Simple", 8, 18);
        Task task2 = new SimpleTask(2,  "Make", "Uncompleted","Simple",8, 18);
        Task task3 = new SimpleTask(3,  "Join", "Uncompleted","Simple",8, 50);
        Task task4 = new SimpleTask(4,  "Start", "Uncompleted","Simple",8, 18);
        Task task5 = new SimpleTask(5,  "Finish", "Uncompleted","Simple",8, 18);
        Task task6 = new SimpleTask(6,  "Correcting", "Uncompleted","Simple",8, 1000);

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
        Utility.createTask(task6.getIdTask(), task6.getTaskName(), 8, 80);
        List<Task> complexTask = new ArrayList<>();
        complexTask.add(task1);
        complexTask.add(task3);

        Utility.createTask(7,"complex Task", complexTask);
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
        TaskManagement.assignTaskToEmployee(3,Utility.getTaskById(task6.getIdTask()));

        //TaskManagement.modifyTaskStatus(3, 6);
        //TaskManagement.modifyTaskStatus(2, 3);



        Utility.saveData();*/

        /*Utility.loadData();
        //TaskManagement.modifyTaskStatus(3, 6);
        TaskManagement.modifyTaskStatus(2, 3);
        TaskManagement.modifyTaskStatus(1,1);
        for(Task task : Utility.getTaskList()){
            System.out.println(task);
        }
        List<Employee> filteredEmployees = Utility.filterEmployees();
        Utility.saveData();*/
    }
}
