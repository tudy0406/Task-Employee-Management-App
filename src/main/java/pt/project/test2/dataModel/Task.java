package pt.project.test2.dataModel;

public abstract sealed class Task permits SimpleTask, ComplexTask {

    protected int idTask;
    protected String statusTask;
    protected String taskName;

    public Task(int idTask, String statusTask, String taskName) {
        this.idTask = idTask;
        this.statusTask = statusTask;
        this.taskName = taskName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return idTask == task.idTask;
    }
}
