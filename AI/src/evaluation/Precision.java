package evaluation;

import java.util.List;

public class Precision<L> implements EvaluationMeasure<L> {
    @Override
    public double evaluate(List<L> trueLabels, List<L> predictedLabels) {
        int tp = 0, fp = 0;
        for (int i = 0; i < trueLabels.size(); i++) {
            boolean predictedPositive = predictedLabels.get(i).equals(1);
            boolean actuallyPositive = trueLabels.get(i).equals(1);

            if (predictedPositive && actuallyPositive) tp++;
            if (predictedPositive && !actuallyPositive) fp++;
        }
        return tp + fp == 0 ? 0.0 : tp / (double) (tp + fp);
    }
}
