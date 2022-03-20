package exercitii;

public class Test {

    public static void main(String[] args) {

        Merci m1 = new Merci("chocolate", "Belgian", 10);
        Merci m2 = new Merci("vanilla", "Belgian", 15);
        System.out.println(m1);
        System.out.println(m1.getLength());
        System.out.println(m1.getShape());
        System.out.println();
        Milka ml1 = new Milka("vanilla", "Swiss", 10, 15);
        System.out.println(ml1);
        System.out.println(ml1.getRadius());
        System.out.println(ml1.getShape());
        System.out.println(ml1.getVolume());
        System.out.println();
        Lindt l1 = new Lindt("orange", "Bucharest", 5, 6, 5);
        System.out.println(l1);
        System.out.println(l1.getLength());
        System.out.println(l1.getVolume());
        System.out.println(l1.getShape());

        CandyBox[] b1 = {m1, ml1, l1};
        CandyBox[] b2 = {m2, l1, ml1};

        CandyBag bag1 = new CandyBag(b1);
        CandyBag bag2 = new CandyBag(b2);

        System.out.println(bag1);
        System.out.println();
        System.out.println(bag2);
        bag2.setBoxes(b1);
        System.out.println(bag2);

        Area a1 = new Area(bag1, 27, "Drumul Valea Furcii");
        a1.printAddress();

    }
}
