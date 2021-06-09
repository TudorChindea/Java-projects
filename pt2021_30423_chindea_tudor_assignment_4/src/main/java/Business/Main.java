package Business;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Class that starts the prgram and sets the initial scene
 */
public class Main extends Application {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
        primaryStage.setTitle("Store simulator 2");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception{
        launch(args);

    }
}

