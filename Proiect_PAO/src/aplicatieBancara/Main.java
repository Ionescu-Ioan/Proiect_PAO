package aplicatieBancara;

import aplicatieBancara.Card.*;

public class Main {
    public static void main(String[] args) {

        Banca banca = Banca.getBanca();

        Banca.showBanca();
        banca.showBanca();


        GeneratorCard generatorCard = new GeneratorCard();
        CardDebit c1 = generatorCard.creareCardDebit("RO49AAAA1B31007593840000", "Popescu Andrei");
        CardDebit c2 = generatorCard.creareCardDebit("RO49AAAA1B31007593840000", "Popescu Mircea");
        System.out.println(c2.toString());

    }
}
