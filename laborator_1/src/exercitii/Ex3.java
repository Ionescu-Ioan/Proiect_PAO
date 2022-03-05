package exercitii;

import java.util.Scanner;

public class Ex3 {

    public static int Factorial(int x){
        int rez = 1;
        for(int i = 2; i<=x; ++i)
            rez = rez * i;
        return rez;
    }

    public static void main(String[] args) {

        Scanner scanner;

        scanner = new Scanner(System.in);

        System.out.println("Dati valoare pentru n: ");

        int n = scanner.nextInt();

        System.out.println("factoril(" + n +") = " + Factorial(n));


    }
}
