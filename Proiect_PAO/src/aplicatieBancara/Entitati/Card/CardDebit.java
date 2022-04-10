package aplicatieBancara.Entitati.Card;

import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.TipCard;

import static aplicatieBancara.Entitati.TipCard.DEBIT;

public class CardDebit extends Card{

    private final static TipCard tip = DEBIT;

    public CardDebit(int cardId, Cont cont){
        super(cardId, cont);
        sold = this.cont.getSold();
    }


    public String getTip()
    {
        return "Debit";
    }


}
