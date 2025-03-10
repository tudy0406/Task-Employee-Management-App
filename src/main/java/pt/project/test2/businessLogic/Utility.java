package pt.project.test2.businessLogic;

import pt.project.test2.dataModel.*;

import java.util.*;

public class Utility {
    private static List<Employee> employeeList = new ArrayList<Employee>();
    private static List<Task> taskList = new ArrayList<>();

    public static void filterEmployees(){
        List<Employee> filteredEmployees = new ArrayList<>(employeeList
                .stream()
                .filter(employee -> TaskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee()) >= 40)
                .toList());
        filteredEmployees.sort(Comparator.comparingInt(emp -> TaskManagement.calculateEmployeeWorkDuration(emp.getIdEmployee())));
        filteredEmployees.forEach(System.out::println);
    }

    public static Map<String, Map<String,Integer>> getEmployeeWork(int employeeId, TaskManagement manager) {
        int completedHours = 0, uncompletedHours = 0;

        Map<String, Map<String,Integer>> employeeWork = new HashMap<>();//returned map
        Map<Employee, List<Task>> tmpMap = TaskManagement.getMap();//temporary map from task management

        List<Employee> employees = tmpMap.keySet().stream().toList();//get the list of employees from the task management map

        for(Employee employee : employees) {
            employeeWork.put(employee.getName(), new HashMap<>());
            List<Task> currentEmployeeTasks = tmpMap.get(employee);

            for(Task task : currentEmployeeTasks) {
                if(task.getStatusTask().equals("Completed")) {
                    completedHours++;
                }
                if(task.getStatusTask().equals("Uncompleted")) {
                    uncompletedHours++;
                }
            }

            employeeWork.get(employee.getName()).put("Completed", completedHours);
            employeeWork.get(employee.getName()).put("Uncompleted", uncompletedHours);
        }
        return employeeWork;
    }

    public static void createSimpleTask(int idTask, String statusName, int startHour, int endHour) {
        Task task = new SimpleTask(idTask, "Uncompleted", statusName, startHour, endHour);
        taskList.add(task);
    }

    public static void createComplexTask(int idTask, String statusName, List<Task> tasks) {
        ComplexTask complexTask = new ComplexTask(idTask, "Uncompleted", statusName, tasks);
        taskList.add(complexTask);
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }
    public void setEmployeeList(List<Employee> employeeList) {
        Utility.employeeList = employeeList;
    }
    public List<Task> getTaskList() {
        return taskList;
    }
    public void setTaskList(List<Task> taskList) {
        Utility.taskList = taskList;
    }
}
