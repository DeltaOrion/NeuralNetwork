package me.jacob.assign;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        NeuralNetwork neuralNetwork = new NeuralNetwork.Builder(2,1)
                .addHiddenLayer(10)
                .setLearningRate(0.8)
                .build();

        TrainingData data = new StochasticTrainingData(neuralNetwork,10000,10);
        data.addSet(new double[]{1,0}, new double[]{1});
        data.addSet(new double[]{0,1}, new double[]{1});
        data.addSet(new double[]{1,1}, new double[]{0});
        data.addSet(new double[]{0,0}, new double[]{0});

        data.train();

        System.out.println("1,1: " + Arrays.toString(neuralNetwork.calculate(1, 1)));
        System.out.println("1,0: " + Arrays.toString(neuralNetwork.calculate(1, 0)));
        System.out.println("0,1: " + Arrays.toString(neuralNetwork.calculate(0,1)));
        System.out.println("0,0: " + Arrays.toString(neuralNetwork.calculate(0,0)));
    }
}
