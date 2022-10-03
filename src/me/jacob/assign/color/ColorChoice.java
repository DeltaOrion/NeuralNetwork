package me.jacob.assign.color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ColorChoice {

    private final Color input;
    private final List<ColorProbability> probabilities;

    public ColorChoice(Color input) {
        this.input = input;
        this.probabilities = new ArrayList<>();
    }

    public void addProbability(String label, double probability) {
        probabilities.add(new ColorProbability(label,probability));
    }

    public String getChoice() {
        int max = 0;
        for(int i=0;i<probabilities.size();i++) {
            if(probabilities.get(i).getProbability()>probabilities.get(max).getProbability())
                max = i;
        }

        return probabilities.get(max).getName();
    }

    public List<ColorProbability> getProbabilities() {
        List<ColorProbability> sorted = new ArrayList<>(probabilities);
        sorted.sort((o1, o2) -> Double.compare(o2.probability,o1.probability));
        return sorted;
    }

    public Color getInput() {
        return input;
    }

    public record ColorProbability(String name, double probability) {

        public String getName() {
            return name;
        }

        public double getProbability() {
            return probability;
        }

        @Override
        public String toString() {
            return "(" + name +", "+probability+")";
        }
    }
}
