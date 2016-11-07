import Screens.MainScreen.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Screens/MainScreen/MainScreen.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("База данных кинопроката");
        primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.show();

        mainController = loader.getController();

    }

    private MainController mainController;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void stop(){
        mainController.closeConnection();
    }
}
