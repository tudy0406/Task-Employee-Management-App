package dataModel;

public class SimpleTask extends Task{
    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask) {
        super(idTask, statusTask);
    }

    public int getStartHour() {
        return startHour;
    }
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    @Override
    public int estimateDuration(){
        return endHour - startHour;
    }

}
