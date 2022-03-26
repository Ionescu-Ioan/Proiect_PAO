package aplicatieBancara;

import aplicatieBancara.Card.*;

public class Main {
    public static void main(String[] args) {

        Servicii servicii = new Servicii();

        Banca banca = Banca.getBanca();
        servicii.afisareDetaliiBanca();


    }
}
