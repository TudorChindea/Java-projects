import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import bll.ClientBLL;
import model.Client;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * sets the initial scene to StartPage.fxml
 */
public class Main extends Application {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * starts the application and sets the initial scene
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartPage.fxml")));
        primaryStage.setTitle("Store simulator 1");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
