package pt.project.test2.dataModel;

import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task{
    private List<Task> tasks = new ArrayList<>();

    public ComplexTask(int idTask, String taskName, String statusTask, String type, List<Task> tasks) {
        super(idTask, taskName, statusTask, type);
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        for(Task t : tasks){
            if(t.getIdTask() == task.getIdTask()){
                throw new IllegalArgumentException("Task already exists");
            }
        }
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public int estimateDuration(){
        return tasks.stream()
                .filter(task -> "Complete".equals(task.getStatusTask()))
                .mapToInt(Task::estimateDuration)
                .sum();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        String tasksToString = "";

        for(Task t : tasks){
            tasksToString += t.toString() + "\n";
        }
        return "Complex Task [ " + "id: " + getIdTask() + ", " + "taskName: " + getTaskName() + ", " +" type: "+ getType() + "statusTask: " + getStatusTask() + ", \n" + "tasks: " + tasksToString + "]";
    }
}
