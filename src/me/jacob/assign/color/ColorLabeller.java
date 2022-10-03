package me.jacob.assign.color;

import me.jacob.assign.perceptron.NeuralNetwork;
import me.jacob.assign.perceptron.StochasticTrainingData;
import me.jacob.assign.perceptron.TrainingData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColorLabeller {

    private NeuralNetwork brain;
    private List<ColorSet> trainingData;
    private List<String> labelMap;
    private TrainingData coach;

    public void init(List<ColorSet> colors, int repetitions) {
        this.trainingData = colors;
        this.labelMap = new ArrayList<>();
        buildLabelMap(trainingData);
        this.brain = new NeuralNetwork.Builder(3,
                labelMap.size())
                .addHiddenLayer(colors.size()+3)
                .setLearningRate(0.5)
                .build();
        buildTrainingData(repetitions);
    }

    private void buildLabelMap(List<ColorSet> trainingData) {
        String currLabel = "";
        for(ColorSet set : trainingData) {
            if(!set.getName().equals(currLabel)) {
                currLabel = set.getName();
                labelMap.add(currLabel);
            }
        }
    }

    public void train() {
        coach.train();
    }

    public ColorChoice getTrainingChoice(int r, int g, int b) {
        Color color = new Color(r,g,b);
        for(ColorSet set : trainingData) {
            if(set.getColor().equals(color)) {
                return getChoice(color,buildClassification(set));
            }
        }

        return null;
    }

    public ColorChoice getLabel(int r, int g, int b) {
        double rNorm = normalize(r);
        double gNorm = normalize(g);
        double bNorm = normalize(b);

        double[] classifications = brain.calculate(rNorm,gNorm,bNorm);
        return getChoice(new Color(r,g,b),classifications);
    }

    private ColorChoice getChoice(Color input, double[] classifications) {
        ColorChoice choice = new ColorChoice(input);
        for(int i=0;i<classifications.length;i++) {
            choice.addProbability(getLabel(i),classifications[i]);
        }

        return choice;
    }

    private String getLabel(int node) {
        return labelMap.get(node);
    }

    private double normalize(int color) {
        return (1.0f/255.0f) * color;
    }

    private void buildTrainingData(int repetitions) {
        coach = new StochasticTrainingData(brain,repetitions, 10);

        for (ColorSet set : trainingData) {
            double[] inputs = new double[3];

            inputs[0] = normalize(set.getColor().getRed());
            inputs[1] = normalize(set.getColor().getGreen());
            inputs[2] = normalize(set.getColor().getBlue());

            coach.addSet(inputs, buildClassification(set));
        }
    }

    private double[] buildClassification(ColorSet set) {
        double maxDistance = getDistance(new Color(0,0,0),new Color(255,255,255));
        double[] outputs = new double[labelMap.size()];
        int count = 0;
        for(String label : labelMap) {
            if(set.getName().equals(label)) {
                outputs[count] = 1;
            }
            count++;
        }
        /*
        for(int j=0;j<trainingData.size();j++) {
            double distance = getDistance(set.getColor(),trainingData.get(j).getColor());
            double normalized = distance/maxDistance;
            double similarity = (1-normalized);
            outputs[j] = similarity;
        }
        */
        return outputs;
    }

    //return 1 if the colors are the same, 0 if they are not even close
    private static double getDistance(Color e1, Color e2) {
        long rmean = ( (long)e1.getRed() + (long)e2.getRed() ) / 2;
        long r = (long)e1.getRed() - (long)e2.getRed();
        long g = (long)e1.getGreen() - (long)e2.getGreen();
        long b = (long)e1.getBlue() - (long)e2.getBlue();
        return Math.sqrt((((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8));
    }


}
