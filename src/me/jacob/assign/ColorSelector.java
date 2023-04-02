package me.jacob.assign;

import me.jacob.assign.color.ColorChoice;
import me.jacob.assign.color.ColorLabeller;
import me.jacob.assign.color.ColorSet;
import me.jacob.assign.color.ColorTrainingReader;
import me.jacob.assign.perceptron.NeuralNetwork;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ColorSelector {

    public static void main(String[] args) throws FileNotFoundException {
        String path = null;
        if(args.length == 0) {
            path = "ColorTrainingData.txt";
        } else {
            path = args[0];
        }

        Reader reader = new FileReader(path);
        ColorTrainingReader colorReader = new ColorTrainingReader(reader);
        ColorLabeller labeller = new ColorLabeller();

        System.out.println(colorReader.getColorLabels());
        labeller.init(colorReader.getColorLabels(),200);
        labeller.train();


        for(ColorSet colorSet : colorReader.getColorLabels()) {
            ColorChoice choice = labeller.getLabel(colorSet.getColor().getRed(),colorSet.getColor().getGreen(),colorSet.getColor().getBlue());
            System.out.println("ACTUAL: "+colorSet.getName() + ", "+colorSet.getAltName());
            System.out.println("COMPUTER: "+choice.getChoice());
            System.out.println(choice.getProbabilities());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Input Color: ");
            String next = scanner.next();
            if(next.equals("."))
                break;

            String[] input = next.split(",");
            int r = Integer.parseInt(input[0]);
            int g = Integer.parseInt(input[1]);
            int b = Integer.parseInt(input[2]);

            ColorChoice choice = labeller.getLabel(r,g,b);
            System.out.println("Color Name: " + choice.getChoice());
            int count = 0;
            for(ColorChoice.ColorProbability probability : choice.getProbabilities()) {
                System.out.print(probability + ", ");
                if(count>=3)
                    break;

                count++;
            }
            System.out.println();
        }
    }

}
