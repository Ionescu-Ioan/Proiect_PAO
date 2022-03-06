package exercitii;

public class TestSubject {

    public static void main(String[] args) {

        Room camera1 = new Room(24, "amfiteatru",3);
        Room camera2 = new Room(10, "laborator", 1);

        Person persoana1 = new Person("Andrei", "Popescu", 31, 123546, "male");
        Person persoana2 = new Person("Alexandra", "Ionescu", 29, 2324334, "female");

        Subject subiect1 = new Subject(camera1, 30,persoana1);
        Subject subiect2 = new Subject(camera2, 24, persoana2);

        System.out.println("subiect1 are atributele: ");
        System.out.println(subiect1.toString());
        System.out.println("subiect2 are atributele: ");
        System.out.println(subiect2.toString());

    }
}
