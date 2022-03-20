package exercitii;

import java.util.Objects;

public class Lindt extends CandyBox{
    private final String shape = "parallelepiped";
    private double length;
    private double width;
    private double height;

    public Lindt(){

    }

    public Lindt(String flavor, String origin, double length, double width, double height) {
        super(flavor, origin);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public double getVolume(){
        return length * width * height;
    }

    public String getShape() {
        return shape;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "The " + origin + " " + flavor + " has volume " + getVolume();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lindt lindt = (Lindt) o;
        return length == lindt.length && width == lindt.width && height == lindt.height && origin == lindt.origin && flavor == lindt.flavor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, length, width, height);
    }
}
