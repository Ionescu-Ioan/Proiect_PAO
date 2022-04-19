package exercitii;

import java.util.Arrays;
import java.util.Scanner;

public class Ex5 {


    public static void main(String[] args) {

        Scanner scanner;

        scanner = new Scanner(System.in);

        System.out.println("Dati valoare pentru n: ");

        int n = scanner.nextInt();

        int[] x = new int[n];
        int[] y = new int[n];

        System.out.println("Dati valori pentru cele " + n + " elemente: ");

        int jx = 0, jy = 0;
        for(int i = 0; i < n; ++i)
        {
            int aux = scanner.nextInt();


            if(aux % 2 == 0)
            {
                x[jx] = aux;
                jx++;
            }
            else {
                y[jy] = aux;
                jy++;
            }

        }

        System.out.println("Continutul vectorului x:");
        System.out.print(Arrays.toString(x));
        System.out.println();
        System.out.println("Continutul vectorului y:");
        System.out.print(Arrays.toString(y));
    }
}
