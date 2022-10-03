package me.jacob.assign.perceptron;

public class TrainingSet {

    private final double[] inputs;
    private final double[] expected;

    public TrainingSet(double[] inputs, double[] expected) {
        this.inputs = inputs;
        this.expected = expected;
    }

    public double[] getInputs() {
        return inputs;
    }

    public double[] getExpected() {
        return expected;
    }
}
