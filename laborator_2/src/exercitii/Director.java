package exercitii;

public class Director {

    private static String name;
    private static Director director;

    private Director(){
        name = "Dan Georgescu";

    }

    public static Director getDirector(){
        if(director == null)
            director = new Director();
        return director;
    }

    public static void showDirector(){
        System.out.println("Director: " + name);
    }
}

