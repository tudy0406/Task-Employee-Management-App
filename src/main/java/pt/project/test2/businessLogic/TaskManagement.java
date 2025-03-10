package pt.project.test2.businessLogic;

import pt.project.test2.dataModel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagement {
    private static Map<Employee, List<Task>> map = new HashMap<>();

    public static void assignTaskToEmployee(int idEmployee, Task task) throws IllegalAccessException {
        map.putIfAbsent( TaskManagement.getEmployeeById(idEmployee) ,new ArrayList<>());
        List<Task> tasks = map.get(TaskManagement.getEmployeeById(idEmployee));
        for(Task taskTmp : tasks){
            if(task.equals(taskTmp)){
                throw new IllegalAccessException("Task already exists");
            }
        }
        tasks.add(task);
    }

    public static int calculateEmployeeWorkDuration(int idEmployee){
        return map.getOrDefault(TaskManagement.getEmployeeById(idEmployee), new ArrayList<>()).stream()
                .filter(task -> "Completed".equals(task.getStatusTask()))
                .mapToInt(Task::estimateDuration)
                .sum();
    }

    public static void modifyTaskStatus(int idEmployee, int idTask) {
        List<Task> tasks = map.get(TaskManagement.getEmployeeById(idEmployee));

        if(tasks != null){
                tasks
                .stream()
                .filter(task -> task.getIdTask() == idTask)
                .findFirst()
                        .ifPresent(task -> task.setStatusTask(
                                task.getStatusTask().equals("Uncompleted") ? "Completed" : "Uncompleted"
                        ));
        }
    }

    public static void addEmployee(Employee employee) {
        if(map.putIfAbsent(employee, new ArrayList<>()) == null){
            throw new IllegalArgumentException("Employee already exists");
        }
    }


    public static Map<Employee, List<Task>> getMap() {
        return map;
    }

    public static void setMap(Map<Employee, List<Task>> map) {
        TaskManagement.map = map;
    }

    private static Employee getEmployeeById(int idEmployee) {
        List<Employee> employees = TaskManagement.map.keySet().stream().toList();
        for(Employee employee : employees) {
            if(employee.getIdEmployee() == idEmployee) {
                return employee;
            }
        }
        return null;
    }

}
