package pt.project.test2.businessLogic;

import pt.project.test2.dataModel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagement{
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
        for(Task task : map.get(getEmployeeById(idEmployee))){
            if(task.getIdTask() == idTask){
                if(task.getStatusTask().equals("Completed")){
                    task.setStatusTask("Uncompleted");
                }else{
                    task.setStatusTask("Completed");
                }
            }
        }
    }

    //get all the employees which are working on a task
    public static List<Employee> getAssignedEmployees(int idTask) {
        List<Employee> assignedEmployees = new ArrayList<>();
        for(Map.Entry<Employee, List<Task>> entry : map.entrySet()){
            Employee employee = entry.getKey();
            List<Task> tasks = entry.getValue();
            for(Task task : tasks){
                if(task.getType().equals("Complex")){
                    for(Task taskTmp : ((ComplexTask) task).getTasks()){
                        if(taskTmp.getIdTask() == idTask){
                            assignedEmployees.add(employee);
                        }
                    }
                }else{
                    if(task.getIdTask() == idTask){
                        assignedEmployees.add(employee);
                    }
                }
            }
        }
        return assignedEmployees;
    }


    public static Map<Employee, List<Task>> getMap() {
        return map;
    }

    public static void setMap(Map<Employee, List<Task>> map) {
        TaskManagement.map = map;
    }

    private static Employee getEmployeeById(int idEmployee) {
        for(Employee employee : map.keySet()) {
            if(employee.getIdEmployee() == idEmployee) {
                return employee;
            }
        }
        return null;
    }
}
