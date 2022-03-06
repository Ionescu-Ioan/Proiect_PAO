package exercitii;

import java.util.Arrays;
import java.util.Scanner;

public class Ex6 {

    public static void main(String[] args) {

        Scanner scanner;

        scanner = new Scanner(System.in);

        int[] x = new int[100];

        System.out.println("Dati valorile notelor(citirea se opreste la inserarea lui -1): ");

        int a = scanner.nextInt();
        int cnt = 0;
        float media = 0;

        do{
            if(a == -1)
                System.out.println("Nu a fost introdusa nicio nota!");
            x[cnt] = a;
            cnt++;
            media += a;
            a = scanner.nextInt();
        }while(a != -1);

        media = media/cnt;

        System.out.println("Media notelor este: " + media);

    }
}
