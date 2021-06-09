public class Task implements Comparable<Task>{
    private int id;
    private int arrivalTime;
    private int processingTime;
    private int finishTime;
    public Task(int arrivalTime,int processingTime, int id){
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.id=id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public int compareTo(Task t) {
        if(this == null || t == null)
            return 0;
        return this.arrivalTime-t.getArrivalTime();
    }
}
