package org.sample.whiteboardapp;

public class Figure {
    private String shape;
    private String color;
    private Coordinates coords;

    public String getColor() {
        return color;
    }

    public Coordinates getCoords() {
        return coords;
    }

    public String getShape() {
        return shape;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCoords(Coordinates coords) {
        this.coords = coords;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
