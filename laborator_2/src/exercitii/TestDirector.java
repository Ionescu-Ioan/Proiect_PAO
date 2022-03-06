package exercitii;

public class TestDirector {

    public static void main(String[] args) {
        Director d1 = Director.getDirector();
        Director d2 = Director.getDirector();

        Director.showDirector();
        d1.showDirector();
        d2.showDirector();

        //test
        System.out.println(d2 == d2);
    }

}
