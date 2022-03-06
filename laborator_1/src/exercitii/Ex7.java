package exercitii;

import java.util.Scanner;

public class Ex7 {

    public static void main(String[] args) {

        Scanner scanner;

        scanner = new Scanner(System.in);

        int n, x1 = 0, x2 = 1, xi;

        System.out.println("Dati valoare pentru n: ");

        n = scanner.nextInt();

        if (n == 1)
            System.out.println("Primul termen din seria Fibonacci este: " + 0);
        else if (n == 2)
            System.out.println("Al " + n + "-lea termen din seria Fibonacci este: " + 1);
        else
        {
            for (int i = 2; i < n ; ++i)
            {
                xi = x1 + x2;
                if(i == n-1)
                    System.out.println("Al " + n + "-lea termen din seria Fibonacci este: " + xi);
                x1 = x2;
                x2 = xi;

            }

        }
    }
}
