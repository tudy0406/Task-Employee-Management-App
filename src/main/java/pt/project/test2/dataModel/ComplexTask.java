package pt.project.test2.dataModel;

import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task{
    private List<Task> tasks;

    public ComplexTask(int idTask, String statusTask, String statusName, List<Task> tasks) {
        super(idTask, statusTask, statusName);
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

}
