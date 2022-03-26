package aplicatieBancara.Card;

import aplicatieBancara.Cont.Cont;
import aplicatieBancara.TipCard;

import static aplicatieBancara.TipCard.DEBIT;

public class CardDebit extends Card{

    private final static TipCard tip = DEBIT;

    public CardDebit(int cardId, Cont cont){
        super(cardId, cont);
        sold = this.cont.interogareSold();
    }


    public String getTip()
    {
        return "Debit";
    }
}
