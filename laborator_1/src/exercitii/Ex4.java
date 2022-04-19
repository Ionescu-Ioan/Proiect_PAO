package exercitii;

import java.util.Scanner;

public class Ex4 {

    public static int Multiplii(int x){
        int sum = 0;
        for(int i = 3; i <= x; ++i)
            if(i % 3 == 0 || i%5 == 0)
                sum += i;
        return sum;
    }

    public static void main(String[] args) {

        Scanner scanner;

        scanner = new Scanner(System.in);

        System.out.println("Dati valoare pentru n: ");

        int n = scanner.nextInt();

        System.out.println("Suma ceruta este: " + Multiplii(n));


    }
}
