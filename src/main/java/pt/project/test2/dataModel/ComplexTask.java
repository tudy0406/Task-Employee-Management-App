package dataModel;

import java.util.List;

public class ComplexTask extends Task{
    private List<Task> tasks;

    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
    }

    @Override
    public int estimateDuration(){
        int total = 0;
        for(Task task : tasks){
            total += task.estimateDuration();
        }
        return total;
    }
}
