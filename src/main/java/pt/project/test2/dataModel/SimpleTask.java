package pt.project.test2.dataModel;

public non-sealed class SimpleTask extends Task{
    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String taskName , String statusTask, String type, int startHour, int endHour) {
        super(idTask, taskName, statusTask, type);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }
    public int getEndHour() {
        return endHour;
    }

    @Override
    public int estimateDuration(){
        return endHour - startHour;
    }

    @Override
    public String toString(){
        return "SimpleTask [ id: " + idTask + " name: " + taskName +" type: "+ type +" status: " + statusTask + " startHour: " + startHour + " endHour: " + endHour + " ]";
    }

}
