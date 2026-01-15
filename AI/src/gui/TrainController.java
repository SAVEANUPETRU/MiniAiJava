package gui;

import evaluation.Precision;
import evaluation.Accuracy;
import evaluation.EvaluationMeasure;
import domain.Instance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ModelKNN;
import model.ModelNaiveBayes;
import model.ModelPerceptron;
import service.Service;

import java.util.List;

public class TrainController {
    private List<Instance<Double, Integer>> data;
    private Service service;
    @FXML
    private Button KNN;
    @FXML
    private Button NaiveBayes;
    @FXML
    private Button Perceptron;
    @FXML
    private Button TrainSplit;
    @FXML
    private Button Train;
    @FXML
    private Button Display;
    @FXML
    private TextField k;
    @FXML
    private TextField learningRate;
    @FXML
    private TextField epochs;
    @FXML
    private TextField split;

    public TrainController(){};

    public void setController(List<Instance<Double, Integer>> data){
        this.data = data;
    }
    @FXML
    private void initialize(){
        KNN.setOnAction(e->handleKNN());
        NaiveBayes.setOnAction(e->handleNaiveBayes());
        Perceptron.setOnAction(e->handlePerceptron());
        TrainSplit.setOnAction(e->handleTrainSplit());
        Train.setOnAction(e->handleTrain());
        Display.setOnAction(e->handleDisplay());
        service = new Service<>();
    }
    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void handleKNN(){
        try{
            int kNeighbors = Integer.parseInt(k.getText().trim());
            ModelKNN knn = new ModelKNN(kNeighbors);
            service.setModel(knn);
            showMessage("Selected KNN");
        }catch (Exception e){
            showMessage("Not valid k");
        }

    }
    private void handleNaiveBayes(){
        ModelNaiveBayes NaiveBayes = new ModelNaiveBayes();
        service.setModel(NaiveBayes);
        showMessage("Selected Naive Bayes");
    }
    private void handlePerceptron(){
        try{
            double learning = Double.parseDouble(learningRate.getText().trim());
            int epoch = Integer.parseInt(epochs.getText().trim());
            if(epoch > 300 ||  epoch < 10){
                showMessage("Range recommended 10-300");
            }
            if(learning < 0.01  ||  learning > 0.5){
                showMessage("Range recommended 0.01 - 0.5");
            }
            ModelPerceptron p = new ModelPerceptron(learning,epoch);
            service.setModel(p);
            showMessage("Selected Perceptron");


        } catch (Exception e) {
            showMessage("Not valid parameters");
        }
    }
    private void handleTrainSplit(){
        try {
            double percentage = Double.parseDouble(split.getText().trim());
            if( 0 > percentage ||  percentage >= 1){
                showMessage("Range should be 0-1");
            }
            else{
                service.split(data,percentage);
                showMessage("Ready for training");
            }
        }catch (Exception e){
            showMessage("Should be a number");
        }
    }
    private void handleTrain(){
        showMessage("Trained");
        service.train();
    }
    private void handleDisplay(){
        try {
             int fp = 0;
             int tp = 0;
             int fn = 0;
             int tn = 0;
             double acc = 0;
             double pre = 0;
            List<Integer> results = service.testModel();
            List<Instance<Double, Integer>> testSet = service.getTestSet();
            List<Integer> trueLabels = testSet.stream()
                    .map(Instance::getOutput)
                    .toList();
            for (int i = 0; i < trueLabels.size(); i++) {
                int actual = trueLabels.get(i);
                int predicted = results.get(i);
                if (actual == 1 && predicted == 1) tp++;
                else if (actual == 0 && predicted == 0) tn++;
                else if (actual == 0 && predicted == 1) fp++;
                else if (actual == 1 && predicted == 0) fn++;
            }
            EvaluationMeasure<Integer> accMeasure = new Accuracy<>();
            EvaluationMeasure<Integer> preMeasure = new Precision<>();
            acc = accMeasure.evaluate(trueLabels, results);
            pre = preMeasure.evaluate(trueLabels, results);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
            Parent root = loader.load();
            ResultsController ctrl = loader.getController();
            ctrl.setResults(fp,fn,tp,tn,acc,pre);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Results");
            stage.show();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
