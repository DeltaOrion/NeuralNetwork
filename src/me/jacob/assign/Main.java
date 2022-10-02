package me.jacob.assign;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        NeuralNetwork neuralNetwork = new NeuralNetwork.Builder(2,2)
                .addHiddenLayer(3)
                .addHiddenLayer(5)
                .build();

        System.out.println(Arrays.toString(neuralNetwork.calculate(0.1, 0.3)));
        neuralNetwork.train(new double[]{1.0,0.0});

    }

}
