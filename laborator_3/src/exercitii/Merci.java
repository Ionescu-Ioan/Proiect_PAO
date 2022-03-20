package exercitii;

import java.util.Objects;

public class Merci extends CandyBox{
    private final String shape = "cube";
    private double length;

    public Merci(){
    }

    public Merci(String flavor, String origin, int length) {
        super(flavor, origin);
        this.length = length;
    }

    public double getVolume(){
        return length * length * length;
    }

    public String getShape() {
        return shape;
    }

    public double getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "The " + origin + " " + flavor + " has volume " + getVolume();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merci merci = (Merci) o;
        return length == merci.length && origin == merci.origin && flavor == merci.flavor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, length);
    }
}
