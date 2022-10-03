package me.jacob.assign;

import me.jacob.assign.perceptron.NeuralNetwork;
import me.jacob.assign.perceptron.StochasticTrainingData;
import me.jacob.assign.perceptron.TrainingData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class XORProblem {

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork.Builder(2,1)
                .addHiddenLayer(10)
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

        visualize(neuralNetwork);
    }

    private static void visualize(NeuralNetwork neuralNetwork) {
        JFrame jf = new JFrame();
        JLabel jl = new JLabel();
        ImageIcon ii = new ImageIcon(getVisualization(neuralNetwork,800,800,1));

        jl.setIcon(ii);
        jf.add(jl);
        jf.pack();
        jf.setVisible(true);
    }

    private static Image getVisualization(NeuralNetwork neuralNetwork, int width, int height, int resolution) {
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        double stepX = 1.0f/((double) width/resolution);
        double stepY = 1.0f/((double) height/resolution);

        for(int x=0;x<width-resolution;x+=resolution) {
            for(int y=0;y<height-resolution;y+=resolution) {
                double inputX = (double)x/resolution * stepX;
                double inputY = (double)y/resolution * stepY;
                double[] calculation = neuralNetwork.calculate(inputX,inputY);
                int val = (int) (calculation[0] * 255);
                Color color = new Color(val,val,val);
                fill(image,x,y,x+resolution,y+resolution,color);
            }
        }
        return image;
    }

    private static void fill(BufferedImage image ,int x1, int y1, int x2, int y2, Color color) {
        if(x2 < x1) {
            int temp = x2;
            x2 = x1;
            x1 = temp;
        }

        if(y2 < y1) {
            int temp = y2;
            y2 = y1;
            y1 = temp;
        }

        for(int x=x1;x<=x2;x++) {
            for(int y=y1;y<=y2;y++) {
                image.setRGB(x, y, color.getRGB());
            }
        }
    }

}
