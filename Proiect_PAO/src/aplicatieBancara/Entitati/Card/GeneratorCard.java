package aplicatieBancara.Entitati.Card;

import aplicatieBancara.Entitati.Cont.Cont;

public class GeneratorCard {

    public CardDebit creareCardDebit(Cont cont, int uniqueId){
        return new CardDebit(++uniqueId, cont);
    }

    public CardCredit creareCardCredit(Cont cont, int uniqueId){
        return new CardCredit(++uniqueId, cont);
    }


}
