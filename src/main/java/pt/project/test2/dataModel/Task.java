package pt.project.test2.dataModel;

import java.io.Serializable;

public abstract sealed class Task implements Serializable permits SimpleTask, ComplexTask{

    protected int idTask;
    protected String statusTask;
    protected String taskName;
    protected String type;
    public Task(int idTask, String taskName, String statusTask, String type) {
        this.idTask = idTask;
        this.statusTask = statusTask;
        this.taskName = taskName;
        this.type = type;
    }

    public abstract int estimateDuration();

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return idTask == task.idTask;
    }
}
