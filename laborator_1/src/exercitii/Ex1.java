package exercitii;

import java.util.Scanner;

public class Ex1 {

    public static void main(String[] args) {

        Scanner scanner;

        scanner = new Scanner(System.in);

        System.out.println("Dati valoare pentru n: ");

        int n = scanner.nextInt();

        for(int i = 0; i <= n; i+=2)
        {
            System.out.println(i);
        }
    }

}
