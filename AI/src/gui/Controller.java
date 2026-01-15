package gui;

import domain.Instance;
import input.DataReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Instance<Double, Integer>> data;
    @FXML
    private Button dummy;

    @FXML
    private Button heart;
    @FXML
    private void initialize() {

        dummy.setOnAction(e->dummyHandle());
        heart.setOnAction(e->heartHandle());
    }
    private void dummyHandle(){
        DataReader reader = new DataReader();
        data = new ArrayList<>();
        data = reader.loadData("C:/Users/petru/java-UBB/AI/src/input/default_dummy_data.csv");
        train();
    }
    private void heartHandle(){
        DataReader reader = new DataReader();
        data = new ArrayList<>();
        data = reader.loadData("C:/Users/petru/java-UBB/AI/src/input/heart.csv");
        train();
    }
    private void train(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("train.fxml"));
            Parent root = loader.load();
            TrainController ctrl = loader.getController();
            ctrl.setController(data);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Train");
            stage.show();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
