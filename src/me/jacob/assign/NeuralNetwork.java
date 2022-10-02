package me.jacob.assign;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private final Layer inputLayer;
    private final Layer outputLayer;
    private final double learningRate;

    protected NeuralNetwork(int input, List<Integer> hiddenLayers, int output, double learningRate) {
        this.inputLayer = new Layer(this,0,input);
        int count = 1;
        this.learningRate = learningRate;
        Layer root = inputLayer;
        for(int neurons : hiddenLayers) {
            Layer next = new Layer(this,count,neurons);
            root.setNextLayer(next);
            next.setPreviousLayer(root);
            root = next;
            count++;
        }

        outputLayer = new Layer(this,count,output);
        outputLayer.setPreviousLayer(root);
        root.setNextLayer(outputLayer);
    }

    public double[] calculate(double... values) {
        inputLayer.setValues(values);
        inputLayer.feedForward();

        return outputLayer.getValues();
    }

    public void train(double[] inputs ,double[] expected) {
        //test the neural network
        inputLayer.setValues(inputs);
        inputLayer.feedForward();

        //calculate the error
        Matrix T = Matrix.fromArray(expected,false);
        Matrix R = outputLayer.getActivations();
        Matrix error = T.subtract(R);
        outputLayer.setError(error);

        outputLayer.propagateBackwards();
    }

    public void applyTraining() {
        outputLayer.applyChanges();
    }

    public double getLearningRate() {
        return learningRate;
    }

    public static class Builder {
        private final List<Integer> hiddenLayers;
        private final int input;
        private final int output;
        private double learningRate = 0.5d;

        public Builder(int input, int output) {
            this.input = input;
            this.output = output;
            this.hiddenLayers = new ArrayList<>();
        }

        public Builder addHiddenLayer(int neurons) {
            this.hiddenLayers.add(neurons);
            return this;
        }

        public Builder setLearningRate(double learningRate) {
            this.learningRate = learningRate;
            return this;
        }

        public NeuralNetwork build() {
            return new NeuralNetwork(input,hiddenLayers,output,learningRate);
        }
    }

}
