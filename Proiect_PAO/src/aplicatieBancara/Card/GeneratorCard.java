package aplicatieBancara.Card;

import aplicatieBancara.Cont.Cont;

public class GeneratorCard {

    private static int uniqueId = 0;

    public CardDebit creareCardDebit(Cont cont){
        return new CardDebit(uniqueId++, cont);
    }

    public CardCredit creareCardCredit(Cont cont){
        return new CardCredit(uniqueId++, cont);
    }
}
