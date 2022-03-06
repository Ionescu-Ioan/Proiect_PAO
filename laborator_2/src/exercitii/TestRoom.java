package exercitii;

public class TestRoom {

    public static void main(String[] args) {

        Room camera1 = new Room(24, "amfiteatru",3);
        Room camera2 = new Room(10, "laborator", 1);

        System.out.println("camera1 are atributele: ");
        System.out.println(camera1.toString());
        System.out.println("camera2 are atributele: ");
        System.out.println(camera2.toString());

    }
}
