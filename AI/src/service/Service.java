package service;

import domain.Instance;
import model.Model;

import java.util.ArrayList;
import java.util.List;

public class Service <F,L>{
    private Model<F,L> model;
    private List<Instance<F, L>> trainingSet;
    private List<Instance<F, L>> testSet;
    public Service(){};
    public void setModel(Model<F, L> model) {
        this.model = model;
    }
    public void split(List<Instance<F, L>> allData, double trainPercent){
        int trainSize = (int) (allData.size() * trainPercent);
        trainingSet = new ArrayList<>(allData.subList(0, trainSize));
        testSet = new ArrayList<>(allData.subList(trainSize, allData.size()));
    }
    public void train(){
        model.train(trainingSet);
        System.out.println("Done");
    }
    public List<L> testModel() {
        return model.test(testSet);
    }
    public List<Instance<F,L>> getTestSet(){
        return testSet;
    }
}
