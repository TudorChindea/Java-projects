
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends Application {


    public AnchorPane btn;
    public TextField finalTime;
    public TextField nrClients;
    public TextField nrReg;
    public TextField maxServ;
    public TextField minServ;
    public TextField maxArr;
    public TextField minArr;
    public Button btn1;
    public TextArea ta;

    public void init(Scene scene){
        ta=(TextArea)scene.lookup("#ta");
        ta.setText("");
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample.fxml")));
        primaryStage.setTitle("Store simulator 1");
        primaryStage.setScene(new Scene(root, 600, 400));

        primaryStage.show();
    }

    public void runProgram(ActionEvent actionEvent)throws Exception {
        SimulationManager genius = new SimulationManager(Integer.parseInt(finalTime.getText()), Integer.parseInt(maxServ.getText()), Integer.parseInt(minServ.getText()), Integer.parseInt(maxArr.getText()), Integer.parseInt(minArr.getText()), Integer.parseInt(nrClients.getText()), Integer.parseInt(nrReg.getText()));
        Thread t = new Thread(genius);
        final AtomicBoolean printYes= new AtomicBoolean(false);
        final AtomicBoolean finalPrint=new AtomicBoolean(false);
        t.start();
        init(this.minArr.getScene());
        Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(1),
                event -> {
                    if (printYes.compareAndSet(false,false)){
                        ta.appendText("Time " + genius.getCurrentTime());
                        for(Task t2: genius.getGeneratedTasks()){
                            ta.appendText( "(" + t2.getId() + "," + t2.getArrivalTime() + "," + t2.getProcessingTime() + ");");
                        }
                        for(int i=0;i< genius.getScheduler().getServers().size();i++){
                            Server s=genius.getScheduler().getServers().get(i);
                            ta.appendText("Queue " + (i+1)+"\n");
                            if (!s.getTasks().isEmpty()) {
                                ta.appendText("(" + s.getTasks().get(0).getId() + "," + s.getTasks().get(0).getArrivalTime() + "," + s.getTasks().get(0).getProcessingTime() + ")\n");
                            }
                            else{
                                ta.appendText("closed\n");
                            }
                        }
                        ta.appendText("\n");
                    }
                    if(genius.exitSim() == 1 || genius.getCurrentTime()>genius.getTimeLimit()){
                        printYes.set(true);
                        if(finalPrint.compareAndSet(false,false)) {
                            ta.appendText("peak hour "+genius.getMaxH()+"\navg service time" + genius.getAwgServTime() +"\n avg waiting time "+ genius.getAwgWaitTime());
                            finalPrint.set(true);
                        }
                    }
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public static void main(String[] args) throws Exception{
        launch(args);

    }
}

