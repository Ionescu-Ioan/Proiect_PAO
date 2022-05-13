package aplicatieBancara.Entitati.Card;

import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.TipCard;

import java.time.LocalDate;

import static aplicatieBancara.Entitati.TipCard.DEBIT;

public class CardDebit extends Card{

    private final static TipCard tip = DEBIT;

    public CardDebit(int cardId, Cont cont){
        super(cardId, cont);
        sold = this.cont.getSold();
    }

    public CardDebit(int cardId, Cont cont, int CVV, String numar, LocalDate data) {
        super(cardId, cont, CVV, numar, data);
        sold = this.cont.getSold();
    }

    public String toCSV()
    {
        return this.cardId + "," + this.IBAN + "," + this.getTip();
    }


    public String getTip()
    {
        return "DEBIT";
    }


}
