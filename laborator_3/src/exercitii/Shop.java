package exercitii;

public class Shop {

    public static void main(String[] args) {
        Milka milka1 = new Milka("vanilla", "Swiss", 10, 15);
        Milka milka2 = new Milka("chocolate", "Belgian", 10, 10);
        Milka milka3 = new Milka("strawberries", "Swiss", 10, 15);
        Milka milka4 = new Milka("orange", "France", 15, 15);
        Lindt lindt1 = new Lindt("orange", "Bucharest", 5, 6, 5);
        Merci merci1 = new Merci("chocolate", "Belgian", 10);

        CandyBox[] boxes1 = {milka1, milka2, milka3};
        CandyBox[] boxes2 = {milka4, lindt1, merci1};

        CandyBag bag1 = new CandyBag(boxes1);
        CandyBag bag2 = new CandyBag(boxes2);

        Area area1 = new Area(bag1, 10, "Bulevardul Iuliu Maniu");
        Area area2 = new Area(bag2, 25, "Str Brasov");

        area1.printAddress();
        area2.printAddress();
    }

}
