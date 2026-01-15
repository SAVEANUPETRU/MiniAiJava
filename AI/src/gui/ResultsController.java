package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ResultsController {

    private int fp;
    private int tp;
    private int fn;
    private int tn;
    private double acc;
    private double pre;

    @FXML
    private Label FalsePositive;
    @FXML
    private Label FalseNegative;
    @FXML
    private Label TruePositive;
    @FXML
    private Label TrueNegative;
    @FXML
    private Label Accuracy;
    @FXML
    private Label Precision;
    public ResultsController() {}
    public void setResults(int fp, int fn, int tp, int tn, double acc, double pre) {
        this.fp = fp;
        this.fn = fn;
        this.tp = tp;
        this.tn = tn;
        this.acc = acc;
        this.pre = pre;
        TrueNegative.setText(String.valueOf(tn));
        TruePositive.setText(String.valueOf(tp));
        FalsePositive.setText(String.valueOf(fp));
        FalseNegative.setText(String.valueOf(fn));
        Accuracy.setText(String.format("%.2f", acc));
        Precision.setText(String.format("%.2f", pre));
    }
    @FXML
    private void initialize() {}

}
