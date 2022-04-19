package exercitii;

import java.util.Scanner;
import java.util.Arrays;

//2. Scrieti un program care verifica daca doua siruri de caractere sunt anagrame. (ex: ramo, mora si roma sunt anagrame)

public class Ex2 {
    public static void main(String[] args) {

        Scanner scanner;
        scanner = new Scanner(System.in);
        System.out.println("Dati primul sir de caractere: ");
        String s1 = scanner.nextLine();
        System.out.println("Dati al doilea sir de caractere: ");
        String s2 = scanner.nextLine();

        boolean ok = true;
        if(s1.length() != s2.length())
            ok = false;

        char[] a1 = s1.toLowerCase().toCharArray();
        char[] a2 = s2.toLowerCase().toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        ok = Arrays.equals(a1, a2);

        if(ok)
            System.out.println("Cele doua siruri sunt anagrame.\n");
        else
            System.out.println("Cele doua siruri nu sunt anagrame.\n");
    }
}
