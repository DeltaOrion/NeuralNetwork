package me.jacob.assign;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private final int index;
    private final List<Neuron> neurons;
    private Layer previousLayer;
    private Layer nextLayer;

    public Layer(int index, int quantity) {
        this.index = index;
        this.neurons = new ArrayList<>();
        for(int i=0;i<quantity;i++) {
            this.neurons.add(new Neuron());
        }
    }

    public void setValues(double[] values) {
        for(int i=0;i<neurons.size();i++) {
            Neuron neuron = neurons.get(i);
            neuron.setValue(values[i]);
        }
    }

    public double[] getValues() {
        double[] values = new double[neurons.size()];
        int count = 0;
        for(Neuron neuron : neurons) {
            values[count] = neuron.getValue();
            count++;
        }
        return values;
    }

    public Layer getPreviousLayer() {
        return previousLayer;
    }

    public void setPreviousLayer(Layer previousLayer) {
        this.previousLayer = previousLayer;
        for(Neuron neuron : neurons) {
            neuron.setLayer(previousLayer.neurons);
        }
    }

    public Layer getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public void calculate() {
        for(Neuron neuron : neurons) {
            neuron.calculate();
        }
    }

    @Override
    public String toString() {
        return "Layer{" +
                "index=" + index +
                '}';
    }
}
