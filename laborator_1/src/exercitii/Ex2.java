package exercitii;

import java.util.Scanner;

public class Ex2 {

    public static void main(String[] args) {

        Scanner scanner;

        scanner = new Scanner(System.in);

        System.out.println("Dati valoare pentru a: ");

        int a = scanner.nextInt();

        System.out.println("Dati valoare pentru b: ");

        int b = scanner.nextInt();

        if( a > b)
        {
            System.out.println(a);
        }
        else
        {
            System.out.println(b);

        }
    }
}
