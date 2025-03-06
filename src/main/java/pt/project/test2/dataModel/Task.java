package dataModel;

public abstract class Task {

    protected int idTask;
    protected String statusTask;

    public Task(int idTask, String statusTask) {
        this.idTask = idTask;
        this.statusTask = statusTask;
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
}
