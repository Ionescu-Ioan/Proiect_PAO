package exercitii;
//1. Se citeste un sir de caractere de la tastatura, verificati daca este un palindrom.

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {

        Scanner scanner;
        scanner = new Scanner(System.in);
        System.out.println("Dati sirul de caractere: ");
        String s = scanner.nextLine();

        int i = 0;
        int j = s.length() - 1;

        while(i <= j)
        {
            if(s.charAt(i) != s.charAt(j))
            {
                System.out.println("Sirul dat nu este palindrom!\n");
                break;
            }
            i += 1;
            j -= 1;
        }
        System.out.println("Sirul dat este palindrom!\n");
    }
}
