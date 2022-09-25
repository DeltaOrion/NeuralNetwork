package me.jacob.assign;

import java.util.*;

public class Neuron {

    private final List<Connection> connections;
    private double value;
    private double bias = Math.random();

    public Neuron() {
        this.connections = new ArrayList<>();
    }

    public double getValue() {
        return this.value;
    }

    public void calculate() {
        double value = 0;
        for(Connection connection : connections) {
            value += (connection.getNeuron().getValue() * connection.getWeight());
        }

        value += bias;

        this.value = sigmoid(value);
    }

    public void setValue(double value) {
        this.value = value;
    }

    private double sigmoid(double x) {
        return 1/(1+Math.pow(Math.E,-x));
    }

    public void setLayer(Collection<Neuron> neurons) {
        this.connections.clear();
        for(Neuron neuron : neurons) {
            this.connections.add(new Connection(neuron));
        }
    }


    //this isn't efficient as the matrix representation but thats not the point
    private static class Connection {
        private final Neuron neuron;
        private double weight;

        public Connection(Neuron neuron) {
            this.neuron = neuron;
            this.weight = Math.random();
        }

        public double getWeight() {
            return weight;
        }

        public Neuron getNeuron() {
            return neuron;
        }
    }
}
