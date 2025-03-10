package pt.project.test2.dataModel;

public non-sealed class SimpleTask extends Task{
    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, String statusName , int startHour, int endHour) {
        super(idTask, statusTask, statusName);
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

}
