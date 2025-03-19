package pt.project.test2.businessLogic;

import pt.project.test2.configuration.Config;
import pt.project.test2.dataAccess.ISerialization;
import pt.project.test2.dataModel.*;

import java.util.*;

public class Utility {
    private static List<Task> taskList = new ArrayList<>();
    private static final ISerialization serialization = new ISerialization();

    public static List<Employee>  filterEmployees(){
        List<Employee> filteredEmployees = new ArrayList<>(TaskManagement.getMap().keySet()
                .stream()
                .filter(employee -> TaskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee()) >= 40)
                .toList());
        filteredEmployees.sort(Comparator.comparingInt(emp -> TaskManagement.calculateEmployeeWorkDuration(emp.getIdEmployee())));
        return filteredEmployees;
    }

    public static Map<String, Map<String,Integer>> getEmployeeWork() {
        int completedTasks = 0, uncompletedTasks = 0;

        Map<String, Map<String,Integer>> employeeWork = new HashMap<>();//returned map
        Map<Employee, List<Task>> tmpMap = TaskManagement.getMap();//temporary map from task management

        List<Employee> employees = tmpMap.keySet().stream().toList();//get the list of employees from the task management map

        for(Employee employee : employees) {
            employeeWork.put(employee.getName(), new HashMap<>());//create cell for every employee with employeeName as key
            completedTasks = 0;
            uncompletedTasks = 0;
            for(Task task : tmpMap.get(employee)) {
                if(task.getStatusTask().equals("Completed")) {
                    completedTasks++;
                }else{
                    uncompletedTasks++;
                }
            }

            employeeWork.get(employee.getName()).put("Completed", completedTasks);
            employeeWork.get(employee.getName()).put("Uncompleted", uncompletedTasks);
        }
        return employeeWork;
    }

    public static void createEmployee(int idEmployee, String employeeName){
        try{
            addEmployee(new Employee(idEmployee, employeeName));
        }catch (IllegalArgumentException e){
            throw e;
        }
    }

    public static void createTask(int idTask, String taskName, int startHour, int endHour) {
        Task task = new SimpleTask(idTask, taskName, "Uncompleted","Simple",startHour, endHour);
        taskList.add(task);
    }

    public static void createTask(int idTask, String taskName, List<Task> tasks) {
        for(Task task : tasks) {
            if(getTaskById(task.getIdTask()) == null) {
                return;
            }
        }
        ComplexTask complexTask = new ComplexTask(idTask, taskName, "Uncompleted", "Complex", tasks);
        taskList.add(complexTask);
    }

    public static void addToComplexTask(int idComplexTask, int idTask){
        if(getTaskById(idComplexTask) == null) {
            throw new IllegalArgumentException("Complex Task does not exist!");
        }
        if(getTaskById(idTask) == null) {
            throw new IllegalArgumentException("Task does not exist");
        }

        if(!(getTaskById(idComplexTask) instanceof ComplexTask complexTask)) {
            throw new IllegalArgumentException("The Task you want to add to is not a Complex Task!");
        }else{
            if(complexTask.getTasks().contains(getTaskById(idTask))) {
                throw new IllegalArgumentException("The Task you want to add is already added to this Complex Task!");
            }
            complexTask.addTask(getTaskById(idTask));
        }
    }

    public static void removeFromComplexTask(int idComplexTask, int idTask){
        if(getTaskById(idComplexTask) == null) {
            throw new IllegalArgumentException("Complex Task does not exist!");
        }
        if(getTaskById(idTask) == null) {
            throw new IllegalArgumentException("Task does not exist");
        }

        if(!(getTaskById(idComplexTask) instanceof ComplexTask complexTask)) {
            throw new IllegalArgumentException("The Task you want to remove from is not a Complex Task!");
        }else{
            if(!complexTask.getTasks().contains(getTaskById(idTask))) {
                throw new IllegalArgumentException("The Task you want to remove is not in this Complex Task's list !");
            }
            complexTask.removeTask(getTaskById(idTask));
        }
    }

    public static void addEmployee(Employee employee) {
        if(TaskManagement.getMap().putIfAbsent(employee, new ArrayList<>()) != null){
            throw new IllegalArgumentException("Employee already exists");
        }
    }

    public static Task getTaskById(int idTask){
        for(Task task : taskList) {
            if(task.getIdTask() == idTask) {
                return task;
            }
        }
        return null;
    }

    public static void saveData() throws Exception {
        serialization.saveData(TaskManagement.getMap(), Utility.getTaskList());
    }

    @SuppressWarnings("unchecked")
    public static void loadData() throws Exception {
        Object[] loadedData = serialization.loadData();
        if(loadedData != null){
            TaskManagement.setMap((Map<Employee, List<Task>>) loadedData[0]);
            taskList = (List<Task>) loadedData[1];
            System.out.println("Loaded Data");
        }
    }

    public static List<Task> getTasksListByIds(List<Task> tasks){
        return taskList.stream().filter(tasks::contains).toList();
    }
    public static List<Task> filterTasks(Task task){
        List<Task> tasks = ((ComplexTask) task).getTasks();
        return taskList.stream().filter(t-> !tasks.contains(t) && t.getIdTask() != task.getIdTask()).toList();
    }

    public static List<Task> getTaskList() {
        return taskList;
    }
    public static void setTaskList(List<Task> taskList) {
        Utility.taskList = taskList;
    }
}
