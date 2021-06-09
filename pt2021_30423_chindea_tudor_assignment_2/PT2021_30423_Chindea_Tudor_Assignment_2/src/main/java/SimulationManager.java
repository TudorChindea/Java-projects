import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimulationManager implements Runnable{
    public int timeLimit= 0,maxProcessingTime =0,minProcessingTime=0,maxArrivalTime=0,minArrivalTime=0,numberOfServers=0,numberOfClients=0;
    int currentTime=0,awgWaitTime=0,awgServTime,peakH=0,maxH=0,peakSum=0;

    private ArrayList<Task> tasksToDelete= new ArrayList<>();
    private Scheduler scheduler ;
    private ArrayList<Task> generatedTasks= new ArrayList<>();
    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int maxArrivalTime, int minArrivalTime,int numberOfClients, int numberOfServers) throws Exception{
        //Scheduler store ;
        this.timeLimit=timeLimit;
        this.maxProcessingTime=maxProcessingTime;
        this.maxArrivalTime=maxArrivalTime;
        this.minProcessingTime=minProcessingTime;
        this.minArrivalTime=minArrivalTime;
        this.numberOfClients=numberOfClients;
        this.numberOfServers=numberOfServers;
        new FileWriter("output.txt");
        scheduler = new Scheduler(numberOfServers,numberOfClients);
        Thread t;
        for(int i=0;i<numberOfServers;i++){
            scheduler.getThreads().get(i).start();
        }
       // scheduler = store;
        generateNRandomTasks();
    }
    private void generateNRandomTasks(){
        int checkoutTime,serviceTime;
        Random rand = new Random();
        for(int i=1;i<=numberOfClients;i++){
            checkoutTime = rand.nextInt(maxArrivalTime-minArrivalTime+1)+minArrivalTime;
            serviceTime = rand.nextInt(maxProcessingTime-minProcessingTime+1)+minProcessingTime;
            generatedTasks.add(new Task(checkoutTime, serviceTime, i));
        }

        Collections.sort(generatedTasks);
    }
    public void printFile(int currentTime){
        String state = new String();
        try (FileWriter fw = new FileWriter("output.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            state = "Time " + currentTime + "\n Waiting clients:\n";
            bw.write(state);
            System.out.println(state);
            for (Task t : generatedTasks) {
                state = "(" + t.getId() + "," + t.getArrivalTime() + "," + t.getProcessingTime() + ");";
                bw.write(state);
                System.out.println(state);
            }
            int serverNr = 1;
            for (Server s : scheduler.getServers()) {
                state = "Queue " + serverNr+++"\n";
                bw.write(state);
                System.out.println(state);
                if (!s.getTasks().isEmpty()) {
                    state = "(" + s.getTasks().get(0).getId() + "," + s.getTasks().get(0).getArrivalTime() + "," + s.getTasks().get(0).getProcessingTime() + ")\n";
                    bw.write(state);
                    System.out.println(state);
                }
                else{
                    bw.write("closed\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int awgServiceT(){
        int avg=0;
        for(Task t: generatedTasks){
            avg += t.getProcessingTime();
        }
        avg /= numberOfClients;
        return avg;
    }
    public int exitSim(){
        int clientsLeft;
        if(generatedTasks.size() == 0){
            clientsLeft=0;
            for(Server s: scheduler.getServers())
                clientsLeft+=s.getTasks().size();
            if(clientsLeft == 0)
                return 1;
        }
        return 0;
    }
    public void printFinal(int maxH, int awgWaitTime,int awgServTime){
        try (FileWriter fw = new FileWriter("output.txt", true);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            bw.write("peak hour "+maxH+"\navg service time" + awgServTime +"\n avg waiting time "+ awgWaitTime);
        }
        catch (IOException e){
        }
    }
    public void sleepTh(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        while(currentTime <= timeLimit){
            if(currentTime==0){
                awgServTime=awgServiceT();
            }
            if(exitSim() == 1){
                break;
            }
            for(Task t : generatedTasks) {
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);
                        awgWaitTime += t.getFinishTime();
                    tasksToDelete.add(t);
                }
            }
            generatedTasks.removeAll(tasksToDelete);
            printFile(currentTime);
            for (Server s : scheduler.getServers()){
                peakSum+=s.getTasks().size();
            }
            if(peakSum>peakH) {
                peakH = peakSum;
                maxH=currentTime;
            }
            currentTime++;
            sleepTh();
        }
        awgWaitTime/=numberOfClients;
        printFinal(maxH, awgWaitTime, awgServTime);
    }

    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public int getMinArrivalTime() {
        return minArrivalTime;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public int getMinProcessingTime() {
        return minProcessingTime;
    }

    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public ArrayList<Task> getGeneratedTasks() {
        return generatedTasks;
    }

    public void setGeneratedTasks(ArrayList<Task> generatedTasks) {
        this.generatedTasks = generatedTasks;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getAwgWaitTime() {
        return awgWaitTime;
    }

    public void setAwgWaitTime(int awgWaitTime) {
        this.awgWaitTime = awgWaitTime;
    }

    public int getAwgServTime() {
        return awgServTime;
    }

    public void setAwgServTime(int awgServTime) {
        this.awgServTime = awgServTime;
    }

    public int getPeakH() {
        return peakH;
    }

    public void setPeakH(int peakH) {
        this.peakH = peakH;
    }

    public int getMaxH() {
        return maxH;
    }

    public void setMaxH(int maxH) {
        this.maxH = maxH;
    }

    public int getPeakSum() {
        return peakSum;
    }

    public void setPeakSum(int peakSum) {
        this.peakSum = peakSum;
    }
}
