package aplicatieBancara.Card;


import aplicatieBancara.Cont.Cont;
import aplicatieBancara.TipCard;
import static aplicatieBancara.TipCard.CREDIT;


public class CardCredit extends Card{

    private final static TipCard tip = CREDIT;
    private double sumaCreditata;


    public CardCredit(int cardId, Cont cont){
        super(cardId, cont);
        sold = this.cont.interogareSold();
        sumaCreditata = 0;
    }


    public void actualizareSumaCreditata(double suma)
    {
        sumaCreditata += suma;
    }


    public double getSumaCreditata(){
        return sumaCreditata;
    }

    public String getTip()
    {
        return "Credit";
    }
}
