import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
public class Server implements Runnable {
    private ArrayList<Task> tasks;
    private AtomicInteger waitingPeriod = new AtomicInteger();
    public Server(int clientsNr){
        this.tasks = new ArrayList<>(clientsNr);

    }
    public void addTask(Task newTask){

        this.tasks.add(newTask);
        waitingPeriod.getAndAdd(newTask.getProcessingTime());

    }
    public void run(){
        while(true){
            try {
                if(!getTasks().isEmpty())
                while(!getTasks().isEmpty()){
                    if(tasks.get(0).getProcessingTime() == 0){
                        tasks.remove(0);
                    }else{
                        tasks.get(0).setProcessingTime(tasks.get(0).getProcessingTime()-1);
                    }
                    waitingPeriod.getAndAdd(-1);
                    Thread.sleep(1000);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

//    public Task[] getTask(){
//        return tasks;
//    }
}
