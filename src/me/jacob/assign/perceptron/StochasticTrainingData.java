package me.jacob.assign.perceptron;

import java.util.Collections;
import java.util.List;

public class StochasticTrainingData extends TrainingData {

    private final int batchSize;
    private final int repetitions;

    public StochasticTrainingData(NeuralNetwork neuralNetwork, int repetitions, int batchSize) {
        super(neuralNetwork);
        this.batchSize = batchSize;
        this.repetitions = repetitions;
    }

    public StochasticTrainingData(NeuralNetwork neuralNetwork, int repetitions) {
        this(neuralNetwork,repetitions,10);
    }

    @Override
    public void train() {
        NeuralNetwork neuralNetwork = getNeuralNetwork();
        List<TrainingSet> trainingData = getTrainingData();

        int count = 0;
        for(int i=0;i<repetitions;i++) {
            //if(i%10==0)
            //    System.out.println("Repetitions:" + i);
            Collections.shuffle(trainingData);
            for(TrainingSet set : trainingData) {
                neuralNetwork.train(set.getInputs(), set.getExpected());
                count++;
                if(count>=batchSize) {
                    count=0;
                    neuralNetwork.applyTraining();
                }
            }
        }
    }
}
