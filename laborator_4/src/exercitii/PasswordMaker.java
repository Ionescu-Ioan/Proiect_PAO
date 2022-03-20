package exercitii;

import java.util.Random;
/*
3. Sa se implementeze o clasa PasswordMaker ce genereaza o parola pornind de la datele unei
        persoane. Aceasta clasa o sa contina urmatoarele:
        a. o constanta MAGIC_NUMBER, care ia orice valoare doriti
        b. un String constant MAGIC_STRING, lung de minim 20 caractere, generat random (puteti
        crea o metoda pentru asta – Stringul este alcatuit din caractere 😊)
        c. un constructor care primeste un String, numit name
        d. o metoda getPassword() care va returna parola
        Parola se construieste concatenand urmatoarele:
        - un sir random de lungime MAGIC_NUMBER
        - 10 caractere din MAGIC_STRING
        - lungimea atributului name ca si String
        - un numar intreg generat random din intervalul [0,100] folosind clasa Random (
        https://docs.oracle.com/javase/8/docs/api/java/util/Random.html )
        Modificati clasa PasswordMaker astfel incat sa respecte conceptul de Singleton.
*/

import java.util.Random;

public class PasswordMaker {


    static String generateRandomString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(targetStringLength, leftLimit, rightLimit + 1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    private static String name;
    private static PasswordMaker passwordMAker;
    private static String password;
    private static final int MAGIC_NUMBER = 3;
    private static final String MAGIC_STRING = generateRandomString(20);

    private PasswordMaker() {
        this.name = "Ionescu";
        String randomString = generateRandomString(MAGIC_NUMBER);
        String magic =  MAGIC_STRING.substring(0, 10);
        int len = name.length();
        Random r = new Random();
        int randomNumber = r.nextInt(100  + 1);
        password = randomString + magic + len + randomNumber;
    }

    public static PasswordMaker getPassword(){
        if(passwordMAker == null)
            passwordMAker = new PasswordMaker();
        return passwordMAker;
    }

    public static void showPassword()
    {
        System.out.println("Password: " + password);
    }
}
