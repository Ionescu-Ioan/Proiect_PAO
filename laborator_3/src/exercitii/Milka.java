package exercitii;

import java.util.Objects;

public class Milka extends CandyBox{
    private final String shape = "cylinder";
    private double radius;
    private double height;

    public Milka(){
    }

    public Milka(String flavor, String origin, double radius, double height) {
        super(flavor, origin);
        this.radius = radius;
        this.height = height;
    }

    public double getVolume(){
        return 3.14 * radius * radius *height;
    }

    public String getShape() {
        return shape;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
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
        Milka milka = (Milka) o;
        return radius == milka.radius && height == milka.height && origin == milka.origin && flavor == milka.flavor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, radius, height);
    }
}
