package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ai.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("AI App");
        stage.setMinWidth(200);
        stage.setMinHeight(200);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
