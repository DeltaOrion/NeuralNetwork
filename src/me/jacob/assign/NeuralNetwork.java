package me.jacob.assign;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private final Layer inputLayer;
    private final Layer outputLayer;

    protected NeuralNetwork(int input, List<Integer> hiddenLayers, int output) {
        this.inputLayer = new Layer(0,input);
        int count = 1;
        Layer root = inputLayer;
        for(int neurons : hiddenLayers) {
            Layer next = new Layer(count,neurons);
            root.setNextLayer(next);
            next.setPreviousLayer(root);
            root = next;
            count++;
        }

        outputLayer = new Layer(count,output);
        outputLayer.setPreviousLayer(root);
        root.setNextLayer(outputLayer);
    }

    public double[] calculate(double... values) {
        inputLayer.setValues(values);
        Layer root = inputLayer.getNextLayer();
        while (root!=null) {
            root.calculate();
            root = root.getNextLayer();
        }

        return outputLayer.getValues();
    }

    public void train(double[] values, double[] expected) {
        double[] actual = calculate(values);
        double cost = getCost(actual,expected);
    }

    private double getCost(double[] actual, double[] expected) {
        double cost = 0;
        for(int i=0;i<actual.length;i++) {
            double c = actual[i] - expected[i];
            cost += (c * c);
        }

        return cost;
    }

    public static class Builder {
        private List<Integer> hiddenLayers;
        private int input;
        private int output;

        public Builder(int input, int output) {
            this.input = input;
            this.output = output;
            this.hiddenLayers = new ArrayList<>();
        }

        public Builder addHiddenLayer(int neurons) {
            this.hiddenLayers.add(neurons);
            return this;
        }

        public NeuralNetwork build() {
            return new NeuralNetwork(input,hiddenLayers,output);
        }
    }

}
