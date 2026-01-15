package evaluation;

import java.util.List;

public class Accuracy <L> implements EvaluationMeasure<L> {
    @Override
    public double evaluate(List<L> trueLabels, List<L> predictedLabels) {
        int correct = 0;
        for (int i = 0; i < trueLabels.size(); i++) {
            if (trueLabels.get(i).equals(predictedLabels.get(i))) {
                correct++;
            }
        }
        return correct / (double) trueLabels.size();
    }
}
