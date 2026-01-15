package model;

import domain.Instance;

import java.util.*;

public class ModelKNN implements Model<Double,Integer> {
    private List<Instance<Double,Integer>> trainingData;
    int k;

    public ModelKNN(int k) {
        this.trainingData = new ArrayList<>();
        this.k = k;
    }

    @Override
    public void train(List<Instance<Double,Integer>> instances){
        this.trainingData.clear();
        this.trainingData.addAll(instances);
    }
    @Override
    public List<Integer> test(List<Instance<Double,Integer>> instances){
        List<Integer> predictions = new ArrayList<>();
        for (Instance<Double, Integer> testInstance : instances) {
            int predictedLabel = predict(testInstance);
            predictions.add(predictedLabel);
        }

        return predictions;
    }

    private Integer predict(Instance<Double, Integer> testInstance) {
        List<Map.Entry<Double, Integer>> distances = new ArrayList<>();
        for (Instance<Double, Integer> trainInstance : trainingData) {
            double distance = distance(testInstance.getInput(), trainInstance.getInput());
            distances.add(Map.entry(distance, trainInstance.getOutput()));
        }
        distances.sort(Map.Entry.comparingByKey());
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < Math.min(k, distances.size()); i++) {
            neighbors.add(distances.get(i).getValue());
        }
        return majorityVote(neighbors);
    }
    private double distance(List<Double> a, List<Double> b) {
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            double diff = a.get(i) - b.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
    private Integer majorityVote(List<Integer> labels) {
        Map<Integer, Long> counts = new HashMap<>();
        for (Integer label : labels) {
            counts.put(label, counts.getOrDefault(label, 0L) + 1);
        }
        return Collections.max(counts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
