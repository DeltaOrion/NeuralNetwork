package me.jacob.assign.color;

import java.awt.*;

public class ColorSet {

    private final String name;
    private String altName;
    private final Color color;

    public ColorSet(String name, Color color, String altName) {
        this.name = name;
        this.color = color;
        this.altName = altName;
    }

    public ColorSet(String name, Color color) {
        this(name,color,null);
    }

    public String getName() {
        return name;
    }

    public String getAltName() {
        return altName;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "ColorSet{" +
                "name='" + name + '\'' +
                ", altName='" + altName + '\'' +
                ", color=" + color +
                '}';
    }
}
