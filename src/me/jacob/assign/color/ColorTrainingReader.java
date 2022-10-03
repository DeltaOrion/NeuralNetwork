package me.jacob.assign.color;

import java.awt.*;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ColorTrainingReader {

    private final List<ColorSet> labelledColors;

    public ColorTrainingReader(Reader reader) {
        this.labelledColors = new ArrayList<>();
        readColors(reader);
    }

    private void readColors(Reader reader) {
        Scanner scanner = new Scanner(reader);
        String label = "";
        while (scanner.hasNext()) {
            String name = scanner.next();
            if(name.startsWith("Color")) {
                String[] split = name.split(":");
                label = split[1];
            } else {
                String hex = scanner.next();

                //move through rgb
                scanner.next();
                scanner.next();
                scanner.next();

                Color color = Color.decode(hex);
                labelledColors.add(new ColorSet(label, color,name));
            }
        }
    }

    public List<ColorSet> getColorLabels() {
        return Collections.unmodifiableList(labelledColors);
    }
}
