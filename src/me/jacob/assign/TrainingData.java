package me.jacob.assign;

import java.util.ArrayList;
import java.util.List;

public abstract class TrainingData {

    private final NeuralNetwork neuralNetwork;
    private final List<TrainingSet> trainingData;

    protected TrainingData(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        this.trainingData = new ArrayList<>();
    }

    public void addSet(double[] inputs, double[] expected) {
        this.trainingData.add(new TrainingSet(inputs,expected));
    }

    public abstract void train();

    public List<TrainingSet> getTrainingData() {
        return trainingData;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }
}
