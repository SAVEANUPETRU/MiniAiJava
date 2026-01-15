package model;

import domain.Instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelPerceptron implements Model<Double,Integer> {
    private List<Double> weights;
    private double bias;
    private double learningRate;
    private int epochs;
    public ModelPerceptron(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }
    @Override
    public void train(List<Instance<Double, Integer>> instances) {
        int featureCount = instances.get(0).getInput().size();
        weights = new ArrayList<>(Collections.nCopies(featureCount, 0.0));
        bias = 0.0;
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (Instance<Double, Integer> instance : instances) {
                int yTrue = instance.getOutput() == 0 ? -1 : 1;
                double yPred = predictValue(instance.getInput());
                int yPredLabel = yPred >= 0 ? 1 : -1;

                if (yTrue != yPredLabel) {
                    for (int i = 0; i < featureCount; i++) {
                        double newWeight = weights.get(i) + learningRate * yTrue * instance.getInput().get(i);
                        weights.set(i, newWeight);
                    }
                    bias += learningRate * yTrue;
                }
            }
        }
    }
    @Override
    public List<Integer> test(List<Instance<Double, Integer>> instances) {
        List<Integer> predictions = new ArrayList<>();
        for (Instance<Double, Integer> instance : instances) {
            double yPred = predictValue(instance.getInput());
            predictions.add(yPred >= 0 ? 1 : 0);
        }
        return predictions;
    }

    private double predictValue(List<Double> features) {
        double sum = bias;
        for (int i = 0; i < features.size(); i++) {
            sum += features.get(i) * weights.get(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Perceptron{" +
                "weights=" + weights +
                ", bias=" + bias +
                '}';
    }

}
