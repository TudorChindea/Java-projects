//import sun.util.resources.Bundles;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
   private List<Server> servers;
   private List<Thread> threads = new ArrayList<>();
   private int maxNoServers;
   //private int maxTasksPerServer;
    public Scheduler(int maxNoServers,int clientsNr){
        servers = new ArrayList<>();
        this.maxNoServers=maxNoServers;
        for(int i=0;i<maxNoServers;i++){
            servers.add(new Server(clientsNr));
            threads.add(new Thread(servers.get(i)));
            //servers.get(i).run();
        }
    }
    public void dispatchTask(Task t){
        int minwaitingPeriod = servers.get(0).getWaitingPeriod().get(),finishTime=0;
        Server shortestPeriod = servers.get(0);
        int serverId = 0;

        for(int i=1;i<maxNoServers;i++){
           if(servers.get(i).getWaitingPeriod().get() < minwaitingPeriod){
               minwaitingPeriod = servers.get(i).getWaitingPeriod().get();
               shortestPeriod = servers.get(i);
               //serverId = i;
           }
        }
        //shortestPeriod.getWaitingPeriod().set(shortestPeriod.getWaitingPeriod().intValue()+t.getProcessingTime());
        for(Task tN: shortestPeriod.getTasks()){
            finishTime+= tN.getProcessingTime();
        }
        t.setFinishTime(finishTime);

        shortestPeriod.addTask(t);
        //servers.get(serverId).addTask(t);
        //System.out.println(shortestPeriod.getTasks().peek().getId());
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
    }
    public List<Server> getServers(){
        return servers;
    }
}
