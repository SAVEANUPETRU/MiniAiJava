package model;

import domain.Instance;

import java.util.*;

public class ModelNaiveBayes implements Model<Double,Integer> {
    private Map<Integer, List<List<Double>>> featureValuesByLabel;
    private Map<Integer, Double> labelProbabilities;
    private List<Integer> labels;
    @Override
    public void train(List<Instance<Double, Integer>> instances) {
        featureValuesByLabel = new HashMap<>();
        labelProbabilities = new HashMap<>();
        for (Instance<Double, Integer> instance : instances) {
            Integer label = instance.getOutput();
            featureValuesByLabel.putIfAbsent(label, new ArrayList<>());

            if (featureValuesByLabel.get(label).isEmpty()) {
                for (int i = 0; i < instance.getInput().size(); i++) {
                    featureValuesByLabel.get(label).add(new ArrayList<>());
                }
            }
            for (int i = 0; i < instance.getInput().size(); i++) {
                featureValuesByLabel.get(label).get(i).add(instance.getInput().get(i));
            }
        }
        int totalInstances = instances.size();
        for (Integer label : featureValuesByLabel.keySet()) {
            int count = featureValuesByLabel.get(label).get(0).size();
            labelProbabilities.put(label, count / (double) totalInstances);
        }
        labels = new ArrayList<>(featureValuesByLabel.keySet());
    }
    @Override
    public List<Integer> test(List<Instance<Double, Integer>> instances) {
        List<Integer> predictions = new ArrayList<>();
        for (Instance<Double, Integer> instance : instances) {
            predictions.add(predict(instance));
        }
        return predictions;
    }
    private Integer predict(Instance<Double, Integer> instance) {
        Map<Integer, Double> posterior = new HashMap<>();

        for (Integer label : labels) {
            double prob = Math.log(labelProbabilities.get(label));

            for (int i = 0; i < instance.getInput().size(); i++) {
                double x = instance.getInput().get(i);
                double mean = mean(featureValuesByLabel.get(label).get(i));
                double std = std(featureValuesByLabel.get(label).get(i), mean);
                prob += logGaussian(x, mean, std);
            }
            posterior.put(label, prob);
        }
        return Collections.max(posterior.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    private double mean(List<Double> values) {
        double sum = 0.0;
        for (double v : values)
            sum += v;
        return sum / values.size();
    }

    private double std(List<Double> values, double mean) {
        double sum = 0.0;
        for (double v : values)
            sum += Math.pow(v - mean, 2);
        return Math.sqrt(sum / values.size());
    }

    private double logGaussian(double x, double mean, double std) {
        if (std == 0) std = 1e-6;
        double exponent = -Math.pow(x - mean, 2) / (2 * std * std);
        return exponent - Math.log(std) - 0.5 * Math.log(2 * Math.PI);
    }

}
