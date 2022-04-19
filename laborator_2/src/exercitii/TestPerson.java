package exercitii;

public class TestPerson {

    public static void main(String[] args) {

        Person persoana1 = new Person("Andrei", "Popescu", 21, 123546, "male");
        Person persoana2 = new Person("Alexandra", "Ionescu", 20, 2324334, "female");

        System.out.println("persoana1 are atributele: ");
        System.out.println(persoana1.toString());
        System.out.println("persoana2 are atributele: ");
        System.out.println(persoana2.toString());

    }
}
